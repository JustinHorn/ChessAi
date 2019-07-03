package board;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import abstractFigure.*;
import abstractFigure.Figure.Team;
import figureWithIn.*;

import positionAndMove.*;

public class GetSpecialMoves_FromBoard {

	public static List<Move> getMySpecialMoves(Board b, Figure me) {
		Position myP = b.getPosition_of_FigureWithId(me.getId());
		List<Move> specialMoves = new LinkedList<Move>();
	
		if (me instanceof Koenig) {
			specialMoves.addAll(getRocharde_if_possible((Koenig) me, b, myP));
		}
	
		if (me instanceof Bauer) {
			specialMoves.addAll(getSpecialBauerMoves_if_possible((Bauer) me, b, myP));
		}
	
		return specialMoves;
	}

	private static List<Move> getRocharde_if_possible(Koenig k, Board b, Position myP) {
		List<Move> moves = new LinkedList<Move>();
		if (b.hasFigure_beenMoved(k.getId()) || BoardUtils.is_koenig_check(b, k.getTeam())) {
	
		} else {
			moves.addAll(get_ifPossible_rochadeMove(b, k, myP, true));
			moves.addAll(get_ifPossible_rochadeMove(b, k, myP, false));
		}
		return moves;
	}

	private static List<Move> get_ifPossible_rochadeMove(Board b, Figure me, Position myP, boolean leftOrRight) {
		LinkedList<Move> m = new LinkedList<Move>();
		if (rochadePossible(b, me, myP, leftOrRight)) {
			m.add(new Move(me, new EmptyField(), myP, new Position(myP.getRow(), 3 + (leftOrRight ? -2 : +2)), MoveTyp.Rochade,
					(leftOrRight ? 'K' : 'D')));
		}
		return m;
	}

	private static boolean rochadePossible(Board b, Figure me, Position myP, boolean leftOrRight) {
		List<Figure> figures;
		if (leftOrRight) {
			figures = GetFigures_fromPositionTo._left_until_includeNotEmptyField(b, myP);
		} else {
			figures = GetFigures_fromPositionTo._right_until_includeNotEmptyField(b, myP);
		}
		return isTurmAlright(b, me, figures,leftOrRight) && areRochadeFieldsEmpty(figures)
				&& are_fields_unthreadend(b, figures, me.getTeam());
	}

	private static boolean isTurmAlright(Board b, Figure me, List<Figure> figures, boolean leftOrRight) {
		int row = (me.getTeam() == Team.WHITE ? 0 : 7);
		int turmCol = (leftOrRight ? 0 : 7);
	
		Figure turm = b.getFigure_at(row, turmCol);
		
		return turm instanceof Turm && turm.getTeam() == me.getTeam() && !b.hasFigure_beenMoved(turm.getId()) && figures.contains(turm);
	}

	private static boolean areRochadeFieldsEmpty(List<Figure> horizontalLineOfFigures) {
		List<Figure> figures = horizontalLineOfFigures;
		int s = figures.size();
		for (int i = 0; i < s - 1; i++) {
			if (figures.get(i) instanceof EmptyField) {
	
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean are_fields_unthreadend(Board b, List<Figure> horizontalLineOfFigures, Team team) {
		List<Position> pos = horizontalLineOfFigures.stream().map(f -> b.getPosition_of_FigureWithId(f.getId()))
				.collect(Collectors.toCollection(LinkedList<Position>::new));
			if (BoardUtils.threats_at_Position(b, pos.get(0), team).size() != 0) {
				return false;
			}
			
			if (BoardUtils.threats_at_Position(b, pos.get(1), team).size() != 0) {
				return false;
			} 
		
		return true;
	}

	private static List<Move> getSpecialBauerMoves_if_possible(Bauer me, Board b, Position myP) {
		List<Move> moves = new LinkedList<Move>();
		moves.addAll(addTwiceIfPossible(b, me, myP));
		int upOrDown = (me.getTeam() == Team.WHITE? +1 : -1);
		int nearEnd = (me.getTeam() == Team.WHITE? 6 : 1);
	
		if (b.hasLastMove()) {
			Move lastMove = b.getLastMove();
			if (lastMove.getType() == MoveTyp.Twice) {
				Position bauer_twice = b.getPosition_of_FigureWithId(lastMove.getMovingFigure().getId());
				int bTCol = bauer_twice.getCol();
				int bTRow = bauer_twice.getRow();
				if (bTRow == myP.getRow()) {
					for (int i : new int[] { -1, 1 }) {
						if (myP.getCol() + i == bTCol) {
							moves.add(new Move(me, lastMove.getMovingFigure(), myP,
									new Position(bTRow + upOrDown,bTCol),
									MoveTyp.EnPassant, '-'));
						}
					}
				}
			}
		}
		if (myP.getRow() == nearEnd) {
			for (int i : new int[] { -1, +1 }) {
				if (b.isInBounds(myP.getCol() + i)) {
					Figure x = b.getFigure_at(myP.getRow() + upOrDown, myP.getCol() + i);
					if (!(x instanceof EmptyField) && x.getTeam() != me.getTeam()) {
						moves.addAll(FigureUtils.addSpecialBauer_moves(me, x, b));
					}
				}
			}
			Figure x = b.getFigure_at(myP.getRow() + upOrDown, myP.getCol());
			if ((x instanceof EmptyField)) {
				moves.addAll(FigureUtils.addSpecialBauer_moves(me, x, b));
			}
		}
		return moves;
	}

	private static List<Move> addTwiceIfPossible(Board b, Figure me, Position myP) {
		List<Move> l = new LinkedList<Move>();
		if (onStartingPosition_and_nextToFieldsAreEmpty(b, me, myP)) {
			l.add(new Move(me, new EmptyField(), myP,
					new Position(myP.getRow() + 2 * (me.getTeam() == Team.WHITE? +1 : -1), myP.getCol()), MoveTyp.Twice, '-'));
		}
		return l;
	}

	private static boolean onStartingPosition_and_nextToFieldsAreEmpty(Board b, Figure me, Position myP) {
		int whiteOrBlackStart = (me.getTeam() == Team.WHITE? 1 : 6);
		int direction = (me.getTeam() == Team.WHITE? +1 : -1);
		int myRow = myP.getRow();
		int myCol = myP.getCol();
		return myRow == whiteOrBlackStart && b.getFigure_at(myRow + 1 * direction, myCol) instanceof EmptyField
				&& b.getFigure_at(myRow + 2 * direction, myCol) instanceof EmptyField;
	}

}
