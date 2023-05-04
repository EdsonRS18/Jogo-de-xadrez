package xadrezPeca;

import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezCamada.Color;
import xadrezCamada.XadrezPeca;

public class Torre extends XadrezPeca{

    public Torre(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override 
    public String toString(){
        return "T";
    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
 
        //para cima
        Posicao p = new Posicao(0, 0);
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        } 
        if (getTabuleiro().posicaoExiste(p )&& temPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //para esquerda

        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        } 
        if (getTabuleiro().posicaoExiste(p )&& temPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //direita
        p.setValues(posicao.getLinha(), posicao.getColuna() +1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        } 
        if (getTabuleiro().posicaoExiste(p )&& temPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        } 
        if (getTabuleiro().posicaoExiste(p )&& temPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }




         return mat;
    }
    
}
