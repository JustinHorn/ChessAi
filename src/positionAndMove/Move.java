package positionAndMove;

import abstractFigure.*;
import abstractFigure.Figure.Team;
import board.Board;
import figureWithIn.EmptyField;

public class Move {
	private Position fromPosition;
	private Position toPosition;
	private Figure movingFigure;
	private Figure defeatedFigure;
	private MoveTyp type;
	private char typeModifier;

	public Move(Board b, Position start, Position end) {
		assignVars(b.getFigure_at(start), b.getFigure_at(end), start, end, MoveTyp.Normal, '-');
		errorHandling(movingFigure, defeatedFigure, start, end);
	}

	public Move(Board b, Position start, Position end, MoveTyp kind,char f) {
		assignVars(b.getFigure_at(start), b.getFigure_at(end), start, end, kind, f);
		errorHandling(movingFigure, defeatedFigure, start, end);
	}
	
	public Move(Figure movingFigure,Figure defeatedFigure, Position start, Position end) {
		assignVars(movingFigure, defeatedFigure, start, end, MoveTyp.Normal, '-');
		errorHandling(movingFigure, defeatedFigure, start, end);
	}
	
	public Move(Figure movingFigure,Figure defeatedFigure, Position start, Position end,MoveTyp kind,char f) {
		assignVars(movingFigure, defeatedFigure, start, end, kind, f);
		errorHandling(movingFigure, defeatedFigure, start, end);
	}
	
	private void assignVars(Figure movingFigure,Figure defeatedFigure, Position start, Position end,MoveTyp kind,char f) {
		fromPosition = start;
		toPosition = end;
		this.movingFigure = movingFigure;
		this.defeatedFigure = defeatedFigure;
		type = kind;
		typeModifier = f;
	}
	
	private void errorHandling(Figure movingFigure,Figure defeatedFigure, Position start, Position end ) {
		if(start.equals(end)) {
			throw new IllegalArgumentException("Start and End position of the move are the same");
		}
		if(movingFigure instanceof EmptyField) {
			throw new IllegalArgumentException("An EmptyField can not make a move");
		}
	
	}
	
	public Position fromPosition() {
		return fromPosition;
	}
	
	public Position toPosition() {
		return toPosition;
	}

	public Figure getMovingFigure() {
		return movingFigure;
	}
	
	public Figure getDefeatedFigure() {
		return defeatedFigure;
	}
	
	public Team getTeam( ) {
		return movingFigure.getTeam();
	}
	
	@Override
	public boolean equals(Object move) {
		if(move instanceof Move) {
			Move m =(Move) move;
			if(m.fromPosition.equals(fromPosition)&& m.toPosition.equals(toPosition) 
					&& movingFigure.equals(m.getMovingFigure())&& defeatedFigure.equals(defeatedFigure)
					&& m.getType() == type && m.getTypeModifier() == typeModifier) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "from: "+fromPosition+" to: "+toPosition;
	}

	public MoveTyp getType() {
		return type;
	}

	public char getTypeModifier() {
		return typeModifier;
	}

}
