package application;

import xadrezCamada.XadrezPeca;

//user interface, essa classe vai receber a matriz de peças da partida
public class UI {
    //metodo que cria a matriz
    public static void printTabuleiro(XadrezPeca[][] pecas){
        for(int i=0; i<pecas.length; i++){
            System.out.print((8-i) + " ");//fazer a coluna com numeros
            for(int j=0; j<pecas.length; j++){
                printPeca(pecas[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");//fazer a linha com as letras

    }
    //imprimir uma peça
    private static void printPeca(XadrezPeca peca){
        if (peca == null){
            System.out.print("-");
        }
        else{
            System.out.print(peca);
        }
        System.out.print(" ");
    }
}
