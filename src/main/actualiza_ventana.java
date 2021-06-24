package main;

import java.net.DatagramPacket;

public class Actualiza_ventana implements Runnable{
    private Ventana ven;
    public Actualiza_ventana(Ventana ventana){
        ven = ventana;
        ventana.HistorialLabel.setText(ventana.historial);
    }
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(;;){
            try{
                ven.HistorialLabel.setText(ven.historial);
                ven.SiguienteLabel.setText(ven.nodo.obtener_siguiente().obtener_ip() +":"+ ven.nodo.obtener_siguiente().obtener_puerto());
                ven.AnteriorLabel.setText(ven.nodo.obtener_anterior().obtener_ip() +":"+ ven.nodo.obtener_anterior().obtener_puerto());
                ven.ActivosLista.setListData((String[]) ven.nodo.ClienteMulticast.obtener_servidores_RMI().toArray());
                Thread.sleep(500);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
}
