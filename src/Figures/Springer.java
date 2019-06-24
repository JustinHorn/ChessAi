package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.Board;
import Main.Move;
import Main.Position;

public class Springer extends Figure{
	
	public Springer(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = 3;
		name = "Springer";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		// TODO Auto-generated method stub
		return GetAccessableFigures.springerMovement(this);
	}
}