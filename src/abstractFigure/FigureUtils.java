package abstractFigure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import abstractFigure.Figure.*;
import board.*;
import figureWithIn.Bauer;
import figureWithIn.EmptyField;
import positionAndMove.Move;
import positionAndMove.MoveTyp;
import positionAndMove.Position;

public class FigureUtils {

	public static List<Move> movesInRange(Figure me) {
		List<Figure> accessableFigures = me.getAccessableFigures();
		List<Move> moves = new LinkedList<Move>();
		Board b = me.getBoard();
		for (Figure f : accessableFigures) {
			if (f instanceof EmptyField || f.getTeam() != me.getTeam()) {
				moves.add(new Move(me, f, b.getPosition_of_FigureWithId(me.getId()),
						b.getPosition_of_FigureWithId(f.getId())));
			}
		}

		return moves;
	}
	

	public static List<Move> addSpecialBauer_moves(Figure moving, Figure defeated, Board b) {
		List<Move> specialMoves = new LinkedList<Move>();
		Position positionM = b.getPosition_of_FigureWithId(moving.getId());
		Position positionD = b.getPosition_of_FigureWithId(defeated.getId());

		specialMoves.add(new Move(moving, defeated, positionM, positionD, MoveTyp.BauerTo, 'D'));
		specialMoves.add(new Move(moving, defeated, positionM, positionD, MoveTyp.BauerTo, 'S'));
		specialMoves.add(new Move(moving, defeated, positionM, positionD, MoveTyp.BauerTo, 'L'));
		specialMoves.add(new Move(moving, defeated, positionM, positionD, MoveTyp.BauerTo, 'T'));
		return specialMoves;
	}


	/**
	 * @return 
	 */
	public static List<Figure> getHostileFigures(List<Figure> list, Team team) {
		List<Figure> hostile = new LinkedList<Figure>();
		Iterator<Figure> it = list.iterator();
		while(it.hasNext()) {
			Figure f = it.next();
			if ((f instanceof EmptyField) || f.getTeam() == team) {
				
			}else {
				hostile.add(f);
			}
		}
		return hostile;
	}

}
