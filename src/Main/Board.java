package Main;

import Figures.*;

public class Board {

	private Figure[][] board = new Figure[8][8];

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
				board[i][j] = new EmptyField(new Position(i,j));
			}
		}
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

	protected void changeBoard(Move m) {
		Figure temp = board[m.fromPostion().getRow()][m.fromPostion().getCol()];
		board[m.toPostion().getRow()][m.toPostion().getCol()] = temp;
		board[m.fromPostion().getRow()][m.fromPostion().getCol()] = new EmptyField(m.fromPostion());
	}
	
	protected void reverseBoard(Move m) {
		board[m.fromPostion().getRow()][m.fromPostion().getCol()] = m.getMovingFigure();
		board[m.toPostion().getRow()][m.toPostion().getCol()] = m.getDefeatedFiugre();
	}

	private Figure[][] setBoard_from_charArray(char[][] b) {
		Figure[][] f = new Figure[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				f[i][j] = char_toFigures(b[i][j], new Position(i, j));
			}
		}
		return f;
	}


	private Figure char_toFigures(char sign, Position p) {
		boolean isUpper = Character.isUpperCase(sign);
		sign = Character.toUpperCase(sign);
		if (sign == 'D') {
			return new Dame(this, p, isUpper);
		} else if (sign == 'K') {
			return new Koenig(this, p, isUpper);
		} else if (sign == 'L') {
			return new Laeufer(this, p, isUpper);
		} else if (sign == 'S') {
			return new Springer(this, p, isUpper);
		} else if (sign == 'B') {
			return new Bauer(this, p, isUpper);
		} else if (sign == 'T') {
			return new Turm(this, p, isUpper);
		} else if (sign == '-') {
			return new EmptyField(p);
		}
		throw new IllegalArgumentException("Char at " + p + " does not match pattern: " + sign);

	}


}
