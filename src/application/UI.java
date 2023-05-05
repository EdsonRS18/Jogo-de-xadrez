package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import tabuleiroCamada.Peca;
import xadrezCamada.Color;
import xadrezCamada.XadrezPartida;
import xadrezCamada.XadrezPeca;
import xadrezCamada.XadrezPosicao;

//user interface, essa classe vai receber a matriz de peças da partida
public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }    

    public static XadrezPosicao lerXadrezPosicao(Scanner sc){
        try {
            String s =  sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new XadrezPosicao(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("erro na leitura da posicao, entradas validas de a1 ate h8");  
        }
    }

    public static void printPartida(XadrezPartida xadrezPartida, List<XadrezPeca> capturada){
        printTabuleiro(xadrezPartida.getPecas());
        System.out.println();
        printPecasCapturadas(capturada);
        System.out.println();
        System.out.println("turno: " + xadrezPartida.getTurno());
        if (!xadrezPartida.getCheckMate()) {
			System.out.println("Esperando jogador: " + xadrezPartida.getJogador());
			if (xadrezPartida.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + xadrezPartida.getJogador());
	    }
    }

    
    //metodo que cria a matriz
    public static void printTabuleiro(XadrezPeca[][] pecas){
        for(int i=0; i<pecas.length; i++){
            System.out.print((8-i) + " ");//fazer a coluna com numeros
            for(int j=0; j<pecas.length; j++){
                printPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");//fazer a linha com as letras
    }
    //possiveis posicoes
    public static void printTabuleiro(XadrezPeca[][] pecas, boolean[][] possivelMovimento){
        for(int i=0; i<pecas.length; i++){
            System.out.print((8-i) + " ");//fazer a coluna com numeros
            for(int j=0; j<pecas.length; j++){
                printPeca(pecas[i][j], possivelMovimento[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");//fazer a linha com as letras

    }
    //imprimir uma peça
    private static void printPeca(XadrezPeca peca, boolean background) {
        if(background ==true){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
    	if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (peca.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}

    private static void printPecasCapturadas(List<XadrezPeca> capturada){
        List<XadrezPeca> white = capturada.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<XadrezPeca> black = capturada.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("pecas capturadas");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);   
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
    
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);   
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    
    }   
}
