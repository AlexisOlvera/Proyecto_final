package main;

import java.io.Serializable;

public class Id_serv_RMI implements Comparable<Id_serv_RMI>, Serializable {
    private int puerto;
    private String ip;

    public Id_serv_RMI(int puerto, String ip) {
        this.puerto = puerto;
        this.ip = ip;
    }

    public int obtener_puerto() {
        return puerto;
    }

    public String obtener_ip() {
        return ip;
    }

    public String obtener_host() {
        return "rmi:/"+ip+":" + puerto + "/Buscar_archivo";
    }


    @Override
    public int compareTo(Id_serv_RMI o) {
        return (int)(this.puerto - o.puerto );
    }
}
