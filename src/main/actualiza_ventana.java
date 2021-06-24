package main;

import javax.swing.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Set;

public class Actualiza_ventana implements Runnable{
    private Ventana ven;
    public Actualiza_ventana(Ventana ventana){
        ven = ventana;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(;;){
            try{
                ven.HistorialLabel.setText("<html>" +  ven.nodo.obtener_historial().historial +  "</html>;");
                ven.SiguienteLabel.setText(ven.nodo.obtener_siguiente().obtener_ip() +":"+ ven.nodo.obtener_siguiente().obtener_puerto());
                ven.AnteriorLabel.setText(ven.nodo.obtener_anterior().obtener_ip() +":"+ ven.nodo.obtener_anterior().obtener_puerto());
                ven.PorcentajeLabel.setText("<html><span style=\"color:green\">" +ven.nodo.obtener_historial().porcentaje+"%</span><html>");
                DefaultListModel listModel = new DefaultListModel();
                Set<Id_serv_RMI> servidores_RMI = ven.nodo.ClienteMulticast.obtener_servidores_RMI();
                int n_servidores = servidores_RMI.size();
                Id_serv_RMI[] arr = new Id_serv_RMI[n_servidores];
                servidores_RMI.toArray(arr);
                for (int i = 0; i < arr.length; i++)
                {
                    listModel.addElement(arr[i].obtener_ip() + ":" + arr[i].obtener_puerto());
                }
                ven.ActivosLista.setModel(listModel);
                Thread.sleep(500);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
}
