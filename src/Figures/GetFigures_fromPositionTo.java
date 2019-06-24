package Figures;

import java.util.LinkedList;
import java.util.List;

import Main.Board;
import Main.Move;
import Main.Position;

public class GetFigures_fromPositionTo {



	public static List<Figure> _upperLeftDiagonal_until_includeNotEmptyField(Board board,
			Position position) {
		return get_until_includeNotEmptyField(board, position, -1, -1);
	}

	public static List<Figure> _upperRightDiagonal_until_includeNotEmptyField(Board board,
			Position position) {
		return get_until_includeNotEmptyField(board, position, +1, -1);
	}

	public static List<Figure> _lowerLeftDiagonal_until_includeNotEmptyField(Board board,
			Position position) {
		return get_until_includeNotEmptyField(board, position, -1, +1);
	}

	public static List<Figure> _lowerRightDiagonal_until_includeNotEmptyField(Board board,
			Position position) {
		return get_until_includeNotEmptyField(board, position, +1, +1);
	}

	public static List<Figure> _right_until_includeNotEmptyField(Board board, Position position) {
		return get_until_includeNotEmptyField(board, position, +1, 0);
	}

	public static List<Figure> _left_until_includeNotEmptyField(Board board, Position position) {
		return get_until_includeNotEmptyField(board, position, -1, 0);
	}

	public static List<Figure> _above_until_includeNotEmptyField(Board board, Position position) {
		return get_until_includeNotEmptyField(board, position, 0, -1);
	}

	public static List<Figure> _under_until_includeNotEmptyField(Board board, Position position) {
		return get_until_includeNotEmptyField(board, position, 0, +1);
	}

	private static List<Figure> get_until_includeNotEmptyField(Board b, Position p, int leftRight, int upDown) {
		leftRight = (int) Math.signum(leftRight);
		upDown = (int) Math.signum(upDown);
		List<Figure> list = new LinkedList<Figure>();
		int r = p.getRow() + upDown;
		int c = p.getCol() + leftRight;
		if (b.isInBounds(r) && b.isInBounds(c)) {
			do {
				list.add(b.getFigure_at(r, c));
				r += upDown;
				c += leftRight;
			} while (b.isInBounds(r) && b.isInBounds(c) && list.get(list.size() - 1) instanceof EmptyField);
		}
		return list;
	}

}
