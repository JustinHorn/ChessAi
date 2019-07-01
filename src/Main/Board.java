package Main;

import java.util.LinkedList;
import java.util.List;

import Figures.*;

public class Board  {

	private Figure[][] board = new Figure[8][8];
	private boolean isWhitesTurn = true;
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
		for(int i = 0; i< 8; i++) {
			for(int j = 0; j< 8; j++) {
				board[i][j] = new EmptyField();
			}
		}
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

	
	public boolean isInBounds(int b) {
		return (-1 < b) && (b <8 );
	}
	
	public Figure getFigure_at(Position p) {
		return getFigure_at(p.getRow(),p.getCol());
	}
	
	public Figure getFigure_at(int row,int col) {
		return board[row][col];
	}
	
	public void makeMove(Move m) {
		if(isMoveLegal(m) ) {
			isWhitesTurn = ! isWhitesTurn;
			changeBoard(m);
			movesPlayed.add(m);
		} else {
			throw new IllegalArgumentException("Move is not legal: " + m);
		}
	}
	
	public void takeMoveBack(Move m) {
			reverseBoard(m);
			isWhitesTurn = ! isWhitesTurn;
			if(isMoveLegal(m)) {
				movesPlayed.remove(m);
			} else {
				isWhitesTurn = ! isWhitesTurn;
				changeBoard(m);
				throw new IllegalArgumentException("Move has not been played: "+m);
			}
	
	}

	public boolean isMoveLegal(Move m) {

		if(isWhitesTurn != m.isWhite()) {
			return false;
		}
		
		List<Move> moves = m.getMovingFigure().getMoves();
		moves.addAll(specialMoves(m.getMovingFigure()));
		if(!moves.contains(m)) {
			return false;
		}
		
		return true;
	}
	
	public List<Move> specialMoves(Figure movingFigure) {
		
		return new LinkedList<Move>();
	}


	protected void changeBoard(Move m) {
		Figure temp = board[m.fromPostion().getRow()][m.fromPostion().getCol()];
		board[m.toPostion().getRow()][m.toPostion().getCol()] = temp;
		board[m.fromPostion().getRow()][m.fromPostion().getCol()] = new EmptyField();
	}
	
	protected void reverseBoard(Move m) {
		board[m.fromPostion().getRow()][m.fromPostion().getCol()] = getFigure_at(m.toPostion());
		board[m.toPostion().getRow()][m.toPostion().getCol()] = m.getDefeatedFigure();
	}

	public Position getPosition_of_FigureWithId(int figureID) {
		for(int i = 0; i < 8;i++) {
			for(int j = 0; j < 8;j++) {
				if(board[i][j].getId() == figureID) {
					return new Position(i,j);
				}
			}
		}
		throw new IllegalArgumentException("No Figure with Id: "+ figureID);
	}

	@Override
	public boolean equals(Object board) {
		if(board instanceof Board) {
			Board b = (Board)board;
			for(int i = 0; i < 8;i++) {
				for(int j = 0; j < 8;j++) {
					if(b.getFigure_at(i,j).firstChar() != getFigure_at(i, j).firstChar()) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
}
