package Main;

public class Position {
	private int row;
	private int col;
	
	public Position(int r,int c) {
		if(r <0 || 7 < r || c < 0 || 7< c) {
			throw new IllegalArgumentException("Postion out of bounds: r: "+r+" c: "+c);
		}
		row = r;
		col = c;
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
