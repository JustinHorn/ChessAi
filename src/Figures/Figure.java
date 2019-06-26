
package Figures;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Main.*;


public abstract class Figure {
	
	protected boolean isWhite;
	private Board board;
	protected int value;
	protected String name;
	private static int count = 0;
	private final int id;
	
	public Figure(Board b) {
		id = count++;
		board = b;
	}
	
	
	public List<Move> getMoves() {
		List<Move> moves = FigureUtils.movesInRange(this);
		moves = moves.stream().filter(m -> BoardUtils.is_ownKingThreathend_afterMove(this.board, m)).collect(Collectors.toCollection(LinkedList<Move>::new));
		return moves;
	}

	abstract public List<Figure> getAccessableFigures();
	
	abstract public  List<Figure> getAccessableFigures(Position p);
	

	
	public Position getPosition() {
		return board.getPosition_ofFigureWithId(id);
	}
	
	protected Board getBoard() {
		return board;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isWhite() {
		return isWhite;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public char firstChar() {
		return name.charAt(0);
	}
}