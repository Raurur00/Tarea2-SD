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
    public boolean sendExplorer;
    public boolean sendEco;
    public String mensaje;
    public int FL;

    public ProcesoClient(int id, int num_vecinos) 
    throws RemoteException
    {
        this.id = id;
        this.idCoordinador = -1;
        this.num_vecinos = num_vecinos;
        this.sendExplorer = false;
        this.sendEco = false;
        this.mensaje = "";
        this.FL = -1;
    }

    public  String eco(String id)
  	throws RemoteException, Exception
  	{
    	if (Integer.parseInt(id) > this.id) this.id = Integer.parseInt(id);
      	return id;
  	}
    
    public  void explorer(String idCoord, String idNodo)
  	throws RemoteException, Exception
  	{
    	//ignora mensajes explorer con id menor al actual
    	if (Integer.parseInt(idCoord) > this.idCoordinador) 
    	{
      		//actualiza id mayor
      	    this.idCoordinador = Integer.parseInt(idCoord);
            this.FL = Integer.parseInt(idNodo);

            if (this.num_vecinos == 1)
            {
                this.sendEco = true;
            }   else    {
                this.sendExplorer = true;
            }
        }

        else if (Integer.parseInt(idCoord) == this.idCoordinador)
        {
            this.sendEco = true;
        }
        System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer de " + id + " - " 
        + "Coordinador acutal: " + Integer.toString(this.idCoordinador));
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
                stub.explorer(Integer.toString(this.idCoordinador),Integer.toString(this.id));
                System.out.println(this.id + " manda explorer a " + id);
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
    }
    
    public static void listen(String id, InterfaceProceso k)
  	throws Exception
  	{
    	System.setProperty("java.security.policy","file:/C:/Users/Pablo/Desktop/Git/Tarea2-SD/Proceso.policy");
     
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