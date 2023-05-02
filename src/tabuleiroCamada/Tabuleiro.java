package tabuleiroCamada;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;


    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas <1){
            throw new tabuleiroException("Erro na criacao do tabuleiro, deve haver no minimo uma linha e uma coluna");
        }

        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }
//nao faz sentido poder mudar o tamanho do tabuleiro depois de criado

    public int getLinhas() {
        return linhas;
    }


    public int getColunas() {
        return colunas;
    }


    public Peca peca(int linha, int coluna){
        if (!posicaoExiste(linha, coluna)){
            throw new tabuleiroException("posicao nao existe no tabuleiro");
        }
        return pecas[linha][coluna];
        
    }

    public Peca peca(Posicao posicao){
        if (!posicaoExiste(posicao)){
            throw new tabuleiroException("posicao nao existe no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    //colocar peca em uma posicao
    public void localPeca(Peca peca, Posicao posicao){
        if (temUmaPeca(posicao)){
            throw new tabuleiroException("ja existe uma peca nessa posicao: "+posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca; //nessa posicao fica a peca
        peca.posicao = posicao;
    }

    //se uma peça existe pela linha e coluna
    private boolean posicaoExiste(int linha, int coluna){
       return  linha >=0 && linha <linhas && coluna >=0 && coluna < colunas;
    }

    public boolean posicaoExiste(Posicao posicao){
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    //verificar se existe uma peca nessa posicao
    public boolean temUmaPeca(Posicao posicao){
        if (!posicaoExiste(posicao)){
            throw new tabuleiroException("posicao nao existe no tabuleiro");
        }
      return  peca(posicao) != null;

    }


    
    
    


    
}
