package figureWithIn;

import java.util.List;

import abstractFigure.Figure;
import abstractFigure.GetAccessableFigures;
import board.*;
import positionAndMove.Position;

public class Dame extends Figure {

	public Dame(Board b, Team team) {
		super(b);
		this.team = team;
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
