package main;

public class actualiza_ventana implements Runnable{

    public actualiza_ventana(Ventana ventana){
        ventana.HistorialLabel.setText(ventana.historial);
    }
    @Override
    public void run() {

    }
}
