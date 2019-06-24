package Figures;
import java.util.List;

import Main.Move;
import Main.Position;

public class EmptyField extends Figure {

	public EmptyField(Position p) {
		isWhite = false;
		board = null;
		value = 0;
		name = "EMPTY";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		throw new IllegalAccessError("EmptyField cant do moves!");
	}

	@Override
	public boolean isWhite() {
		throw new IllegalAccessError("EmptyField cant be white or black!");
	}

	@Override
	public void setPosition(Position p) {
		throw new IllegalAccessError("EmptyField cannot change its possition!");
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		// TODO Auto-generated method stub
		return null;
	}
}
