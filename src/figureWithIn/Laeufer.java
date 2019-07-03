package figureWithIn;

import java.util.LinkedList;
import java.util.List;

import abstractFigure.Figure;
import abstractFigure.GetAccessableFigures;
import board.Board;
import positionAndMove.Move;
import positionAndMove.Position;

public class Laeufer extends Figure {
	
	public Laeufer(Board b, Team team) {
		super(b);
		this.team = team;
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
