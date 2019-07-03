package figureWithIn;

import java.util.LinkedList;
import java.util.List;

import abstractFigure.Figure;
import abstractFigure.GetAccessableFigures;
import board.*;
import positionAndMove.Position;

public class Bauer extends Figure {

	public Bauer(Board b, Team team) {
		super(b);
		this.team = team;
		value = 1;
		name = "Bauer";
	}

	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.bauerMovement(this);
	}
	
	@Override
	public List<Figure> getAccessableFigures(Position p) {
		return GetAccessableFigures.bauerMovement(getBoard(),p,team);
	}
	
	public static List<Figure> getAccessableFigures(Board b, Position p, Team team) {
		return GetAccessableFigures.bauerMovement(b,p,team);
	}
	
}
