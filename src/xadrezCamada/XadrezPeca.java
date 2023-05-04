package xadrezCamada;

import tabuleiroCamada.Peca;
import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;

public abstract class XadrezPeca extends Peca{

    private Color color;

    public XadrezPeca(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean temPecaOponente(Posicao posicao){
        XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
        return p != null && p.getColor() != color;
    
    }

   

    


    
}
