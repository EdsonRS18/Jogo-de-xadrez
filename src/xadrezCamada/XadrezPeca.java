package xadrezCamada;

import tabuleiroCamada.Peca;
import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;

public abstract class XadrezPeca extends Peca{

    private Color color;
    private int moveCount;

    public XadrezPeca(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public void incrementarCount(){
        moveCount++;
    }

    public void decrementarCount(){
        moveCount--;
    }

    public XadrezPosicao getXadrezPosicao(){
        return XadrezPosicao.fromPosicao(posicao);
    }

    protected boolean temPecaOponente(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p != null && p.getColor() != color;
    
    }

   

    


    
}
