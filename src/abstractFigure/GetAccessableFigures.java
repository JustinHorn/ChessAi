package abstractFigure;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import board.Board;
import figureWithIn.EmptyField;
import positionAndMove.Position;

/**
 * 
 * @author Murtag Has functions that can be used by @Figure Classes to return
 *         the other figures, that are in its.
 */
public class GetAccessableFigures {
	
	/**
	 * Also: List = OutputArgument!
	 * @return 
	 */
	public static List<Figure> removeFriendlyFigures_and_EmptyFields(List<Figure> list, boolean white_team_friendly) {
		List<Figure> hostile = new LinkedList<Figure>();
		Iterator<Figure> it = list.iterator();
		while(it.hasNext()) {
			Figure f = it.next();
			if ((f instanceof EmptyField) || f.isWhite() == white_team_friendly) {
				
			}else {
				hostile.add(f);
			}
		}
		return hostile;
	}
	
	
	public static List<Figure> koenigMovement(Figure me) {
		return koenigMovement(me.getBoard(),me.getPosition());
	}
	
	public static List<Figure> koenigMovement(Board b, Position p) {
		int r = p.getRow();
		int c = p.getCol();
		List<Figure> figures = new LinkedList<Figure>();

		int[] value = { -1, 0, 1 };
		for (int i : value) 
			if (b.isInBounds(r + i)) 
				for (int j : value) 
					if (b.isInBounds(c+j)) 
						figures.add(b.getFigure_at(r + i, c+j));
		
						
		figures.remove(b.getFigure_at(p));
		return figures;
	}

	public static List<Figure> springerMovement(Figure me) {
		Board b = me.getBoard();
		return springerMovement(me.getBoard(),me.getPosition());
	}
	
	public static List<Figure> springerMovement(Board b, Position p) {
		int r = p.getRow();
		int c = p.getCol();
		List<Figure> figures = new LinkedList<Figure>();

		int[] f = { 1, 2 };
		int[] vorzeichen = { -1, 1 };
		for (int i : new int[] { 0, 1 }) {
			for (int v : vorzeichen) {
				if (b.isInBounds(r + f[i] * v)) {
					for (int v2 : vorzeichen) {
						if (b.isInBounds(c + f[1 - i] * v2)) {
							figures.add(b.getFigure_at(r + f[i] * v, c + f[1 - i] * v2));
						}
					}
				}
			}
		}

		return figures;
	}


	public static List<Figure> dameMovement(Figure me) {
		return dameMovement(me.getBoard(),me.getPosition());
	}
	
	public static List<Figure> dameMovement(Board b, Position p) {
		List<Figure> turm = turmMovement(b,p);
		List<Figure> lauefer = laeuferMovement(b,p);

		turm.addAll(lauefer);
		return turm;
	}

	public static List<Figure> turmMovement(Figure me) {
		return turmMovement(me.getBoard(), me.getPosition());
	}
	
	public static List<Figure> turmMovement(Board b,Position p) {
		List<Figure> horizontal = horizontal(b,p);
		List<Figure> vertical = vertical(b,p);

		horizontal.addAll(vertical);
		return horizontal;
	}

	public static List<Figure> laeuferMovement(Figure me) {
		return laeuferMovement(me.getBoard(),me.getPosition()); 
	}
	

	public static List<Figure> laeuferMovement(Board b, Position p) {
		List<Figure> right_left = rightToLeftDiagonal(b,p);
		List<Figure> left_right = leftToRightDiagonal(b,p);
		right_left.addAll(left_right);

		return right_left;
	}

	public static List<Figure> bauerMovement(Figure me) {
		return bauerMovement(me.getBoard(), me.getPosition(), me.isWhite());
	}

	public static List<Figure> bauerMovement(Board board, Position position, boolean isWhite) {
		List<Figure> figures = new LinkedList<Figure>();
		
		int edge = (isWhite?6:1);
		
		if( position.getRow() != edge) {
			int direction = isWhite ? 1 : -1;
			int r = position.getRow() + direction;
			int c = position.getCol();
			figures.addAll(getFigures(board,  r, c, isWhite));
		}
		return figures;
	}
	
	private static 	List<Figure> getFigures(Board board,int r,int c, boolean isWhite) {
		List<Figure> f = new LinkedList<Figure>();
		Figure figure_infront = board.getFigure_at(r, c);
		if (figure_infront instanceof EmptyField) {
			f.add(figure_infront);
		}
		f.addAll(addAttackFigure_Bauer(board,  r, c, isWhite));
		return f;
	}
	
	public static List<Figure> addAttackFigure_Bauer(Board board,int r,int c, boolean isWhite) {
		List<Figure> f = new LinkedList<Figure>();
		f.addAll(checkBauer_leftRight_updateFront(board, r, c + 1, isWhite));
		f.addAll(checkBauer_leftRight_updateFront(board, r, c - 1, isWhite));
		return f;
	}

	private static List<Figure> checkBauer_leftRight_updateFront(Board b, int r, int c,boolean boolWhite) {
		List<Figure> figures = new LinkedList<Figure>();
		if (b.isInBounds(c)) {
			Figure figure_front_right = b.getFigure_at(r, c);
			if (!(figure_front_right instanceof EmptyField) && figure_front_right.isWhite() != boolWhite) {
				figures.add(figure_front_right);
			}
		}
		return figures;
	}

	public static List<Figure> leftToRightDiagonal(Figure me) {
		return leftToRightDiagonal(me.getBoard(), me.getPosition());
	}

	public static List<Figure> rightToLeftDiagonal(Figure me) {
		return rightToLeftDiagonal(me.getBoard(), me.getPosition());
	}

	public static List<Figure> vertical(Figure me) {
		return vertical(me.getBoard(), me.getPosition());
	}

	public static List<Figure> horizontal(Figure me) {
		return horizontal(me.getBoard(), me.getPosition());
	}

	public static List<Figure> rightToLeftDiagonal(Board board, Position p) {
		List<Figure> lower_left = GetFigures_fromPositionTo._lowerLeftDiagonal_until_includeNotEmptyField(board,
				p);
		List<Figure> upper_right = GetFigures_fromPositionTo._upperRightDiagonal_until_includeNotEmptyField(board,
				p);

		lower_left.addAll(upper_right);
		return lower_left;
	}

	public static List<Figure> leftToRightDiagonal(Board b, Position p) {
		List<Figure> upper_left = GetFigures_fromPositionTo._upperLeftDiagonal_until_includeNotEmptyField(b, p);
		List<Figure> lower_right = GetFigures_fromPositionTo._lowerRightDiagonal_until_includeNotEmptyField(b, p);

		upper_left.addAll(lower_right);
		return upper_left;
	}

	public static List<Figure> vertical(Board b, Position p) {
		List<Figure> above = GetFigures_fromPositionTo._above_until_includeNotEmptyField(b, p);
		List<Figure> under = GetFigures_fromPositionTo._under_until_includeNotEmptyField(b, p);

		above.addAll(under);
		return above;
	}

	public static List<Figure> horizontal(Board b, Position p) {
		List<Figure> left = GetFigures_fromPositionTo._left_until_includeNotEmptyField(b, p);
		List<Figure> right = GetFigures_fromPositionTo._right_until_includeNotEmptyField(b, p);

		left.addAll(right);
		return left;
	}

	/**
	 * Also: List = OutputArgument!
	 */
	private static void removeLastElement_if_friendly(List<Figure> list, boolean white_team) {
		Figure last = list.get(list.size() - 1);
		if (!(last instanceof EmptyField) && (last.isWhite() == white_team)) {
			list.remove(last);
		}
	}


}