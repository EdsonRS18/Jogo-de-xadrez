package application;

import java.util.Scanner;

import xadrezCamada.XadrezPartida;
import xadrezCamada.XadrezPeca;
import xadrezCamada.XadrezPosicao;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();

        while(true){
            UI.printTabuleiro(xadrezPartida.getPecas());
            System.out.println();
            System.out.print("origem: ");
            XadrezPosicao origem = UI.lerXadrezPosicao(sc);

            System.out.println();
            System.out.print("alvo: ");
            XadrezPosicao alvo = UI.lerXadrezPosicao(sc);

            XadrezPeca pecaCapturada = xadrezPartida.performXadrezMove(origem, alvo);

        }
        
    }
    
}
 