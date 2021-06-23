package main;

public class Actualiza_ventana implements Runnable{

    public Actualiza_ventana(Ventana ventana){
        ventana.HistorialLabel.setText(ventana.historial);
    }
    @Override
    public void run() {

    }
}
