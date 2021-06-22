package main;

import java.rmi.Remote;

public interface Buscar_archivo extends Remote {
    Id_serv_RMI buscar(String nombre);
}
