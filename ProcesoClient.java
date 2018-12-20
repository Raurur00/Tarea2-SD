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
    public int num_mensajes;
    public boolean sendExplorer;
    public boolean sendEco;
    public boolean sendMenasje;
    public int FL;
    public boolean iniciador;
    public boolean coordinador;
    public String mensajeDescifrado;
    public String mensajeCifrado;
    public String uk;

    public ProcesoClient(int id, int num_vecinos) 
    throws RemoteException
    {
        this.id = id;
        this.idCoordinador = -1;
        this.num_vecinos = num_vecinos;
        this.sendExplorer = false;
        this.sendEco = false;
        this.sendMensaje = false;
        this.FL = -1;
        this.iniciador = false;
        this.coordinador = false;
        this.num_mensajes = 0;
        this.mensajeDescifrado = "";
        this.mensajeCifrado = "";
        this.uk = "";

    }
    public void sendMensaje(String id, String mensaje)
    throws RemoteException, Exception
    {
        this.mensajeDescifrado = mensaje;
        this.sendMensaje = true;
    }


    public  String eco(String id)
  	throws RemoteException, Exception
  	{
        if (Integer.parseInt(id) == this.idCoordinador)
        {
            this.num_mensajes++;
            System.out.println("Nodo " + Integer.toString(this.id) + " recibe eco coordinador: " + id 
            + ", mensajes: " + this.num_mensajes);
            
            /*
            Si llega un eco se suma 1 al contador de mensajes
            Si es iniciador y el numero de mensajes recibidos es igual al numero de vecinos,
            el nodo es el nuevo coordinador 
            */
            if (this.num_mensajes == this.num_vecinos)
            {
                if (this.iniciador){
                    if (this.num_mensajes == this.num_vecinos){
                        this.coordinador = true;
                        System.out.println("Soy Coordinador (" + this.id + ")");
                    }
                } else {
                    /*
                    Si no es iniciador y el numero de mensajes recibidos es igual al numero de vecinos menos 1,
                    el nodo mando un eco 
                    */
                    this.sendEco = true;
                }   
            }
            
        }
        else if (Integer.parseInt(id) < this.idCoordinador)
        {
            System.out.println("Nodo " + Integer.toString(this.id) + " recibe eco coordinador: " + id +
            ", Se extingue");
        }
        
      	return id;
  	}
    
    public  void explorer(String idCoord, String idNodo)
  	throws RemoteException, Exception
  	{
        /* 
        Si el coordinador recibido es mayor al coordiandor actual:
        - Cambia al coordiandor
        - Cambia el First Link a la id del nodo de donde recibio el mensaje
        - Pone el contador de ecos en 0
        - Manda explorers a los vecions
        */
    	if (Integer.parseInt(idCoord) > this.idCoordinador) 
        {
	    this.iniciador = false;
            this.idCoordinador = Integer.parseInt(idCoord);
            System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer de " + idNodo +
            ", Nuevo Coordinador: " + Integer.toString(this.idCoordinador));
            this.FL = Integer.parseInt(idNodo);
            this.num_mensajes = 1;
            this.sendExplorer = true;
            /*Si el nodo solo tiene un vecinos y recibe un explorer, manda un eco */
            if (this.num_vecinos == 1 ){
                this.sendEco = true;
            } 
        } 
        /* 
        Si el coordinador recibido es igual al coordiandor actual:
        - Suma 1 al contador de ecos
        - Si es iniciador verifica si el numero de vecinos es igual al numero de ecos
        - Si es igual, se vuelver coordiandor
        - Si no es iniciador verifica si el numero de vecinos menos 1 es igual al numero de ecos
        - Si es igual manda un eco
        */
        else if (Integer.parseInt(idCoord) == this.idCoordinador) {
            System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer de " + idNodo + ", mensajes: " + this.num_mensajes);
            this.num_mensajes++;
            if (this.num_mensajes == (this.num_vecinos))
            {
                if (this.iniciador)
                {
                    this.coordinador = true;
                    System.out.println("Soy Coordinador (" + this.id + ")");
                }   else    {
                    this.sendEco = true;
                }
            
            }
            /*Si el nodo solo tiene un vecinos y recibe un explorer, manda un eco */
            if (this.num_vecinos == 1 ){
                this.sendEco = true;
            }     
        }   else    {
            System.out.println("Nodo " + Integer.toString(this.id) + " recibe explorer coordinador: " + idCoord +
            ", Se extingue");
        }
    }

    public void invocar(String id, int metodo, String mensaje) 
    {
        boolean flag = false;
        do
        {
            String host = "127.0.0.1";
            try {
                Registry registry = LocateRegistry.getRegistry(host);
                InterfaceProceso stub = (InterfaceProceso) registry.lookup("Proceso" + id);
                if (metodo == 0)
                {
                    stub.explorer(Integer.toString(this.idCoordinador),Integer.toString(this.id));
                    System.out.println("Nodo " + this.id + " manda explorer a " + id +"(ID Coordinador:" + this.idCoordinador + ")");
                
                }   else if (metodo == 1)   {
                    stub.eco(Integer.toString(this.idCoordinador));
                    System.out.println("Nodo " + this.id + " manda eco a " + id);
                }   else    {
                    stub.sendMensaje(Integer.toString(this.idCoordinador), mensaje);
                    System.out.println("Nodo " + this.id + " manda mensaje a " + id);
                }
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
    }
    
    public static void listen(String id, InterfaceProceso k)
  	throws Exception
  	{
    	System.setProperty("java.security.policy","Proceso.policy");
     
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
    public void Descifrar(String ruta_archivo, String ip_server) {

        BufferedReader br = null;
        FileReader fr = null;
        String mensajeCifrado = "";
        String numberGroup = "grupo_17";
        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(ruta_archivo);
            br = new BufferedReader(fr);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                mensajeCifrado += sCurrentLine;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            Registry registry = LocateRegistry.getRegistry(ip_server);
            InterfaceServer stub = (InterfaceServer) registry.lookup("PublicKey");
            this.mensajeCifrado = mensajeCifrado;
            this.uk = stub.getKey(numberGroup);
            this.mensajeDescifrado = stub.decipher(numberGroup, mensajeCifrado, uk);
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }
}
