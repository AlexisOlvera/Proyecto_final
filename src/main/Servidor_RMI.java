package main;

import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor_RMI implements Runnable, Buscar_archivo {

    private Id_serv_RMI id_mio;
    private File directorio;
    private Nodo nodo_yo;

    private boolean buscar_dentro(String nombre_buscar) {
        File[] archivos = this.directorio.listFiles();

        for (File archivo : archivos) {
            if (archivo.getName().equals(nombre_buscar)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Id_serv_RMI buscar(String nombre_buscar) throws RemoteException {
        Id_serv_RMI id = new Id_serv_RMI(-1, "54");

        if (buscar_dentro(nombre_buscar)) {
            id = nodo_yo.obtener_id(); // Llenar datos ip y puerto
        } else {
            id = Cliente_RMI.buscar_siguiente_nodo(nombre_buscar, nodo_yo.obtener_siguiente()); //El cliente llama al servidor RMI del siguiente nodo
        }
        return id;
    }

    Servidor_RMI(File directorio, Id_serv_RMI id) {
        this.directorio = directorio;
        this.id_mio = id;
    }

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
            System.setProperty("java.rmi.server.codebase", "file:/c:/Temp/Buscar_archivo/");
            Servidor_RMI obj = new Servidor_RMI(directorio, id_mio);
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