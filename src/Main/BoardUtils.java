package Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import Figures.*;

public class BoardUtils {

	public static Figure find_whiteKoenig(Board b) {
		return find_Koenig(b, true);
	}

	public static Figure find_blackKoenig(Board b) {
		return find_Koenig(b, false);
	}

	public static List<Figure> getWhiteFigures(Board b) {
		return find_figures(b, true);
	}

	public static List<Figure> getBlackFigures(Board b) {
		return find_figures(b, false);
	}

	public static List<Figure> threads_byWhite_atPosition(Board b, Position p) {
		return threats_at_Position(b, p, false);
	}

	public static List<Figure> threads_byBlack_atPosition(Board b, Position p) {
		return threats_at_Position(b, p, true);
	}

	public static boolean is_whiteKoenig_check(Board b) {
		return is_koenig_check(b, true);
	}

	public static boolean is_blackKoenig_check(Board b) {
		return is_koenig_check(b, false);
	}

	public static boolean is_blackKoenig_checkMate(Board b) {
		return is_checkMate(b, false);
	}

	public static boolean is_whiteKoenig_checkMate(Board b) {
		return is_checkMate(b, true);
	}

	public static List<Move> getWhiteMoves(Board b) {
		return getMoves_byColor(b, true);
	}

	public static List<Move> getBlackMoves(Board b) {
		return getMoves_byColor(b, false);
	}

	public static boolean is_ownKingThreathend_afterMove(Board b, Move m) {
		b.makeChange(m);
		boolean illegal = is_koenig_check(b, m.isWhite());
		b.reverseChange(m);
		return !illegal;
	}

	private static Figure find_Koenig(Board b, boolean white) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Figure f = b.getFigure_at(r, c);
				if (f instanceof Koenig && f.isWhite() == white) {
					return f;
				}
			}
		}
		throw new IllegalArgumentException("No Koenig: "+(white?"white":"black"));
	}

	private static List<Figure> find_figures(Board b, boolean white) {
		List<Figure> f = new ArrayList<Figure>();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Figure rc = b.getFigure_at(r, c);
				if (!(rc instanceof EmptyField) && rc.isWhite() == white) {
					f.add(rc);
				}
			}
		}
		return f;
	}

	public static boolean is_koenig_check(Board b, boolean isWhite) {
		Figure koenig = find_Koenig(b, isWhite);

		return threats_at_Position(b, koenig.getPosition(), isWhite).size() > 0;

	}

	public static boolean is_checkMate(Board b, boolean isWhite) {
		return is_koenig_check(b, isWhite) && (getMoves_byColor(b, isWhite).isEmpty());
	}

	private static List<Figure> threats_at_Position(Board b, Position p, boolean isWhite) {
		List<Figure> threats = new LinkedList<Figure>();
		List<Figure> types_exceptBauer = setUpFigures_exceptBauer_andEmptyField(b, isWhite);

		threats.addAll(addThreats_forEachFigure_exceptBauer(types_exceptBauer, p));
		threats.addAll(addThreats_byBauer(b, p, isWhite));

		return threats;

	}

	private static List<Figure> setUpFigures_exceptBauer_andEmptyField(Board b, boolean isWhite) {
		List<Figure> types = new ArrayList<Figure>();
		types.add(new Dame(b, isWhite));
		types.add(new Laeufer(b, isWhite));
		types.add(new Turm(b, isWhite));
		types.add(new Springer(b, isWhite));
		return types;
	}

	private static List<Figure> addThreats_forEachFigure_exceptBauer(List<Figure> figures, Position p) {
		List<Figure> threats = new LinkedList<Figure>();
		for (Figure figure : figures) {
			threats.addAll(getThreats_byFigure_exceptBauer(figure, p));
		}
		return threats;
	}

	private static List<Figure> getThreats_byFigure_exceptBauer(Figure m, Position p) {
		List<Figure> figure_moves = m.getAccessableFigures(p);
		GetAccessableFigures.removeFriendlyFigures_and_EmptyFields(figure_moves, m.isWhite());
		int i = 0;
		while (i < figure_moves.size()) {
			Figure f = figure_moves.get(i);
			if (f.getName().equals(m.getName())) { // apperently I can not access getClass because that is not static?!
				i++;
			} else {
				figure_moves.remove(f);
			}
		}
		return figure_moves;
	}

	private static List<Figure> addThreats_byBauer(Board b, Position p, boolean isWhite) {
		List<Figure> bauer = new LinkedList<Figure>();
		GetAccessableFigures.addAttackFigure_Bauer(b, bauer, p.getRow(), p.getCol(), isWhite);
		GetAccessableFigures.removeFriendlyFigures_and_EmptyFields(bauer, isWhite);
		int i = 0;
		while (i < bauer.size()) {
			Figure f = bauer.get(i);
			if (f instanceof Bauer) {
				i++;
			} else {
				bauer.remove(f);
			}
		}
		return bauer;
	}

	private static List<Move> getMoves_byColor(Board b, boolean isWhite) {
		List<Figure> figures = find_figures(b, isWhite);
		List<Move> m = new LinkedList<Move>();
		for (Figure f : figures) {
			m.addAll(f.getMoves());
		}
		return m;
	}

	public static void displayBoard(Board b) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(b.getFigure_at(i, j).firstChar());
			}
			System.out.println();
		}
	}

	public static Figure char_toFigures(Board b, char sign, Position p) {
		boolean isWhite = Character.isUpperCase(sign);
		sign = Character.toUpperCase(sign);
		return returnFigure(b, sign, isWhite);
	}

	public static Figure returnFigure(Board b, char type, boolean isWhite) {
		switch (type) {
		case 'D':
			return new Dame(b, isWhite);
		case 'K':
			return new Koenig(b, isWhite);
		case 'L':
			return new Laeufer(b, isWhite);
		case 'S':
			return new Springer(b, isWhite);
		case 'B':
			return new Bauer(b, isWhite);
		case 'T':
			return new Turm(b, isWhite);
		case '-':
			return new EmptyField();
		default:
			throw new IllegalArgumentException("Type not found: " + type);
		}

	}

	public static List<Move> getSpecialMoves_forMe(Board b, Figure me) {
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
		if (b.hasFigure_beenMoved(k.getId())) {

		} else {
			moves.addAll(getPossible_rochadeMove(b, k,myP, true));
			moves.addAll(getPossible_rochadeMove(b, k,myP, false));
		}
		return moves;
	}

	private static List<Move> getPossible_rochadeMove(Board b, Figure me,Position myP, boolean leftOrRight) {
		List<Figure> figures;
		if (leftOrRight) {
			figures = GetFigures_fromPositionTo._left_until_includeNotEmptyField(b, myP);
		} else {
			figures = GetFigures_fromPositionTo._right_until_includeNotEmptyField(b, myP);
		}
		int row = (me.isWhite() ? 0 : 7);
		int turmCol = (leftOrRight ? 0 : 7);

		Figure turm = b.getFigure_at(row, turmCol);
		LinkedList<Move> m = new LinkedList<Move>();

		if (turm instanceof Turm && turm.isWhite() == me.isWhite()&& areRochadeFieldsEmpty(figures) && are_fields_unthreadend(b, figures, me.isWhite())
				&& !b.hasFigure_beenMoved(turm.getId())) {
				m.add(new Move(me, new EmptyField(), myP, new Position(row, 3+(me.isWhite()?-2:+2)), MoveTyp.Rochade, 'K'));
			
		}
		return m;
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

	private static boolean are_fields_unthreadend(Board b, List<Figure> horizontalLineOfFigures, boolean isWhite) {
		List<Position> pos = horizontalLineOfFigures.stream().map(f -> b.getPosition_of_FigureWithId(f.getId()))
				.collect(Collectors.toCollection(LinkedList<Position>::new));
		int s = pos.size();
		for (int i = 0; i < s - 1; i++) {
			if (BoardUtils.threats_at_Position(b, pos.get(i), isWhite).size() == 0) {

			} else {
				return false;
			}
		}
		return true;
	}

	private static List<Move> getSpecialBauerMoves_if_possible(Bauer me, Board b, Position myP) {
		List<Move> moves = new LinkedList<Move>();
		moves.addAll(addTwiceIfPossible(b, me, myP));
		int upOrDown = (me.isWhite() ? +1 : -1);
		int nearEnd = (me.isWhite() ? 6 : 1);

		if (b.hasLastMove()) {
			Move lastMove = b.getLastMove();
			if (lastMove.getType() == MoveTyp.Twice) {
				Position bauer_twice = b.getPosition_of_FigureWithId(lastMove.getMovingFigure().getId());
				if (bauer_twice.getRow() == myP.getRow()) {
					if (myP.getCol() - 1 == bauer_twice.getCol()) {
						moves.add(new Move(me, lastMove.getMovingFigure(), myP,
								new Position(bauer_twice.getRow() + upOrDown, bauer_twice.getCol()), MoveTyp.EnPassant,
								'-'));
					}
					if (myP.getCol() + 1 == bauer_twice.getCol()) {
						moves.add(new Move(me, lastMove.getMovingFigure(), myP,
								new Position(bauer_twice.getRow() + upOrDown, bauer_twice.getCol()), MoveTyp.EnPassant,
								'-'));
					}
				}
			}
		}
		if (myP.getRow() == nearEnd) {
			if (b.isInBounds(myP.getCol() - 1)) {
				Figure x = b.getFigure_at(myP.getRow() + upOrDown, myP.getCol() - 1);
				if (!(x instanceof EmptyField) && x.isWhite() != me.isWhite()) {
					moves.addAll(FigureUtils.addSpecialBauer_moves(me, x, b));
				}
			}
			if (b.isInBounds(myP.getCol() + 1)) {
				Figure x = b.getFigure_at(myP.getRow() + upOrDown, myP.getCol() + 1);
				if (!(x instanceof EmptyField) && x.isWhite() != me.isWhite()) {
					moves.addAll(FigureUtils.addSpecialBauer_moves(me, x, b));
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
					new Position(myP.getRow() + 2 * (me.isWhite() ? +1 : -1), myP.getCol()),MoveTyp.Twice,'-'));
		}
		return l;
	}

	private static boolean onStartingPosition_and_nextToFieldsAreEmpty(Board b, Figure me, Position myP) {
		int whiteOrBlackStart = (me.isWhite() ? 1 : 6);
		int direction = (me.isWhite() ? +1 : -1);
		int myRow = myP.getRow();
		int myCol = myP.getCol();
		return myRow == whiteOrBlackStart && b.getFigure_at(myRow + 1 * direction, myCol) instanceof EmptyField
				&& b.getFigure_at(myRow + 2 * direction, myCol) instanceof EmptyField;
	}
}
