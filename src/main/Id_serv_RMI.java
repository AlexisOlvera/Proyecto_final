package main;

public class Id_serv_RMI {
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
        return "rmi://"+ip+":" + puerto + "/buscar";
    }


}
