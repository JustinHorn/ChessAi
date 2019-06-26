package Figures;

import java.util.List;

import Main.*;

public class Dame extends Figure {

	public Dame(Board b,  boolean isWhite) {
		super(b);
		this.isWhite = isWhite;
		value = 9;
		name = "Dame";
	}



	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.dameMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.dameMovement(this.getBoard(),p);
	}
}
