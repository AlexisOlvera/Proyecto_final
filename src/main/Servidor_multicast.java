package main;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Servidor_multicast  implements Runnable{

    int puerto_servidor_RMI;

    public Servidor_multicast(int puerto_servidor_RMI) {
        this.puerto_servidor_RMI = puerto_servidor_RMI;
    }

    public void anunciar(){
        InetAddress gpo;
        try{
            MulticastSocket s= new MulticastSocket(9876);
            s.setReuseAddress(true);
            s.setTimeToLive(1500);
            String msj = puerto_servidor_RMI + "";
            byte[] b = msj.getBytes();
            gpo = InetAddress.getByName("228.1.1.1”");
            s.joinGroup(gpo);
            for(;;){
                DatagramPacket p = new DatagramPacket(b,b.length,gpo,9999);
                s.send(p);
                System.out.println("Enviando mensaje "+msj+ " con un TTL= "+s.getTimeToLive());
                try{
                    Thread.sleep(5);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        anunciar();
    }
}
