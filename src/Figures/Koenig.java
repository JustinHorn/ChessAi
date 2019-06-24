package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;

public class Koenig extends Figure{
	
	public Koenig(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = Integer.MAX_VALUE;
		name = "Koenig";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		// TODO Auto-generated method stub
		return GetAccessableFigures.koenigMovement(this);
	}

}
