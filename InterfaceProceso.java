import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface InterfaceProceso extends Remote {
  
  public abstract String eco(String id) throws RemoteException, Exception;

}