package Figures;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Main.*;


public abstract class Figure {
	
	protected boolean isWhite;
	protected Board board;
	protected int value;
	protected String name;
	protected Position position;
	
	public List<Move> getMoves() {
		List<Move> moves = FigureUtils.movesInRange(this);
		moves = moves.stream().filter(m -> BoardUtils.is_ownKingThreathend_afterMove(this.board, m)).collect(Collectors.toCollection(LinkedList<Move>::new));
		return moves;
	}

	abstract public List<Figure> getAccessableFigures();
	
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
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position p) {
		position = p;
	}
	
	public char firstChar() {
		return name.charAt(0);
	}
	
	
}
