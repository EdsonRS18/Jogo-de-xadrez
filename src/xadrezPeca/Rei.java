package xadrezPeca;

import tabuleiroCamada.Tabuleiro;
import xadrezCamada.Color;
import xadrezCamada.XadrezPeca;

public class Rei extends XadrezPeca{

    public Rei(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
       
    }

    public String toString(){
        return "R";
    }

    
}
