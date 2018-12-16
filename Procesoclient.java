import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProcesoClient {

    public ProcesoClient() {}

    public static void invocar(String id) {

        while(true)
        {
            String host = "127.0.0.1";
            try {
                Registry registry = LocateRegistry.getRegistry(host);
                InterfaceProceso stub = (InterfaceProceso) registry.lookup("Proceso" + id);
                String response = stub.eco(id);
                System.out.println("response: " + response);
                break;
            } catch (Exception e) {
                System.err.println("Client exception: " );//+ e.toString());
                //e.printStackTrace();
            }
        }
    }
}