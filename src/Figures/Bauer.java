package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.Board;
import Main.Move;
import Main.Position;

public class Bauer extends Figure {

	public Bauer(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = 1;
		name = "Bauer";
		position = p;
	}

	@Override
	public void setPosition(Position p) {
		if ((p.getRow() == 7 && isWhite) || (p.getRow() == 0 && !isWhite)) {
			throw new IllegalArgumentException(
					"A Bauer does not reach the end of the board. He gets transformed to a different figure first!");
		}
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}

	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.bauerMovement(this);
	}
	
	
}
