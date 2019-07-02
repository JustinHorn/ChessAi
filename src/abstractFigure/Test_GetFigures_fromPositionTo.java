package abstractFigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import board.*;
import figureWithIn.Bauer;
import figureWithIn.Dame;
import figureWithIn.Laeufer;
import figureWithIn.Springer;
import figureWithIn.Turm;
import positionAndMove.Position;

public class Test_GetFigures_fromPositionTo {

	private Board basicBoard() {
		char[][] b = new char[8][8];
		b[0] = "-------K".toCharArray();
		b[1] = "----S---".toCharArray();
		b[2] = "---s-T--".toCharArray();
		b[3] = "-b--D-d-".toCharArray();
		b[4] = "---l-B--".toCharArray();
		b[5] = "--------".toCharArray();
		b[6] = "--------".toCharArray();
		b[7] = "k-------".toCharArray();
		return new Board(b);
	}
	
	
	
	@Test
	void _upperLeftDiagonal_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> upperLeft_Diagonal = GetFigures_fromPositionTo._upperLeftDiagonal_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(upperLeft_Diagonal.get(0) instanceof Springer);
		assertFalse(upperLeft_Diagonal.get(0).isWhite());
	}
	
	@Test
	void _upperRightDiagonal_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> upperRight_Diagonal = GetFigures_fromPositionTo._upperRightDiagonal_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(upperRight_Diagonal.get(0) instanceof Turm);
		assertTrue(upperRight_Diagonal.get(0).isWhite());
	}
	
	@Test
	void _lowerLeftDiagonal_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> lowerLeft_Diagonal = GetFigures_fromPositionTo._lowerLeftDiagonal_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(lowerLeft_Diagonal.get(0) instanceof Laeufer);
		assertFalse(lowerLeft_Diagonal.get(0).isWhite());
	}
	
	@Test
	void _lowerRightDiagonal_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> lowerRight_Diagonal = GetFigures_fromPositionTo._lowerRightDiagonal_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(lowerRight_Diagonal.get(0) instanceof Bauer);
		assertTrue(lowerRight_Diagonal.get(0).isWhite());
	}
	
	@Test
	void _left_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> left_row_fromPosition = GetFigures_fromPositionTo._left_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(left_row_fromPosition.get(2) instanceof Bauer);
		assertFalse(left_row_fromPosition.get(2).isWhite());
	}
	
	@Test
	void _right_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> right_row_fromPosition = GetFigures_fromPositionTo._right_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(right_row_fromPosition.get(1) instanceof Dame);
		assertFalse(right_row_fromPosition.get(1).isWhite());
	}
	
	
	@Test
	void _above_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> upCol_from_position = GetFigures_fromPositionTo._above_until_includeNotEmptyField(b,white_Dame_position);
		
		assertTrue(upCol_from_position.get(1) instanceof Springer);
		assertTrue(upCol_from_position.get(1).isWhite());
	}
	
	@Test
	void _under_fromPosition_until_includeNotEmptyField() {
		Board b = basicBoard();	
		Position white_Dame_position = new Position(3,4);
		List<Figure> downCol_from_position = GetFigures_fromPositionTo._under_until_includeNotEmptyField(b,white_Dame_position);
		
		assertEquals(4,downCol_from_position.size());
	}
}
