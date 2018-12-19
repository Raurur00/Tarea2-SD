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
    public int num_echos;
    public boolean sendExplorer;
    public boolean sendEco;
    public int FL;
    public boolean iniciador;
    public boolean coordinador;

    public ProcesoClient(int id, int num_vecinos) 
    throws RemoteException
    {
        this.id = id;
        this.idCoordinador = -1;
        this.num_vecinos = num_vecinos;
        this.sendExplorer = false;
        this.sendEco = false;
        this.FL = -1;
        this.iniciador = false;
        this.coordinador = false;
    }

    public  String eco(String id)
  	throws RemoteException, Exception
  	{
        System.out.println("Nodo " + Integer.toString(this.id) + " recibe eco de " + id);
        this.num_echos++;
        if (this.iniciador){
            if (this.num_echos == this.num_vecinos){
                this.coordinador = true;
                System.out.println("Soy Coordinador");
            }
        } else {
            if (this.num_echos == (this.num_vecinos - 1)){
                this.sendEco = true;
            }
        }
        
      	return id;
  	}
    
    public  void explorer(String idCoord, String idNodo)
  	throws RemoteException, Exception
  	{
        System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer de " + idNodo);
        //ignora mensajes explorer con id menor al actual
    	if (Integer.parseInt(idCoord) > this.idCoordinador) {
      		//actualiza id mayor
            this.idCoordinador = Integer.parseInt(idCoord);
            System.out.println("Nuevo Coordinador: " + Integer.toString(this.idCoordinador));
            this.FL = Integer.parseInt(idNodo);
            this.num_echos = 0;
            this.sendExplorer = true;
        } else if (Integer.parseInt(idCoord) == this.idCoordinador) {
            this.num_echos++;
            if (this.iniciador){
                if (this.num_echos == this.num_vecinos){
                    this.coordinador = true;
                    System.out.println("Soy Coordinador");
                }
            } else {
                if (this.num_echos == (this.num_vecinos - 1)){
                    this.sendEco = true;
                }
            }
        }
        if ((this.num_vecinos - 1) == 0 ){
            this.sendEco = true;
        }     
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
                System.out.println("Nodo " + this.id + " manda explorer a " + id +"(ID Coordinador:" + this.idCoordinador + ")");
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
    }

    public void invocarEco(String id) 
    {
        boolean flag = false;
        do
        {
            String host = "127.0.0.1";
            try {
                Registry registry = LocateRegistry.getRegistry(host);
                InterfaceProceso stub = (InterfaceProceso) registry.lookup("Proceso" + id);
                stub.eco(Integer.toString(this.id));
                System.out.println("Nodo " + this.id + " manda eco a " + id);
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
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