//import java.rmi.RemoteException;
import java.rmi.Remote;
//import java.rmi.Naming;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class Proceso  extends UnicastRemoteObject implements InterfaceProceso
{

    public Proceso()
    throws RemoteException
  {}
    
    public  String eco(String id)
    throws RemoteException, Exception
    {
        return "funciona";
    }

    public static void main(String[] args)
    throws Exception
  {
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }

    try
    {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        System.setProperty("java.security.policy","file:./Proceso.policy");
        InterfaceProceso k = new Proceso();
        Naming.rebind("Proceso", k);
        System.out.println("Proceso Registrado.");
    }   catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}