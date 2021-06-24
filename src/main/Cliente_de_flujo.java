package main;

import java.io.*;
import java.net.Socket;

public class Cliente_de_flujo implements Runnable{
    private Id_serv_RMI id_serv;
    private String nombre_ar;
    File directorio;
    Historial historial;
    public Cliente_de_flujo(Id_serv_RMI id_serv, String nombre_ar, File directorio, Historial historial){
        this.id_serv = id_serv;
        this.nombre_ar = nombre_ar;
        this.directorio = directorio;
        this.historial = historial;
    }
    @Override
    public void run() {
        try {
            Socket cl = new Socket(id_serv.obtener_ip(), id_serv.obtener_puerto()+100);
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            dos.writeUTF(nombre_ar);
            dos.flush();
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            String nombre = dis.readUTF();
            long tam = dis.readLong();
            DataOutputStream dos_archivo = new DataOutputStream(new FileOutputStream(directorio.getAbsolutePath()+"/"+nombre));
            long recibidos=0;
            int n;
            byte[] b = new byte[1024];
            historial.porcentaje=0;
            while(recibidos < tam){
                n = dis.read(b);
                dos_archivo.write(b,0,n);
                dos_archivo.flush();
                recibidos = recibidos + n;
                historial.porcentaje = (int)(recibidos*100/tam);
            }//While
            System.out.print("\n\nArchivo recibido.\n");
            dos.close();
            dis.close();
            dos_archivo.close();
            cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
