package tabuleiroCamada;

public class Peca {

    protected Posicao posicao; //nao é a posição do xadrez, é  a posição da matriz,ela n deve ser vidivel an camada de xadrez
    private Tabuleiro tabuleiro;


    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }



    //ira ficar sem set pois n é permitido mudar o tabuleiro
    //protect pois apenas classes e subclasses do mesmo pacote podem acessar o tabuleiro de uma peça
    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }


    
}
