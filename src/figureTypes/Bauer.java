package figureTypes;

import java.util.List;

import abstractFigure.Figure;
import abstractFigure.GetAccessableFigures;
import board.*;
import positionAndMove.Position;

public class Bauer extends Figure {

	public Bauer(Board b, boolean isWhite) {
		super(b);
		this.isWhite = isWhite;
		value = 1;
		name = "Bauer";
	}

	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.bauerMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.bauerMovement(getBoard(),p,isWhite);
	}
	
}
