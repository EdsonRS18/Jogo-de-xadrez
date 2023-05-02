package xadrezCamada;

import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezPeca.Rei;
import xadrezPeca.Torre;

//coração do jogo, aqui ficara as regras
public class XadrezPartida {

    private Tabuleiro tabuleiro;
    
    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);//aqui é onde informo as dimensoes do tabuleiro
        setupInicial();
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

    private void setupInicial(){
       tabuleiro.localPeca(new Torre(tabuleiro, Color.WHITE), new Posicao(0, 0));
       tabuleiro.localPeca(new Rei(tabuleiro, Color.WHITE), new Posicao(0, 4));
       tabuleiro.localPeca(new Rei(tabuleiro, Color.BLACK), new Posicao(7, 4));
    }
}
