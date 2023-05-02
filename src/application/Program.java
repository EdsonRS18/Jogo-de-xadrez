package application;

import xadrezCamada.XadrezPartida;

public class Program {
    public static void main(String[] args) {
        XadrezPartida xadrezPartida = new XadrezPartida();
        UI.printTabuleiro(xadrezPartida.getPecas());
    }
    
}
