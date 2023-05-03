package xadrezCamada;

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
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for(int i=0; i< tabuleiro.getLinhas(); i++){
            for(int j=0; j<tabuleiro.getColunas(); j++){
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i,j);
            }
        }
        return mat;
    }
    //recebe as coordenadas do xadrez e converte para matriz
    
    private void lugarNovaPeca(char coluna, int linha, XadrezPeca peca){
        tabuleiro.localPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }
    //agora posso instanciar as peças ja em posicoes de xadrez chamando a funcao lugarNovaPeca
    private void setupInicial(){
       lugarNovaPeca('a', 8, new Torre(tabuleiro, Color.WHITE));
       lugarNovaPeca('e', 8, new Rei(tabuleiro, Color.WHITE));
       lugarNovaPeca('e', 1, new Rei(tabuleiro, Color.BLACK));
    }
}
