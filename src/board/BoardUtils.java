package board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import abstractFigure.*;
import abstractFigure.Figure.Team;
import figureWithIn.Bauer;
import figureWithIn.Dame;
import figureWithIn.EmptyField;
import figureWithIn.Koenig;
import figureWithIn.Laeufer;
import figureWithIn.Springer;
import figureWithIn.Turm;
import positionAndMove.Move;
import positionAndMove.Position;

public class BoardUtils {

	public static boolean is_ownKingThreathend_afterMove(Board b, Move m) {
		//Board b2 = b.copy();
		b.makeChange(m);
		boolean illegal = is_koenig_check(b, m.getTeam());
		//Board b3 = b.copy();
		b.reverseChange(m);
		/*if(!(b2.equals(b))) {
			BoardUtils.displayBoard(b2);
			System.out.println("||||||||||||||||||||");
			BoardUtils.displayBoard(b3);

			System.out.println("||||||||||||||||||||");

			BoardUtils.displayBoard(b);
			System.out.println();
		}*/
		return illegal;
	}

	public static Figure find_Koenig(Board b, Team team) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Figure f = b.getFigure_at(r, c);
				if (f instanceof Koenig && f.getTeam() == team) {
					return f;
				}
			}
		}
		throw new IllegalArgumentException("No Koenig: " + (team == Team.WHITE ? "white" : "black"));
	}

	public static List<Figure> find_figures(Board b, Team team) {
		List<Figure> f = new ArrayList<Figure>();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Figure rc = b.getFigure_at(r, c);
				if (!(rc instanceof EmptyField) && rc.getTeam() == team) {
					f.add(rc);
				}
			}
		}
		return f;
	}

	public static boolean is_koenig_check(Board b, Team team) {
		Figure koenig = find_Koenig(b, team);

		return threats_atPosition_byOtherTeams(b, koenig.getPosition(), team).size() > 0;

	}

	public static boolean is_checkMate(Board b, Team team) {
		return is_koenig_check(b, team) && (getMoves_byTeam(b, team).isEmpty());
	}

	public static List<Figure> threats_atPosition_byOtherTeams(Board b, Position p, Team team) {
		List<Figure> threats = new LinkedList<Figure>();
		List<Figure> types_exceptBauer = setUpFigures_exceptBauer_andEmptyField(b, team);

		threats.addAll(addThreats_forEachFigure_exceptBauer(types_exceptBauer, p));
		threats.addAll(addThreats_byBauer(b, p, team));

		return threats;

	}

	private static List<Figure> setUpFigures_exceptBauer_andEmptyField(Board b, Team team) {
		List<Figure> types = new ArrayList<Figure>();
		types.add(new Dame(b, team));
		types.add(new Laeufer(b, team));
		types.add(new Turm(b, team));
		types.add(new Springer(b, team));
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
		figure_moves = FigureUtils.getHostileFigures(figure_moves, m.getTeam());
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

	private static List<Figure> addThreats_byBauer(Board b, Position p, Team team) {
		List<Figure> bauer = new LinkedList<Figure>();
		bauer.addAll(GetAccessableFigures.addAttackFigure_Bauer(b, p.getRow(), p.getCol(), team));
		bauer = FigureUtils.getHostileFigures(bauer, team);
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

	public static List<Move> getMoves_byTeam(Board b, Team team) {
		List<Figure> figures = find_figures(b, team);
		List<Move> m = new LinkedList<Move>();
		for (Figure f : figures) {
			try {
				m.addAll(f.getMoves());
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println();
			}
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
		Team team = (Character.isUpperCase(sign)? Team.WHITE: Team.BLACK);
		sign = Character.toUpperCase(sign);
		return returnFigure(b, sign, team);
	}

	public static Figure returnFigure(Board b, char type, Team team) {
		switch (type) {
		case 'D':
			return new Dame(b, team);
		case 'K':
			return new Koenig(b, team);
		case 'L':
			return new Laeufer(b, team);
		case 'S':
			return new Springer(b, team);
		case 'B':
			return new Bauer(b, team);
		case 'T':
			return new Turm(b, team);
		case '-':
			return new EmptyField();
		default:
			throw new IllegalArgumentException("Type not found: " + type);
		}

	}

	public static void changeBoard(Board b, Move m) {
		b.makeChange(m);
	}
	
	public static void reverseBoard(Board b, Move m) {
		b.reverseChange(m);
	}
}

final class CompareFigures implements Comparator<Figure> {

	@Override
	public int compare(Figure f1, Figure f2) {
		if(f1.getValue() < f2.getValue()) {
			return +1;
		}
		if(f2.getValue() < f1.getValue()) {
			return -1;
		}
		return 0;
	}
	
}
