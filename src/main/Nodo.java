package main;

import java.io.File;
import java.util.Set;

public class Nodo{
    private Id_serv_RMI antes, siguiente, mio;
    private Cliente_RMI ClienteRMI;
    private Servidor_RMI ServidorRMI;

    public Cliente_multicast ClienteMulticast;
    private Servidor_multicast ServidorMulticast;
    private Servidor_de_flujo ServidorFlujo;
    private File directorio;
    private Historial historial;
    Nodo(int puerto) throws InterruptedException {
        mio = new Id_serv_RMI(puerto, "127.0.0.1");
        historial = new Historial();
        directorio = crear_carpeta(puerto+"");
        ServidorMulticast = new Servidor_multicast(puerto);
        ClienteMulticast = new Cliente_multicast(this);
        ServidorRMI = new Servidor_RMI(directorio, this, historial);
        ServidorFlujo = new Servidor_de_flujo(puerto+100, directorio, historial);
        new Thread(ServidorMulticast).start();
        new Thread(ClienteMulticast).start();
        new Thread(ServidorRMI).start();
        new Thread(ServidorFlujo).start();
//        Thread.sleep(7000);
    }


    public void actualizar_antes(){
        Set<Id_serv_RMI> servidores_RMI = ClienteMulticast.obtener_servidores_RMI();
        int n_servidores = servidores_RMI.size();
        Id_serv_RMI[] id_servidores = new Id_serv_RMI[ n_servidores ];
        servidores_RMI.toArray(id_servidores);
        for(int i = 0; i < n_servidores; i++) {
            Id_serv_RMI id_servidor = id_servidores[i];
            if(id_servidor.obtener_puerto() == mio.obtener_puerto())
            {
                antes = id_servidores[(i - 1 + n_servidores) % n_servidores];
            }
        }
    }

    public void actualizar_siguiente() {
        Set<Id_serv_RMI> servidores_RMI = ClienteMulticast.obtener_servidores_RMI();
        int n_servidores = servidores_RMI.size();
        Id_serv_RMI[] id_servidores = new Id_serv_RMI[n_servidores];
        servidores_RMI.toArray(id_servidores);


        for(int i = 0; i < id_servidores.length; i++){
            Id_serv_RMI id_servidor = id_servidores[i];
            if(id_servidor.obtener_puerto() == mio.obtener_puerto()) {
                siguiente = id_servidores[(i + 1) % n_servidores];
            }
        }
    }

    public Id_serv_RMI obtener_id(){
        return mio;
    }

    public  Id_serv_RMI obtener_siguiente(){
        return siguiente;
    }

    public Id_serv_RMI obtener_anterior(){
        return antes;
    }


    public Id_serv_RMI buscar(String nombre)    {
        Id_serv_RMI id = ClienteRMI.buscar_siguiente_nodo(nombre, siguiente);
        return id;
    }

    public File crear_carpeta(String puerto){
        String nueva_carpeta = "Carpetas/" + puerto ;
        File carpeta = new File(nueva_carpeta);

        return carpeta;
    }

    public File obtener_directorio(){
        return directorio;
    }

    public Servidor_RMI obterner_serv_RMI(){
        return ServidorRMI;
    }

    public Servidor_de_flujo obtener_ServidorFlujo() {
        return ServidorFlujo;
    }

    public Historial obtener_historial() {
        return historial;
    }
}