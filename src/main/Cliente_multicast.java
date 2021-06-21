package main;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Set;

public class Cliente_multicast implements Runnable{

    private Set<Id_serv_RMI> servidores_RMI;

    public Cliente_multicast(Set<Id_serv_RMI> servidores_RMI) {
        this.servidores_RMI = servidores_RMI;
    }

    private void escuchar(){
        InetAddress gpo=null;
        try{
            MulticastSocket cl= new MulticastSocket(9999);
            System.out.println("Cliente escuchando puerto "+ cl.getLocalPort());
            cl.setReuseAddress(true);
            try{
                gpo = InetAddress.getByName("228.1.1.1");
            }catch(UnknownHostException u){
                System.err.println("Direccion no valida");
            }//catch
            cl.joinGroup(gpo);
            System.out.println("Unido al grupo");
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[1000],1000);
                cl.receive(p);
                String msj = new String(p.getData());
                System.out.println("Datagrama recibido.."+msj);
                System.out.println("Servidor descubierto:" + p.getAddress()+" puerto:"+p.getPort());
                servidores_RMI.add(new Id_serv_RMI(Integer.parseInt(msj), p.getAddress().toString()));
            }//for
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }

    @Override
    public void run() {
        escuchar();
    }
}
