package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;


public class Turm extends Figure{
	
	public Turm(Board b,  boolean isWhite) {
		super(b);
		this.isWhite = isWhite;
		value = 5;
		name = "Turm";
	}
	
	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.turmMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.turmMovement(this.getBoard(),p);
	}
	
}
