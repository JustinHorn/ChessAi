package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.*;

public class FigureUtils {

	public static List<Move> movesInRange(Figure me) {
		List<Figure> accessableFigures = me.getAccessableFigures();
		List<Move> moves = new LinkedList<Move>();
		Board b = me.getBoard();
		for (Figure f : accessableFigures) {
			if (f instanceof EmptyField || f.isWhite() != me.isWhite()) {
				moves.add(new Move(me, f, b.getPosition_of_FigureWithId(me.getId()),
						b.getPosition_of_FigureWithId(f.getId())));
			}
		}

		return moves;
	}

	public static List<Move> getSpecialMove_of_Figure(Figure moving, Board b) {
		List<Move> specialMoves = new LinkedList<Move>();
		if (moving instanceof Bauer) {
			specialMoves.addAll(getBauer_toSthMoves(b, moving));
		}
		return new LinkedList<Move>();
	}

	private static List<Move> getBauer_toSthMoves(Board b, Figure moving) {
		boolean isWhite = moving.isWhite();
		Position fP = b.getPosition_of_FigureWithId(moving.getId());
		int upOrDown = isWhite ? 1 : -1;
		int edge = isWhite ? 6 : 1;

		List<Move> specialMoves = new LinkedList<Move>();
		if (fP.getRow() == edge) {
			for (int leftOrRight : new int[] { -1, 1 }) {
				if (b.isInBounds(fP.getCol() + leftOrRight)) {
					Figure before = b.getFigure_at(fP.getRow() + upOrDown, fP.getCol() + leftOrRight);
					if (!(before instanceof EmptyField) && before.isWhite() != moving.isWhite()) {
						specialMoves.addAll(addSpecialBauer_moves(moving, before, b));
					}
				}
			}
			Figure front = b.getFigure_at(fP.getRow() + upOrDown, fP.getCol());
			if (front instanceof EmptyField) {
				specialMoves.addAll(addSpecialBauer_moves(moving, front, b));
			}
		}
		return specialMoves;
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

}
