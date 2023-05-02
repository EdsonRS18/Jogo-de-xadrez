package xadrezPeca;

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
    
}
