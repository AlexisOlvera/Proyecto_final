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
    private boolean visitado;


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
        Id_serv_RMI id = new Id_serv_RMI(-1, "-1");
        if(visitado){
            visitado = false;
            return id;
        }
        visitado = true;
        System.err.println("Esta buscando en mi " + id_mio.obtener_puerto());
        if (buscar_dentro(nombre_buscar)) {
            id = id_mio; // Llenar datos ip y puerto
        } else {
            id = Cliente_RMI.buscar_siguiente_nodo(nombre_buscar, nodo_yo.obtener_siguiente()); //El cliente llama al servidor RMI del siguiente nodo
        }
        visitado = false;
        System.err.println("Encontraron " + id.obtener_puerto());
        return id;
    }

    Servidor_RMI(File directorio, Nodo nodo) {
        this.directorio = directorio;
        this.nodo_yo = nodo;
        this.id_mio = nodo.obtener_id();
    }

    @Override
    public void run() {
        try {
            //puerto default del rmiregistry
            java.rmi.registry.LocateRegistry.createRegistry(id_mio.obtener_puerto());
            System.out.println("RMI registro listo en el puerto "+ id_mio.obtener_puerto());
            System.out.println(java.rmi.registry.LocateRegistry.getRegistry(id_mio.obtener_puerto()));
        } catch (Exception e) {
            System.out.println("Excepcion RMI del registry:");
            e.printStackTrace();
        }//catch
        try {
            System.setProperty("java.rmi.server.codebase", "file:/c:/Users/alexi/server/");
            Servidor_RMI obj = new Servidor_RMI(directorio, nodo_yo);
            Buscar_archivo stub = (Buscar_archivo) UnicastRemoteObject.exportObject(obj, 0);
            // Ligamos el objeto remoto en el registro
            Registry registry = LocateRegistry.getRegistry(id_mio.obtener_puerto());
            registry.bind("Buscar_archivo", stub);

            System.err.println("Servidor listo...");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}