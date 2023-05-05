package xadrezPeca;

import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezCamada.Color;
import xadrezCamada.XadrezPartida;
import xadrezCamada.XadrezPeca;

public class Rei extends XadrezPeca{

    private XadrezPartida xadrezPartida;

    public Rei(Tabuleiro tabuleiro, Color color, XadrezPartida xadrezPartida) {
        super(tabuleiro, color);
        this.xadrezPartida = xadrezPartida;
       
    }

    public String toString(){
        return "R";
    }

    private boolean podeMover(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    private boolean testRook(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getColor() == getColor() && p.getMoveCount() ==0;

    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        //cima
        Posicao p = new Posicao(0, 0);
        p.setValues(posicao.getLinha() -1, posicao.getColuna());
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //abaixo
        p.setValues(posicao.getLinha() +1, posicao.getColuna());
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //esquerda
        p.setValues(posicao.getLinha(), posicao.getColuna()-1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //direita
        p.setValues(posicao.getLinha(), posicao.getColuna()+1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //noroeste
        p.setValues(posicao.getLinha()-1, posicao.getColuna()-1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //nordeste
        p.setValues(posicao.getLinha()-1, posicao.getColuna()+1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //sudoeste
        p.setValues(posicao.getLinha()+1, posicao.getColuna()-1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //sudeste
        p.setValues(posicao.getLinha()-1, posicao.getColuna()+1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //jogada especial de roque

        if(getMoveCount() == 0 && !xadrezPartida.getCheck()){
            //jogada espeicla roque pequeno
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if(testRook(posT1)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true; 
                }
            }
              //jogada espeicla roque grande
              Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4 );
              if(testRook(posT2)){
                  Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
                  Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() -2);
                  Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() -3);
                  if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
                      mat[posicao.getLinha()][posicao.getColuna() - 2] = true; 
                  }
              }
        }
        return mat;
    }

    
}
