
package abstractFigure;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import board.*;
import figureWithIn.EmptyField;
import positionAndMove.Move;
import positionAndMove.Position;


public abstract class Figure {
	
	protected Team team;
	private Board board;
	protected int value;
	protected String name;
	private static int count = 0;
	private final int id;
	
	public enum Team {
		WHITE(0),BLACK(1);
		
		private final int i;
		
		private Team(int i) {
			this.i = i;
		}
		
		public int getI() {
			return i;
		}
		
	}
	
	public Figure(Board b) {
		id = count++;
		board = b;
	}
	
	
	public List<Move> getMoves() {
		List<Move> moves = FigureUtils.movesInRange(this);
		moves.addAll(GetSpecialMovesFromBoard.getMySpecialMoves(board, this));
		moves = moves.stream().filter(m -> !BoardUtils.is_ownKingThreathend_afterMove(this.board, m)).collect(Collectors.toCollection(LinkedList<Move>::new));
		return moves;
	}

	abstract public List<Figure> getAccessableFigures();
	
	abstract public List<Figure> getAccessableFigures(Position p);
	
	public static List<Figure> getAccessableFigures(Board b, Position p, boolean isWhite) {
		return new LinkedList<Figure>();
	}

	
	public Position getPosition() {
		return board
				.getPosition_of_FigureWithId(id);
	}
	
	protected Board getBoard() {
		return board;
	}
	
	public int getValue() {
		return value;
	}
	
	public Team getTeam() {
		return team;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public char firstChar() {
		if(this instanceof EmptyField) {
			return '-';
		}
		char s = name.charAt(0);
		if(team == Team.BLACK) s = Character.toLowerCase(s);
		return s;
	}
}