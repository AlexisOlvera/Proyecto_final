package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente_RMI implements Runnable {
    private Cliente_RMI() {}

    @Override
    public void run() {
        String host = "hola";
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            //también puedes usar getRegistry(String host, int port)
            Buscar_archivo stub = (Buscar_archivo) registry.lookup("Suma");

            int x=5,y=4;
            Id_serv_RMI response = stub.buscar("hola");
            System.out.println("respuesta sumar "+x+" y "+y+" : " + response);
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " +e.toString());
            e.printStackTrace();
        }
    }
}
