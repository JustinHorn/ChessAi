package positionAndMove;

import board.Board;

public class Position {
	private int row;
	private int col;
	
	public Position(int r,int c) {
		if(Board.isInBounds(r) || Board.isInBounds(c)) {
			row = r;
			col = c;
		} else {
			throw new IllegalArgumentException("Position out of bounds: r: "+r+" c: "+c);
		}
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Position) {
			Position o = (Position) other;
			if(o.getRow() == row && o.getCol() == col) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "r: "+row+" c: "+col;
	}
	
	
}
