package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrezCamada.XadrezException;
import xadrezCamada.XadrezPartida;
import xadrezCamada.XadrezPeca;
import xadrezCamada.XadrezPosicao;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();
        List<XadrezPeca> capturadas = new ArrayList<>(); 

        while(!xadrezPartida.getCheckMate()){
            try{
                UI.clearScreen();
                UI.printPartida(xadrezPartida, capturadas);
                System.out.print("origem: ");
                XadrezPosicao origem = UI.lerXadrezPosicao(sc);

                boolean[][] possivelMovimento = xadrezPartida.possivelMovimento(origem);
                UI.clearScreen();
                UI.printTabuleiro(xadrezPartida.getPecas(), possivelMovimento);
                System.out.println();
                System.out.print("alvo: ");
                XadrezPosicao alvo = UI.lerXadrezPosicao(sc);

                XadrezPeca pecaCapturada = xadrezPartida.performXadrezMove(origem, alvo);
                if (pecaCapturada    != null){
                    capturadas.add(pecaCapturada);
                }

                
				if (xadrezPartida.getPromocao() != null) {
					System.out.print("Entre com a promocao (B/C/T/Q): ");
					String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("Q")){
                        System.out.print("Entada invalida. Entre com a promocao (B/C/T/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    xadrezPartida.substituirPeca(type);
				}
                
            }catch(XadrezException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
		UI.printPartida(xadrezPartida, capturadas);
    }
    
}
 