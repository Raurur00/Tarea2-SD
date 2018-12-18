import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface InterfaceProceso extends Remote {
  
  public abstract String eco(String id) throws RemoteException, Exception;

  public abstract void explorer(String idCoord, String idNodo) throws RemoteException, Exception;

}