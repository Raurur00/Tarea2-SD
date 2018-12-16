import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;


public class ProcesoServer  extends UnicastRemoteObject implements InterfaceProceso
{

    public ProcesoServer()
    throws RemoteException
  {}
    
    public  String eco(String id)
    throws RemoteException, Exception
    {
        return id;
    }

    public static void listen(String id)
    throws Exception
  {
    System.setProperty("java.security.policy","file:/C:/Users/Pablo/Desktop/Git/Tarea2-SD/Proceso.policy");
     
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }

    try
    {
        InterfaceProceso k = new ProcesoServer();
        Naming.rebind("Proceso" + id, k);
        System.out.println("Proceso Registrado.");
    }   catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}