package xadrezCamada;

import tabuleiroCamada.Tabuleiro;

//coração do jogo, aqui ficara as regras
public class XadrezPartida {

    private Tabuleiro tabuleiro;
    
    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);//aqui é onde informo as dimensoes do tabuleiro

    }

    public XadrezPeca[][] getPecas(){
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinha()][tabuleiro.getColuna()];

        for(int i=0; i< tabuleiro.getLinha(); i++){
            for(int j=0; j<tabuleiro.getColuna(); j++){
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i,j);
            }
        }
        return mat;
    }
}
