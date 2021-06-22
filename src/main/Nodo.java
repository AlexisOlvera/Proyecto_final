package main;

import java.io.File;
import java.util.Set;

public class Nodo{
    private Id_serv_RMI antes, siguiente, mio;
    private Cliente_RMI ClienteRMI;
    private Servidor_RMI ServidorRMI;

    private Cliente_multicast ClienteMulticast;
    private Servidor_multicast ServidorMulticast;

    private File directorio;
    Nodo(int puerto){
        mio = new Id_serv_RMI(puerto, "obtener_IP");
        directorio = crear_carpeta(puerto+"");


        ServidorMulticast = new Servidor_multicast(puerto);
        ClienteMulticast = new Cliente_multicast();

        //ClienteRMI = new ClienteRMI();
        ServidorRMI = new Servidor_RMI(directorio, mio);
        antes = obtener_id_antes();
        siguiente = obtener_id_siguiente();
    }


    private Id_serv_RMI obtener_id_antes(){
        Set<Id_serv_RMI> servidores_RMI = ClienteMulticast.obtener_servidores_RMI();
        int n_servidores = servidores_RMI.size();
        Id_serv_RMI[] id_servidores = new Id_serv_RMI[ n_servidores ];
        servidores_RMI.toArray(id_servidores);


        for(int i = 0; i < n_servidores; i++)
        {
            Id_serv_RMI id_servidor = id_servidores[i];
            if(id_servidor == mio)
            {
                return id_servidores[(i - 1 + n_servidores) % n_servidores];
            }
        }
        return new Id_serv_RMI(-1, "-1");
    }

    private Id_serv_RMI obtener_id_siguiente() {
        Set<Id_serv_RMI> servidores_RMI = ClienteMulticast.obtener_servidores_RMI();
        int n_servidores = servidores_RMI.size();
        Id_serv_RMI[] id_servidores = new Id_serv_RMI[ n_servidores ];
        servidores_RMI.toArray(id_servidores);


        for(int i = 0; i < id_servidores.length; i++)
        {
            Id_serv_RMI id_servidor = id_servidores[i];
            if(id_servidor == mio)
            {
                return id_servidores[(i + 1) % n_servidores];
            }
        }
        return new Id_serv_RMI(-1, "-1");
    }

    public Id_serv_RMI obtener_id(){
        return mio;
    }

    public  Id_serv_RMI obtener_siguiente(){
        return siguiente;
    }

    public Id_serv_RMI obtner_anterior(){
        return antes;
    }


    public void buscar(String nombre)    {
        Id_serv_RMI id = ClienteRMI.buscar_siguiente_nodo(nombre, siguiente);
    }

    public File crear_carpeta(String puerto){
        String nueva_carpeta = "Carpetas/" + puerto ;
        File carpeta = new File(nueva_carpeta);

        if(carpeta.mkdirs()){
            System.out.println("Ahuevo, si se creo");
        }
        else{
            System.out.println("No le sabes");
        }

        return carpeta;
    }


}