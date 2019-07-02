package figureWithIn;
import java.util.List;

import abstractFigure.Figure;
import board.*;
import positionAndMove.Position;

public class EmptyField extends Figure {

	public EmptyField() {
		super(null);
		isWhite = false;
		value = 0;
		name = "EMPTY";
	}
	
	@Override
	public Board getBoard() {
		throw new IllegalAccessError("EmptyField is unaware of its board");
	}
	
	@Override
	public Position getPosition() {
		throw new IllegalAccessError("EmptyField is unaware of its position");
	}

	@Override
	public boolean isWhite() {
		throw new IllegalAccessError("EmptyField cant be white nor black!");
	}

	@Override
	public List<Figure> getAccessableFigures() {
		throw new IllegalAccessError("EmptyField has no accessable figures!");

	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		throw new IllegalAccessError("EmptyField has no accessable figures!");

	}
	
	public char firstChar() {
		return '-';
	}
}
