package ai;

import java.util.LinkedList;
import java.util.List;

import positionAndMove.*;

public class ValueAndMoveContainer {
	
	private double value;
	private List<Move> moves= new LinkedList<Move>();
	
	
	public ValueAndMoveContainer(double value) {
		this.value=value;
	}
	
	public void addMove(Move move) {
		moves.add(move);
	}
	
	public void removeMove(Move m) {
		moves.remove(m);
	}
	
	public double getValue() {
		return value;
	}
	
	public void invertValue() {
		value = -1*value;
	}
	
	public Move getLastMove() {
		return moves.get(moves.size()-1);
	}
	
}
