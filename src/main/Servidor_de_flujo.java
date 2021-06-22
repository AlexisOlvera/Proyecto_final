package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_de_flujo implements Runnable {

    private int puerto;

    public Servidor_de_flujo(int puerto) {
        this.puerto = puerto;
    }

    private File archivo(String nombre){
        File ar = new File(nombre);
        return ar;
    }

    @Override
    public void run() {
        try {
            ServerSocket s = new ServerSocket(puerto);
            // Iniciamos el ciclo infinito del servidor
            for (; ; ) {
                // Esperamos una conexión
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde" + cl.getInetAddress() + ":" + cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre_archivo = dis.readUTF();
                System.out.println("Recibimos el archivo:" + nombre_archivo);
                long tam = dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre_archivo));
                long recibidos = 0;
                int n, porcentaje;
                while (recibidos < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int) (recibidos * 100 / tam);
                    System.out.print("Recibido: " + porcentaje + "%\r");
                }//While
                System.out.print("\n\nArchivo recibido.\n");
                dos.close();
                dis.close();
                cl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }
}
