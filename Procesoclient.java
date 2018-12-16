import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Procesoclient {

    private Procesoclient() {}

    public static void main(String[] args) {

        String host = "127.0.0.1";
        String numberGroup = "grupo_17";
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            InterfaceProceso stub = (InterfaceProceso) registry.lookup("Proceso");
            //String response = stub.eco(numberGroup);
            //System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}