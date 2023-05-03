package xadrezCamada;

import tabuleiroCamada.Posicao;

public class XadrezPosicao {

    private char coluna;
    private int linha;


    public XadrezPosicao(char coluna, int linha) {
        if( coluna < 'a' || coluna >'h' || linha < 1 || linha > 8){
            throw new XadrezException("Erro na instanciacao do XadrezPosicao, valores validos de a1 ate h8");
        }
        this.coluna = coluna;
        this.linha = linha;

    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }
    //estamos mudando a posicao da matriz para uma posicao de xadrez
    // no lugar de ser [0,0] sera [8,a]
    protected Posicao toPosicao(){
        return new Posicao(8 - linha, coluna - 'a');
    }
     //operacao contraria da de cima, lembrando que é coluna linha
    protected static XadrezPosicao fromPosicao(Posicao posicao){
        return new XadrezPosicao((char)('a' - posicao.getColuna()), 8- posicao.getLinha());
    }

    @Override
    public String toString(){
        return "" + coluna + linha;
    } 

    




    
    
}
