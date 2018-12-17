import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface InterfaceProceso extends Remote {
  
  public abstract String eco(String id) throws RemoteException, Exception;

  public abstract String explorer(String id) throws RemoteException, Exception;

  public abstract int getExpRec() throws RemoteException, Exception;

  public abstract boolean ifgetMensaje() throws RemoteException, Exception;

  public abstract int getId() throws RemoteException, Exception;

}