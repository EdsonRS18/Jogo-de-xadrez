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
    private XadrezPeca enPassantVulnerable;
    private XadrezPeca promocao;


    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    
    public XadrezPartida(){
        tabuleiro = new Tabuleiro(8, 8);//aqui é onde informo as dimensoes do tabuleiro
        turno = 1;
        jogador = Color.WHITE;
        check = false;
        checkMate = false;
        setupInicial();
        enPassantVulnerable = null;
    }

    public XadrezPeca getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public int getTurno() {
        return turno;
    }

    public XadrezPeca getPromocao() {
		return promocao;
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

        XadrezPeca movePeca = (XadrezPeca)tabuleiro.peca(alvo);

        // #specialmove promotion
		promocao= null;
		if (movePeca instanceof Peao) {
			if ((movePeca.getColor() == Color.WHITE && alvo.getLinha() == 0) || (movePeca.getColor() == Color.BLACK && alvo.getLinha() == 7)) {
				promocao = (XadrezPeca)tabuleiro.peca(alvo);
				promocao = substituirPeca("Q");
			}
		}

		check = (testCheck(Oponente(jogador))) ? true : false;

        if (testCheckMate(Oponente(jogador))) {
			checkMate = true;
		}
		else {
			nextTurno();
		}

        // #specialmove en passant
		if (movePeca instanceof Peao && (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)) {
			enPassantVulnerable = movePeca;
		}
		else {
			enPassantVulnerable = null;
		}

        
        return (XadrezPeca)pecaCapturada ;
    }

    public XadrezPeca substituirPeca(String type) {
		if (promocao == null) {
			throw new IllegalStateException("não tem peca para ser promovida");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("Q")) {
			return promocao;
		}

		Posicao pos = promocao.getXadrezPosicao().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		XadrezPeca newPiece = newPiece(type, promocao.getColor());
		tabuleiro.localPeca(newPiece, pos);
		pecasNoTabuleiro.add(newPiece);

		return newPiece;
	}

	private XadrezPeca newPiece(String type, Color color) {
		if (type.equals("B")) return new Bispo(tabuleiro, color);
		if (type.equals("C")) return new Cavalo(tabuleiro, color);
		if (type.equals("Q")) return new Rainha(tabuleiro, color);
		return new Torre(tabuleiro, color);
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

        //movimento especial roque pequeno

        if(p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() + 3);
            Posicao alvoT = new Posicao(origem.getLinha(),origem.getColuna() + 1);
            XadrezPeca torre  =(XadrezPeca)tabuleiro.removePeca(origemT);
           tabuleiro.localPeca(torre, alvoT);
           torre.incrementarCount();
        }
        //movimento especial roque grande
        if(p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() - 4);
            Posicao alvoT = new Posicao(origem.getLinha(),origem.getColuna() - 1);
            XadrezPeca torre  =(XadrezPeca)tabuleiro.removePeca(origemT);
           tabuleiro.localPeca(torre, alvoT);
           torre.incrementarCount();
        }

        // #specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getColor() == Color.WHITE) {
					posicaoPeao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				}
				else {
					posicaoPeao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
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

        if(p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() + 3);
            Posicao alvoT = new Posicao(alvo.getLinha(),alvo.getColuna() + 3);
            XadrezPeca torre  =(XadrezPeca)tabuleiro.removePeca(alvoT);
           tabuleiro.localPeca(torre, origemT);
           torre.decrementarCount();
        }
        //movimento especial roque grande
        if(p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(),origem.getColuna() - 4);
            Posicao alvoT = new Posicao(alvo.getLinha(),alvo.getColuna() - 1);
            XadrezPeca torre  =(XadrezPeca)tabuleiro.removePeca(alvoT);
           tabuleiro.localPeca(torre, origemT);
           torre.decrementarCount();
        }

        	// #specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == enPassantVulnerable) {
				XadrezPeca peao = (XadrezPeca)tabuleiro.removePeca(alvo);
				Posicao posicaoPeao;
				if (p.getColor() == Color.WHITE) {
					posicaoPeao = new Posicao(3, alvo.getColuna());
				}
				else {
					posicaoPeao = new Posicao(4, alvo.getColuna());
				}
				tabuleiro.localPeca(peao, posicaoPeao);
			}
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
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Color.WHITE));
        lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.WHITE));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Color.WHITE));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Color.WHITE, this));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Color.WHITE, this));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.BLACK));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Color.BLACK));
        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Color.BLACK));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Color.BLACK));
        lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.BLACK));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Color.BLACK));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Color.BLACK, this));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Color.BLACK, this));
    }
    
}
