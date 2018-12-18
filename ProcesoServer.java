import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;


public class ProcesoServer  extends UnicastRemoteObject implements InterfaceProceso
{
	public int id;
  	public int num_vecinos;
  	public int exp_recibidos;
  	public boolean getMensaje;

  	public ProcesoServer(int id, int num_vecinos)
  	throws RemoteException 
  	{
    	this.id = id;
    	this.num_vecinos = num_vecinos;
    	this.exp_recibidos = 0;
    	this.getMensaje = false;
  	} 
    
  	

  	public boolean ifgetMensaje()
  	{
    	return this.getMensaje;
  	}

  	public int getExpRec()
  	{
    	return this.exp_recibidos;
  	}

  	public int getId()
  	{
    	return this.id;
  	}

  	

	
}