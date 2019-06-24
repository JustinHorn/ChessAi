package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;


public class FigureUtils {

	public static List<Move> movesInRange(Figure me) {
		List<Figure> accessableFigures = me.getAccessableFigures();
		List<Move> moves = new LinkedList<Move>();
		for(Figure f: accessableFigures) {
			moves.add(new Move(me,f,me.getPosition(),f.getPosition()));
		}
		
		return moves;
	}


}
