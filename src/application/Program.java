package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrezCamada.XadrezException;
import xadrezCamada.XadrezPartida;
import xadrezCamada.XadrezPeca;
import xadrezCamada.XadrezPosicao;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();

        while(true){
            try{
                UI.clearScreen();
                UI.printTabuleiro(xadrezPartida.getPecas());
                System.out.println();
                System.out.print("origem: ");
                XadrezPosicao origem = UI.lerXadrezPosicao(sc);

                boolean[][] possivelMovimento = xadrezPartida.possivelMovimento(origem);
                UI.clearScreen();
                UI.printTabuleiro(xadrezPartida.getPecas(), possivelMovimento);
                
                System.out.println();
                System.out.print("alvo: ");
                XadrezPosicao alvo = UI.lerXadrezPosicao(sc);

                XadrezPeca pecaCapturada = xadrezPartida.performXadrezMove(origem, alvo);
                
            }catch(XadrezException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        
    }
    
}
 