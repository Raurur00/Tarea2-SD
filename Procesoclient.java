import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class ProcesoClient extends UnicastRemoteObject implements InterfaceProceso {
    public int id;
    public int idCoordinador;
    public int num_vecinos;
    public int exp_recibidos;
    public boolean getMensaje;
    public String mensaje;

    public ProcesoClient(int id, int num_vecinos) 
    throws RemoteException
    {
        this.id = id;
        this.idCoordinador = -1;
        this.num_vecinos = num_vecinos;
        this.exp_recibidos = 0;
        this.getMensaje = false;
    }

    public void invocar(String id) 
    {
        boolean flag = false;
        do
        {
            String host = "127.0.0.1";
            try {
                Registry registry = LocateRegistry.getRegistry(host);
                InterfaceProceso stub = (InterfaceProceso) registry.lookup("Proceso" + id);
                String response = stub.explorer(Integer.toString(this.id));
                System.out.println(this.id + " manda explorer a " + id);
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
    }
    
    public  String eco(String id)
  	throws RemoteException, Exception
  	{
    	if (Integer.parseInt(id) > this.id) this.id = Integer.parseInt(id);
      	return id;
  	}
    
      public  String explorer(String id)
  	throws RemoteException, Exception
  	{
    	//ignora mensajes explorer con id menor al actual
    	if (Integer.parseInt(id) > this.idCoordinador) 
    	{
      		//actualiza id mayor
      	    this.idCoordinador = Integer.parseInt(id);
            this.getMensaje = true;  
        }
        System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer de " + id + " - " 
        + "Coordinador acutal: " + Integer.toString(this.idCoordinador));
    	this.exp_recibidos ++;
    
    	return id;
    }
    
    public static void listen(String id, InterfaceProceso k)
  	throws Exception
  	{
    	System.setProperty("java.security.policy","file:/C:/Users/javie/Documents/GitHub/Tarea2-SD/Proceso.policy");
     
    	if (System.getSecurityManager() == null) {
      		System.setSecurityManager(new SecurityManager());
    	}

    	try
    	{
      	Naming.rebind("Proceso" + id, k);
    	} catch (RemoteException e) {
     		e.printStackTrace();
    	}
  	}
}