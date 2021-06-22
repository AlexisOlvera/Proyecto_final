package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Buscar_archivo extends Remote {
    Id_serv_RMI buscar(String nombre) throws RemoteException;
}
