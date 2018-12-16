import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;


public class Proceso  extends UnicastRemoteObject implements InterfaceProceso
{

    public Proceso()
    throws RemoteException
  {}
    
    public  String eco(String id) throws RemoteException, Exception
    {
        return "funciona";
    }

    public static void main(String[] args)
    throws Exception
  {
    System.setProperty("java.security.policy","file:/C:/Users/Pablo/Desktop/Git/Tarea2-SD/Proceso.policy");
    
    
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }

    try
    {
        InterfaceProceso k = new Proceso();
        Naming.rebind("Proceso", k);
        System.out.println("Proceso Registrado.");
    }   catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}