package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.Board;
import Main.Move;
import Main.Position;

public class Laeufer extends Figure {
	
	public Laeufer(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = 3;
		name = "Laeufer";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.laeuferMovement(this);
	}
	
	
}
