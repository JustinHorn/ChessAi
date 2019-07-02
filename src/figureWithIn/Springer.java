package figureWithIn;

import java.util.LinkedList;
import java.util.List;

import abstractFigure.Figure;
import abstractFigure.GetAccessableFigures;
import board.Board;
import positionAndMove.Move;
import positionAndMove.Position;

public class Springer extends Figure{
	
	public Springer(Board b,  boolean isWhite) {
		super(b);
		this.isWhite = isWhite;
		value = 3;
		name = "Springer";
	}

	@Override
	public List<Figure> getAccessableFigures() {
		// TODO Auto-generated method stub
		return GetAccessableFigures.springerMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.springerMovement(this.getBoard(),p);
	}
}