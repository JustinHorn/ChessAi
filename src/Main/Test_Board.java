package Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

		assertEquals("Koenig", white_king.getName());
		assertEquals(true, white_king.isWhite());

		assertEquals("Koenig", black_king.getName());
		assertEquals(false, black_king.isWhite());

	}

	@Test
	void test_getFigureById() {
		Board board = basicBoard();
		Figure white_Bauer = board.getFigure_at(0, 0);
		Figure same_white_Bauer = board.getFigure_at(board.getPosition_of_FigureWithId(white_Bauer.getId()));

		assertEquals(white_Bauer, same_white_Bauer);
	}

	@Test
	void changeBoard() {
		Board board = basicBoard();

		board.changeBoard(new Move(board, new Position(0, 7), new Position(7, 0)));

		Figure nothing = board.getFigure_at(0, 7);
		Figure white_king = board.getFigure_at(7, 0);

		assertEquals("Koenig", white_king.getName());
		assertTrue(white_king.isWhite());

		assertTrue(nothing instanceof EmptyField);

	}

	@Test
	void parseBoard_froMString() {
		Board board = basicBoard();

		Figure[] white_figures = new Figure[5];
		Figure[] black_figures = new Figure[5];

		for (int i = 0; i < 5; i++) {
			white_figures[i] = board.getFigure_at(0, i);
			black_figures[i] = board.getFigure_at(1, i);
		}

		for (Figure a : white_figures) {
			if (!a.isWhite()) {
				fail("white Figure is not white");
			}
		}
		for (Figure a : black_figures) {
			if (a.isWhite()) {
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

	@Test 
	void testEquals() {
		Board b1 = basicBoard();
		Board b2 = basicBoard();
		Board b3 = secondKind();
		
		assertTrue(b1.equals(b2));
		assertFalse(b1.equals(b3));

	}
	
	
	private Board secondKind() {
		char[][] b = new char[8][8];
		b[0] = "--T----K".toCharArray();
		b[1] = "---T----".toCharArray();
		b[2] = "--------".toCharArray();
		b[3] = "--------".toCharArray();
		b[4] = "--------".toCharArray();
		b[5] = "--------".toCharArray();
		b[6] = "--------".toCharArray();
		b[7] = "k-------".toCharArray();
		
		return new Board(b);
	}
	
	
	
	@Test
	void test_isMoveLegal() {
		Board board = secondKind();
		
		Move turm2_left = new Move(board,new Position(1, 3), new Position(1,1));
		assertTrue(board.isMoveLegal(turm2_left));
		
	}
	
	@Test
	void test_reverseBoard() {
		Board b = secondKind();
		Move turm2_left = new Move(b,new Position(1, 3), new Position(1,1));
		
		b.changeBoard(turm2_left);
		b.reverseBoard(turm2_left);
		
		Board b2 = secondKind();
		assertTrue(b2.equals(b));
		
	}
	
	@Test
	void test_makeMove() {
		Board board = secondKind();
		
		Move turm2_left = new Move(board,new Position(1, 3), new Position(1,1));
		Move blackKoenig_up = new Move(board,new Position(7, 0), new Position(6,0));
		Move turm1_schachMatt = new Move(board,new Position(0,2), new Position(0,0));
		
		board.makeMove(turm2_left);
		board.makeMove(blackKoenig_up);
				
		board.makeMove(turm1_schachMatt);		
		
		assertTrue(BoardUtils.is_blackKoenig_checkMate(board));
	}
	
	@Test
	void test_detectIllegalMove() {
		Board board = secondKind();
		
		Move turm2_left = new Move(board,new Position(1, 3), new Position(1,1));
		Move blackKoenig_up = new Move(board,new Position(7, 0), new Position(6,0));
		Move turm1_onturm2 = new Move(board,new Position(0,2), new Position(1,1));
		
		board.makeMove(turm2_left);
		board.makeMove(blackKoenig_up);
		try {
			board.makeMove(turm1_onturm2);
			fail("Board did not detect false move");
		}catch (Exception e) {}
	}

}
