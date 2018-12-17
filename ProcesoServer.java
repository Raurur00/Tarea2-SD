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
    
    public  String eco(String id)
    throws RemoteException, Exception
    {
      if (Integer.parseInt(id) > this.id) this.id = Integer.parseInt(id);
        return id;
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

    public  String explorer(String id)
    throws RemoteException, Exception
    {
      System.out.println("nodo " + Integer.toString(this.id) + " recibe explorer de " + id);

      //ignora mensajes explorer con id menor al actual
      if (Integer.parseInt(id) > this.id) 
      {
        //actualiza id mayor
        this.id = Integer.parseInt(id);
        this.getMensaje = true;
      }
      this.exp_recibidos ++;
      
        return id;
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
        //InterfaceProceso k = new ProcesoServer(Integer.parseInt(id), num_vecinos);
        Naming.rebind("Proceso" + id, k);
        
    }   catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}