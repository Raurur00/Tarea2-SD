import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProcesoClient {
    public int id;

    public ProcesoClient(int id) 
    {
        this.id = id;
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
                String response = stub.explorer(Integer.toString(this.id));
                System.out.println(this.id + " manda explorer a " + id);
                flag = false;
            } catch (Exception e) {
                flag = true;
            }
        } while (flag);
        
    }
}