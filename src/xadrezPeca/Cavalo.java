package xadrezPeca;

import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezCamada.Color;
import xadrezCamada.XadrezPeca;

public class Cavalo extends XadrezPeca{

    public Cavalo(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
       
    }

    public String toString(){
        return "C";
    }

    private boolean podeMover(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

      
        Posicao p = new Posicao(0, 0);
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

     
        p.setValues(posicao.getLinha() -2, posicao.getColuna()-1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        
        p.setValues(posicao.getLinha()-2, posicao.getColuna()+1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
      
        p.setValues(posicao.getLinha() -1, posicao.getColuna()+2);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
       
        p.setValues(posicao.getLinha()+1, posicao.getColuna()+2);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //nordeste
        p.setValues(posicao.getLinha()+2, posicao.getColuna()+1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //sudoeste
        p.setValues(posicao.getLinha()+2, posicao.getColuna()-1 );
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //sudeste
        p.setValues(posicao.getLinha()+1, posicao.getColuna()-2);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }

    
}
