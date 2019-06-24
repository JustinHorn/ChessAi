package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;

public class Dame extends Figure {

	public Dame(Board b, Position p, boolean isWhite) {
		this.isWhite = isWhite;
		board = b;
		value = 9;
		name = "Dame";
		position = p;
	}

	@Override
	public List<Move> getMoves() {
		return new LinkedList<Move>();
	}

	@Override
	public List<Figure> getAccessableFigures() {
		return GetAccessableFigures.dameMovement(this);
	}
}
