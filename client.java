import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class client {

    private client() {}
    //Conexión con servidor (Esto debe ser ejecutado por el proceso coordinador)
    public static void main(String[] args) {

        String host = "10.10.2.214";
        String numberGroup = "grupo_17";
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            InterfaceServer stub = (InterfaceServer) registry.lookup("PublicKey");
            String response = stub.getKey(numberGroup);
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}