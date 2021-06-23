package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_de_flujo implements Runnable {

    private int puerto;
    private File directorio;

    public Servidor_de_flujo(int puerto, File directorio) {
        this.puerto = puerto;
        this.directorio = directorio;
    }

    private File archivo(String nombre){
        File ar = new File(directorio.getAbsolutePath() + "/" + nombre);
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
                String nombre_archivo = dis.readUTF();
                System.out.println("Recibimos el archivo:" + nombre_archivo);
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                File f = archivo(nombre_archivo);
                DataInputStream dis_archivo = new DataInputStream(new FileInputStream(f));
                long enviados = 0;
                long tam = f.length();  //Tamaño
                dos.writeUTF(nombre_archivo);
                dos.flush();
                dos.writeLong(tam);
                dos.flush();
                byte[] b = new byte[1024];
                int porcentaje, n;
                while (enviados < tam){
                    n = dis_archivo.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    enviados = enviados+n;
                    porcentaje = (int)(enviados*100/tam);
                    System.out.print("Enviado: "+porcentaje+"%\r");
                }//While
                System.out.print("\n\nArchivo enviado");
                dos.close();
                dis.close();
                cl.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }
}
