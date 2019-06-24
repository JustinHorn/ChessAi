package Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Figures.*;

public class BoardUtils {


	public static Figure find_whiteKoenig(Board b) {
		return find_Koenig(b,true);
	}
	
	public static Figure find_blackKoenig(Board b) {
		return find_Koenig(b,false);
	}

	public static List<Figure> getWhiteFigures(Board b) {
		return find_figures(b,true);
	}
	
	public static List<Figure> getBlackFigures(Board b) {
		return find_figures(b,false);
	}
	
	public static List<Figure> threads_byWhite_atPosition(Board b, Position p) {
		return threats_at_Position(b,p,false);
	}
	
	public static List<Figure> threads_byBlack_atPosition(Board b, Position p) {
		return threats_at_Position(b,p,true);
	}
	
	public static boolean is_whiteKoenig_check(Board b) {
		return is_koenig_check(b,true);
	}
	
	public static boolean is_blackKoenig_check(Board b) {
		return is_koenig_check(b,false);
	}
	
	public static boolean is_blackKoenig_checkMate(Board b) {
		return is_checkMate(b,false);
	}

	public static boolean is_whiteKoenig_checkMate(Board b) {
		return is_checkMate(b,true);
	}
	
	public static List<Move> getWhiteMoves(Board b) {
		return getMoves_byColor(b,true);
	}
	
	public static List<Move> getBlackMoves(Board b) {
		return getMoves_byColor(b,false);
	}
	
	public static boolean is_ownKingThreathend_afterMove(Board b, Move m) {
		Figure toBeMoved = b.getFigure_at(m.fromPostion());
		b.changeBoard(m);
		boolean illegal = is_koenig_check(b,toBeMoved.isWhite());
		b.reverseBoard(m);
		return !illegal;
	}	
	
	private static Figure find_Koenig(Board b, boolean white) {
		for(int r = 0; r < 8; r++) {
			for(int c=0; c < 8; c++) {
				Figure rc = b.getFigure_at(r, c);
				if(rc instanceof Koenig && rc.isWhite() == white) {
					return rc;
				}
			}
		}
		return new EmptyField(new Position(0,0));
	}
	
	private static List<Figure> find_figures(Board b, boolean white) {
		List<Figure> f = new ArrayList<Figure>();
		for(int r = 0; r < 8; r++) {
			for(int c=0; c < 8; c++) {
				Figure rc = b.getFigure_at(r, c);
				if(!(rc instanceof EmptyField) && rc.isWhite() == white) {
					f.add( rc);
				}
			}
		}
		return f;
	}

	public static boolean is_koenig_check(Board b, boolean isWhite) {
		Figure koenig = find_Koenig(b, isWhite);
		return threats_at_Position(b, koenig.getPosition(), isWhite).size() > 0;
	}
	
	public static boolean is_checkMate(Board b,boolean isWhite) {
		return is_koenig_check(b, isWhite) && (getMoves_byColor(b,isWhite).isEmpty());
	}	
	
	private static List<Figure> threats_at_Position(Board b, Position p, boolean isWhite) {
		List<Figure> threats = new LinkedList<Figure>();
		List<Figure> types_exceptBauer = setUpFigures_exceptBauer_andEmptyField(b,p,isWhite);
		
		
		threats.addAll(addThreats_forEachFigure_exceptBauer(types_exceptBauer));
		threats.addAll(addThreats_byBauer(b,p,isWhite));
		
		return threats;
		
	}
	
	private static List<Figure> setUpFigures_exceptBauer_andEmptyField(Board b, Position p, boolean isWhite) {
		List<Figure> types = new ArrayList<Figure>();
		types.add(new Dame(b,p,isWhite));
		types.add(new Laeufer(b,p,isWhite));
		types.add(new Turm(b,p,isWhite));
		types.add(new Springer(b,p,isWhite));
		return types;
	}
	
	private static List<Figure> addThreats_forEachFigure_exceptBauer(List<Figure> figures) {
		List<Figure> threats = new LinkedList<Figure>();
		for(Figure figure: figures) {
			threats.addAll(getThreats_byFigure_exceptBauer(figure));
		}
		return threats;
	}
	
	private static List<Figure> getThreats_byFigure_exceptBauer(Figure m) {
		List<Figure> figure_moves = m.getAccessableFigures();
		GetAccessableFigures.removeFriendlyFigures_and_EmptyFields(figure_moves, m.isWhite());
		int i = 0;
		while(i < figure_moves.size()) {
			Figure f = figure_moves.get(i);
			if(f.getName().equals(m.getName())) { // apperently I can not access getClass because that is not static?!
				i++;
			} else {
				figure_moves.remove(f);
			}
		}
		return figure_moves;
	}
	
	
	private static List<Figure> addThreats_byBauer(Board b, Position p, boolean isWhite) {
		List<Figure> bauer = new LinkedList<Figure>();
		GetAccessableFigures.addAttackFigure_Bauer(b, bauer,p.getRow(),p.getCol(),isWhite);
		GetAccessableFigures.removeFriendlyFigures_and_EmptyFields(bauer, isWhite);
		int i = 0;
		while(i < bauer.size()) {
			Figure f = bauer.get(i);
			if(f instanceof Bauer) {
				i++;
			} else {
				bauer.remove(f);
			}
		}
		return bauer;
	}
	
	private static List<Move> getMoves_byColor(Board b,boolean isWhite) {
		List<Figure> figures = find_figures(b,isWhite);
		List<Move> m = new LinkedList<Move>();
		for(Figure f : figures) {
			m.addAll(f.getMoves());
		}
		return m;
	}

	
}
