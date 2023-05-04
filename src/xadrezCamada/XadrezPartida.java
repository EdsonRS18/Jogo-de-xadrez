package xadrezCamada;

import java.util.ArrayList;
import java.util.List;

import tabuleiroCamada.Peca;
import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezPeca.Rei;
import xadrezPeca.Torre;

//coração do jogo, aqui ficara as regras
public class XadrezPartida {

    private Tabuleiro tabuleiro;
    private int turno;
    private Color jogador;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    
    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);//aqui é onde informo as dimensoes do tabuleiro
        turno = 1;
        jogador = Color.WHITE;
        setupInicial();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public int getTurno() {
        return turno;
    }

    public Color getJogador() {
        return jogador;
    }


    public XadrezPeca[][] getPecas(){
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for(int i=0; i< tabuleiro.getLinhas(); i++){
            for(int j=0; j<tabuleiro.getColunas(); j++){
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i,j);
            }
        }
        return mat;
    }

    public boolean[][] possivelMovimento(XadrezPosicao posicaoOrigem){
        Posicao posicao = posicaoOrigem.toPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).possivelMovimento();

    }
    public XadrezPeca performXadrezMove(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoAlvo ){
        Posicao origem = posicaoOrigem.toPosicao();
        Posicao alvo = posicaoAlvo.toPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoAlvo(origem,alvo);
        Peca pecaCapturada  = makeMove(origem, alvo);
        nextTurno();
        return (XadrezPeca)pecaCapturada ;
    }

    private Peca makeMove(Posicao origem, Posicao alvo){
        Peca p = tabuleiro.removePeca(origem);
        Peca pecaCapturada  = tabuleiro.removePeca(alvo);
        tabuleiro.localPeca(p, alvo);

        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada ;
    }

    private void validarPosicaoOrigem(Posicao posicao){
        if(!tabuleiro.temUmaPeca(posicao)){
            throw new XadrezException("nao existe peça na origem");
        }
        if ( jogador != ((XadrezPeca)tabuleiro.peca(posicao)).getColor()){
            throw new XadrezException("peca escolhida nao é a sua");
        }
        if(!tabuleiro.peca(posicao).algumMovimento()){
            throw new XadrezException("nao existe movimento possivel");
        }
    }

    private void validarPosicaoAlvo(Posicao origem, Posicao alvo){
        if (!tabuleiro.peca(origem).possivelMovimento(alvo)){
            throw new XadrezException("A peça escolhida nao pode se mover para esse local");
        }
    }

    private void nextTurno(){
        turno++;
        jogador = (jogador == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

        
    
    //recebe as coordenadas do xadrez e converte para matriz
    
    private void lugarNovaPeca(char coluna, int linha, XadrezPeca peca){
        tabuleiro.localPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());

        pecasNoTabuleiro.add(peca);
    }
    //agora posso instanciar as peças ja em posicoes de xadrez chamando a funcao lugarNovaPeca
    private void setupInicial(){
        lugarNovaPeca('c', 1, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('c', 2, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('d', 2, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('e', 2, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('e', 1, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('d', 1, new Rei(tabuleiro, Color.WHITE));

        lugarNovaPeca('c', 7, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('c', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('d', 7, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('e', 7, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('e', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('d', 8, new Rei(tabuleiro, Color.BLACK));
    }

    
}
