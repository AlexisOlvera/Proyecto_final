package main;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Servidor_RMI implements Runnable, Buscar_archivo {
    @Override
    public Id_serv_RMI buscar(String nombre) {
        Id_serv_RMI id = new Id_serv_RMI(500, "54");
        return id;
    }

    Servidor_RMI(){}

    @Override
    public void run() {
        try {
            //puerto default del rmiregistry
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registro listo.");
        } catch (Exception e) {
            System.out.println("Excepcion RMI del registry:");
            e.printStackTrace();
        }//catch
        try {
            System.setProperty("java.rmi.server.codebase","file:/c:/Temp/Buscar_archivo/");
            Servidor_RMI obj = new Servidor_RMI();
            Buscar_archivo stub = (Buscar_archivo) UnicastRemoteObject.exportObject(obj, 0);
            // Ligamos el objeto remoto en el registro
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Buscar_archivo", stub);

            System.err.println("Servidor listo...");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
