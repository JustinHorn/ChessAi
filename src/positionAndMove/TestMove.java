package positionAndMove;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import board.Board;

public class TestMove {

	Board b = new Board();

	
	@Test
	void euqals() {
		
		Position p1 = new Position(1,2);
		Position p2 = new Position(2,1);
		
		Move m1 = new Move(b,p1,p2);
		Move m2 = new Move(b,p1,p2);
		
		assertFalse(m1==m2);
		assertTrue(m1.equals(m2));
		
	}
	
	@Test
	void invert() {
		Position p1 = new Position(1,2);
		Position p2 = new Position(2,1);
		
		Move m1 = new Move(b,p1,p2);
		Move m2 = new Move(b,p2,p1);
		
		assertFalse(m1.equals(m2));
		
	}
	
	@Test
	void falseMove() {
		
	}
}
