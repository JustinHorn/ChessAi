package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.Board;
import Main.Move;
import Main.Position;

public class Laeufer extends Figure {
	
	public Laeufer(Board b, boolean isWhite) {
		super(b);
		this.isWhite = isWhite;
		value = 3;
		name = "Laeufer";
	}

	
	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.laeuferMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.laeuferMovement(this.getBoard(),p);
	}
	
}
