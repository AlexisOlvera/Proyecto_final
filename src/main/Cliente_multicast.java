package main;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Cliente_multicast implements Runnable{

    private Set<Id_serv_RMI> servidores_RMI;
    private Nodo mio;

    public Cliente_multicast(Nodo mio) {
        this.servidores_RMI = new TreeSet<>();
        this.mio = mio;
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
                DatagramPacket p = new DatagramPacket(new byte[4],4);
                cl.receive(p);
                ByteBuffer wrapped = ByteBuffer.wrap(p.getData()); // big-endian by default
                int puerto = wrapped.getInt();
                System.out.println("Datagrama recibido.."+puerto);
                System.out.println("Servidor descubierto:" + p.getAddress()+" puerto:"+p.getPort());
                servidores_RMI.add(new Id_serv_RMI(puerto, p.getAddress().toString()));
                mio.actualizar_siguiente();
                mio.actualizar_antes();
                System.out.println("siguiente : " + mio.obtener_siguiente().obtener_puerto());
                System.out.println("anterior : " + mio.obtener_anterior().obtener_puerto());
            }//for
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }

    @Override
    public void run() {
        escuchar();
    }

    public Set<Id_serv_RMI> obtener_servidores_RMI() {
        return servidores_RMI;
    }
}
