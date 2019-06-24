package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;


public class Turm extends Figure{
	
	public Turm(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = 5;
		name = "Turm";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.turmMovement(this);
	}
	
	
	
}
