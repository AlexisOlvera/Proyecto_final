package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente_de_flujo implements Runnable{
    private Id_serv_RMI id_serv;
    private String nombre_ar;
    public Cliente_de_flujo(Id_serv_RMI id_serv, String nombre_ar){
        this.id_serv = id_serv;
        this.nombre_ar = nombre_ar;
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
            DataOutputStream dos_archivo = new DataOutputStream(new FileOutputStream(nombre));
            long recibidos=0;
            int n, porcentaje;
            byte[] b = new byte[1024];
            while(recibidos < tam){
                n = dis.read(b);
                dos_archivo.write(b,0,n);
                dos_archivo.flush();
                recibidos = recibidos + n;
                porcentaje = (int)(recibidos*100/tam);
                System.out.print("Recibido: "+porcentaje+"%\r");
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
