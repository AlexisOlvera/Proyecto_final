package main;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

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
            s.setTimeToLive(1);
            int puerto = puerto_servidor_RMI;
            byte[] b =  ByteBuffer.allocate(4).putInt(puerto).array();
            gpo = InetAddress.getByName("228.1.1.1");
            s.joinGroup(gpo);
            for(;;){
                DatagramPacket p = new DatagramPacket(b,b.length,gpo,9999);
                s.send(p);
                try{
                    Thread.sleep(500);
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
