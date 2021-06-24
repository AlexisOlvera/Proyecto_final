package main;

import javax.swing.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n_nodos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de nodos"));
        Ventana ventanas[] = new Ventana[n_nodos];
        for(int i = 0; i<n_nodos; i++){
            ventanas[i] = new Ventana(new Nodo(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto"))));
        }
    }
}