package Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;


import Figures.*;

public class Test_Board {

	private Board basicBoard() {
		char[][] b = new char[8][8];
		b[0] = "BLSDT--K".toCharArray();
		b[1] = "blsdt---".toCharArray();
		b[2] = "--------".toCharArray();
		b[3] = "--------".toCharArray();
		b[4] = "--------".toCharArray();
		b[5] = "--------".toCharArray();
		b[6] = "--------".toCharArray();
		b[7] = "k-------".toCharArray();
		return new Board(b);
	}
	
	
	@Test
	void parseBoard_fromString() {
		Board board = basicBoard();
		
		Figure white_king = board.getFigure_at(0, 7);
		Figure black_king = board.getFigure_at(7, 0);
		
		assertEquals("Koenig",white_king.getName());
		assertEquals(true,white_king.isWhite());

		assertEquals("Koenig",black_king.getName());
		assertEquals(false,black_king.isWhite());
		
	}
	
	@Test
	void test_getFigureById() {
		Board board = basicBoard();
		Figure white_Bauer = board.getFigure_at(0, 0);
		Figure same_white_Bauer = board.getFigure_at( board.getPosition_ofFigureWithId(0));
		
		assertEquals(white_Bauer,same_white_Bauer);
	}
	
	
	
	@Test
	void changeBoard() {
		Board board = basicBoard();
		
		board.changeBoard(new Move(board,new Position(0,7),new Position(7,0)));
		
		Figure nothing = board.getFigure_at(0, 7);
		Figure white_king = board.getFigure_at(7, 0);
		
		assertEquals("Koenig",white_king.getName());
		assertTrue(white_king.isWhite());

		assertTrue(nothing instanceof EmptyField);
		
	}
	
	@Test
	void parseBoard_froMString() {
		Board board = basicBoard();

		Figure[] white_figures = new Figure[5];
		Figure[] black_figures = new Figure[5];

		for(int i = 0; i < 5;i++) {
			white_figures[i] = board.getFigure_at(0, i);
			black_figures[i] = board.getFigure_at(1, i);
		}
		
		for(Figure a: white_figures) {
			if(!a.isWhite()) {
				fail("white Figure is not white");
			}
		}
		for(Figure a: black_figures) {
			if(a.isWhite()) {
				fail("black Figure is white");
			}
		}
		
		
		assertTrue(white_figures[0] instanceof Bauer);
		assertTrue(white_figures[1] instanceof Laeufer);
		assertTrue(white_figures[2] instanceof Springer);
		assertTrue(white_figures[3] instanceof Dame);
		assertTrue(white_figures[4] instanceof Turm);
		
		assertTrue(black_figures[0] instanceof Bauer);
		assertTrue(black_figures[1] instanceof Laeufer);
		assertTrue(black_figures[2] instanceof Springer);
		assertTrue(black_figures[3] instanceof Dame);
		assertTrue(black_figures[4] instanceof Turm);
	}
	
	
}
