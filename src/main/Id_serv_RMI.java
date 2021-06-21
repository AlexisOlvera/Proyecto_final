package main;

public class Id_serv_RMI {
    private int puerto;
    private String ip;

    public Id_serv_RMI(int puerto, String ip) {
        this.puerto = puerto;
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getIp() {
        return ip;
    }
}
