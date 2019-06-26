package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;


public class FigureUtils {

	public static List<Move> movesInRange(Figure me) {
		List<Figure> accessableFigures = me.getAccessableFigures();
		List<Move> moves = new LinkedList<Move>();
		Board b = me.getBoard();
		for(Figure f: accessableFigures) {
			if(f instanceof EmptyField || f.isWhite() != me.isWhite()) {
				moves.add(new Move(me,f,b.getPosition_ofFigureWithId(me.getId()),b.getPosition_ofFigureWithId(f.getId())));
			}
		}
		
		return moves;
	}


}
