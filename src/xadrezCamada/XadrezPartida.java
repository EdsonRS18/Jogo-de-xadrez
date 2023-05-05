package xadrezCamada;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroCamada.Peca;
import tabuleiroCamada.Posicao;
import tabuleiroCamada.Tabuleiro;
import xadrezPeca.Bispo;
import xadrezPeca.Cavalo;
import xadrezPeca.Peao;
import xadrezPeca.Rainha;
import xadrezPeca.Rei;
import xadrezPeca.Torre;

//coração do jogo, aqui ficara as regras
public class XadrezPartida {

    private Tabuleiro tabuleiro;
    private int turno;
    private Color jogador;
    private boolean check;
    private boolean checkMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    
    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);//aqui é onde informo as dimensoes do tabuleiro
        turno = 1;
        jogador = Color.WHITE;
        check = false;
        checkMate = false;
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
    public boolean getCheck() {
		return check;
	}
    public boolean getCheckMate() {
		return checkMate;
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

        if (testCheck(jogador)) {
			undoMove(origem, alvo, pecaCapturada);
			throw new XadrezException("Voce n pode se colocar em check");
		}
		check = (testCheck(Oponente(jogador))) ? true : false;

        if (testCheckMate(Oponente(jogador))) {
			checkMate = true;
		}
		else {
			nextTurno();
		}
        
        return (XadrezPeca)pecaCapturada ;
    }

    private Peca makeMove(Posicao origem, Posicao alvo){
        XadrezPeca p = (XadrezPeca)tabuleiro.removePeca(origem);
        p.incrementarCount();
        Peca pecaCapturada  = tabuleiro.removePeca(alvo);
        tabuleiro.localPeca(p, alvo);

        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada ;
    }
    //refazer jogada
    private void undoMove(Posicao origem, Posicao alvo, Peca pecaCapturada) {
		XadrezPeca p = (XadrezPeca)tabuleiro.removePeca(alvo);
        p.decrementarCount();
		tabuleiro.localPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.localPeca(pecaCapturada, alvo);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
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

    private Color Oponente(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

    private XadrezPeca rei(Color color) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getColor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (XadrezPeca)p;
			}
		}
		throw new IllegalStateException("nao existe rei da cor " + color + " no tabuleiro");
	}

    private boolean testCheck(Color color) {
		Posicao PosicaoRei = rei(color).getXadrezPosicao().toPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getColor() == Oponente(color)).collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.possivelMovimento();
			if (mat[PosicaoRei.getLinha()][PosicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

    private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getColor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possivelMovimento();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((XadrezPeca)p).getXadrezPosicao().toPosicao();
						Posicao alvo = new Posicao(i, j);
						Peca pecaCapturada = makeMove(origem, alvo);
						boolean testCheck = testCheck(color);
						undoMove(origem,alvo, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	

        
    
    //recebe as coordenadas do xadrez e converte para matriz
    
    private void lugarNovaPeca(char coluna, int linha, XadrezPeca peca){
        tabuleiro.localPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());

        pecasNoTabuleiro.add(peca);
    }
    //agora posso instanciar as peças ja em posicoes de xadrez chamando a funcao lugarNovaPeca
    private void setupInicial(){
        lugarNovaPeca('a', 1, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Color.WHITE));
        lugarNovaPeca('c', 1, new Bispo(tabuleiro, Color.WHITE));
        lugarNovaPeca('d', 1, new Rainha(tabuleiro, Color.WHITE));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Color.WHITE));
        lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.WHITE));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Color.WHITE));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Color.WHITE));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.BLACK));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Color.BLACK));
        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Color.BLACK));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Color.BLACK));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Color.BLACK));
        lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.BLACK));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Color.BLACK));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Color.BLACK));
    }
    
}
