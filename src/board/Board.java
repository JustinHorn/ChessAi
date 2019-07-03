package board;

import java.util.LinkedList;
import java.util.List;

import abstractFigure.*;
import abstractFigure.Figure.Team;
import figureWithIn.EmptyField;
import figureWithIn.Koenig;
import figureWithIn.Turm;
import positionAndMove.Move;
import positionAndMove.Position;

public class Board {

	private Figure[][] board = new Figure[8][8];
	private Team whosTurn = Team.WHITE;
	private List<Move> movesPlayed = new LinkedList<Move>();

	public Board(Figure[][] b) {
		if (b.length != 8 || b[0].length != 8) {
			throw new IllegalArgumentException("Given Board does not have 8x8 entrys!");
		}
		board = b;
	}

	public Board(char[][] b) {
		if (b.length != 8 || b[0].length != 8) {
			throw new IllegalArgumentException("Given char Array does not have 8x8 entrys!");
		}
		board = setBoard_from_charArray(b);
	}

	public Board() {
		char[][] b = new char[8][8];
		b[0] = "TSLKDLST".toCharArray();
		b[1] = "BBBBBBBB".toCharArray();
		b[2] = "--------".toCharArray();
		b[3] = "--------".toCharArray();
		b[4] = "--------".toCharArray();
		b[5] = "--------".toCharArray();
		b[6] = "bbbbbbbb".toCharArray();
		b[7] = "tslkdlst".toCharArray();
		board = setBoard_from_charArray(b);	
	}

	private Figure[][] setBoard_from_charArray(char[][] b) {
		Figure[][] f = new Figure[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				f[i][j] = BoardUtils.char_toFigures(this, b[i][j], new Position(i, j));
			}
		}
		return f;
	}

	public static boolean isInBounds(int b) {
		return (-1 < b) && (b < 8);
	}

	public Figure getFigure_at(Position p) {
		return getFigure_at(p.getRow(), p.getCol());
	}

	public Figure getFigure_at(int row, int col) {
		return board[row][col];
	}

	public void makeMove(Move m) {
		if (isMoveLegal(m)) {
			setTurn_to_nextTeam();
			makeChange(m);
		} else {
			throw new IllegalArgumentException("Move is not legal: " + m);
		}
	}

	public void takeMoveBack(Move m) {
		setTurn_to_previousTeam();
		if (isMoveLegal(m)) {
		} else {
			setTurn_to_previousTeam();
			makeChange(m);
			throw new IllegalArgumentException("Move has not been played: " + m);
		}

	}
	
	private void setTurn_to_previousTeam() {
		whosTurn = getNextTeam(whosTurn);
	}
	
	private void setTurn_to_nextTeam() {
		whosTurn = getNextTeam(whosTurn);
	}
	
	public static Team getNextTeam(Team team ) {
		if(team == Team.WHITE) {
			return  Team.BLACK;
		} else {
			return  Team.WHITE;
		}
	}

	public boolean isMoveLegal(Move m) {
		if (whosTurn != m.getTeam()) {
			return false;
		}
		List<Move> moves = m.getMovingFigure().getMoves();
		moves.addAll(GetSpecialMoves_FromBoard.getMySpecialMoves(this,m.getMovingFigure()));
		if (!moves.contains(m)) {
			return false;
		}

		return true;
	}
	
	public boolean hasFigure_beenMoved(int id) {
		for(Move m: movesPlayed) {
			if(m.getMovingFigure().getId() == id ) {
				return true;
			}
		}
		return false;
	}

	protected void makeChange(Move m) {
		switch (m.getType()) {
		case EnPassant:
			board[m.toPosition().getRow() + (m.getTeam() == Team.WHITE? -1 : 1)][m.toPosition().getCol()] = new EmptyField();
		case Normal:
		case Twice:
			assignFigures_toBoard(m,m.getMovingFigure(),new EmptyField());
			break;
		case Rochade:
			int lOrR = (m.getTypeModifier() == 'K' ? +1 : -1);
			Turm turm = (Turm) getFigure_at(m.fromPosition().getRow(), (m.getTypeModifier() == 'K' ? 0 : 7));
			Koenig koenig = (Koenig) getFigure_at(m.fromPosition().getRow(), 3);
			
			board[m.fromPosition().getRow()][m.fromPosition().getCol()] = new EmptyField();
			board[m.fromPosition().getRow()][(m.getTypeModifier() == 'K' ? 0 : 7)]= new EmptyField();
			
			board[m.fromPosition().getRow()][m.toPosition().getCol()] = koenig;
			board[m.fromPosition().getRow()][m.toPosition().getCol() + lOrR] = turm;
			
			break;
		case BauerTo:
			Figure whateverTheBauerTurnsTo = BoardUtils.returnFigure(this, m.getTypeModifier(), m.getTeam());
			assignFigures_toBoard(m,whateverTheBauerTurnsTo,new EmptyField());
			break;
		default:
			throw new IllegalArgumentException("Something happend to that move");
		}
		movesPlayed.add(m);
	}

	protected void reverseChange(Move m) {
		switch (m.getType()) {
		case EnPassant:
			board[m.toPosition().getRow() + (m.getTeam() == Team.WHITE ? -1 : 1)][m.toPosition().getCol()] = m.getDefeatedFigure();
			assignFigures_toBoard(m,new EmptyField(),m.getMovingFigure());
			break;
		case BauerTo:
		case Normal:
		case Twice:
			assignFigures_toBoard(m,m.getDefeatedFigure(),m.getMovingFigure());
			break;
		case Rochade:
			int lOrR = (m.getTypeModifier() == 'K' ? +1 : -1);

			Turm turm = (Turm) getFigure_at(m.fromPosition().getRow(), m.toPosition().getCol() + lOrR);
			Koenig koenig = (Koenig) getFigure_at(m.fromPosition().getRow(), 3-2*lOrR);
			
			board[m.fromPosition().getRow()][m.toPosition().getCol()] = new EmptyField();
			board[m.fromPosition().getRow()][m.toPosition().getCol() + lOrR] = new EmptyField();
			
			board[m.fromPosition().getRow()][m.fromPosition().getCol()] = koenig;
			board[m.fromPosition().getRow()][(m.getTypeModifier() == 'K' ? 0 : 7)] = turm;
			break;
		default:
			throw new IllegalArgumentException("Something happend to that move");
		}
		movesPlayed.remove(m);
	}
	
	private void assignFigures_toBoard(Move m, Figure toPosition, Figure fromPosition) {
		board[m.toPosition().getRow()][m.toPosition().getCol()] =  toPosition;
		board[m.fromPosition().getRow()][m.fromPosition().getCol()] = fromPosition;
	}

	public Position getPosition_of_FigureWithId(int figureID) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getId() == figureID) {
					return new Position(i, j);
				}
			}
		}
		throw new IllegalArgumentException("No Figure with Id: " + figureID);
	}
	
	public boolean hasLastMove() {
		return movesPlayed.size() > 0;
	}
	
	public Move getLastMove() {
		return movesPlayed.get(movesPlayed.size()-1);
	}

	@Override
	public boolean equals(Object board) {
		if (board instanceof Board) {
			Board b = (Board) board;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (b.getFigure_at(i, j).firstChar() != getFigure_at(i, j).firstChar()) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
}
