package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente_RMI {
    private Cliente_RMI() {}

    public static Id_serv_RMI buscar_siguiente_nodo(String nombre, Id_serv_RMI nodo_siguiente) {
        Id_serv_RMI response = null;
        try {
            Registry registry = LocateRegistry.getRegistry(nodo_siguiente.obtener_puerto());
            Buscar_archivo stub = (Buscar_archivo) registry.lookup("Buscar_archivo");
            response = stub.buscar(nombre);
            System.out.println("respuesta buscar " + response);
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " +e.toString());
            e.printStackTrace();
        }
        return response;
    }
}

