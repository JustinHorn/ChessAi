package Main;

import Figures.Figure;

public class Move {
	private Position fromPosition;
	private Position toPosition;
	private Figure movingFigure;
	private Figure defeatedFigure;
	
	public Move(Board b, Position start, Position end) {
		fromPosition = start;
		toPosition = end;
		if(start.equals(end)) {
			throw new IllegalArgumentException("Start and End position of the move are the same");
		}
		movingFigure = b.getFigure_at(start);
		defeatedFigure = b.getFigure_at(end);
	}
	
	public Move(Figure movingFigure,Figure defeatedFigure, Position start, Position end) {
		fromPosition = start;
		toPosition = end;
		if(start.equals(end)) {
			throw new IllegalArgumentException("Start and End position of the move are the same");
		}
		this.movingFigure = movingFigure;
		this.defeatedFigure = defeatedFigure;
	}
	
	public Position fromPostion() {
		return fromPosition;
	}
	
	public Position toPostion() {
		return toPosition;
	}

	public Figure getMovingFigure() {
		return movingFigure;
	}
	
	public Figure getDefeatedFigure() {
		return defeatedFigure;
	}
	
	public boolean isWhite( ) {
		return movingFigure.isWhite();
	}
	
	@Override
	public boolean equals(Object move) {
		if(move instanceof Move) {
			Move m =(Move) move;
			if(m.fromPosition.equals(fromPosition)&& m.toPosition.equals(toPosition) 
					&& movingFigure.equals(m.getMovingFigure())&& defeatedFigure.equals(defeatedFigure)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "from: "+fromPosition+" to: "+toPosition;
	}
	
	public Move getInversion() {
		return new Move(defeatedFigure,movingFigure, toPosition,fromPosition);
	}
}
