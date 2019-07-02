package board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import abstractFigure.*;
import figureWithIn.Bauer;
import figureWithIn.Dame;
import figureWithIn.EmptyField;
import figureWithIn.Koenig;
import figureWithIn.Laeufer;
import figureWithIn.Springer;
import figureWithIn.Turm;
import positionAndMove.Move;
import positionAndMove.MoveTyp;
import positionAndMove.Position;

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
		
		b.makeChange(turm2_left);
		b.reverseChange(turm2_left);
		
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

	private Board makeChange_board( ) {
		char[][] charB = new char[8][8];
		charB[0] = "T--K----".toCharArray();
		charB[1] = "--B-----".toCharArray();
		charB[2] = "--------".toCharArray();
		charB[3] = "-b------".toCharArray();
		charB[4] = "--------".toCharArray();
		charB[5] = "--------".toCharArray();
		charB[6] = "B-------".toCharArray();
		charB[7] = "---k---t".toCharArray();
		
		return new Board(charB);
		
	}


	@Test
	void test_makeChange_Rochade_white() {
		Board b = makeChange_board();
		
		Koenig white_koenig = (Koenig) b.getFigure_at(0,3);
		Turm white_turm = (Turm) b.getFigure_at(0, 0);
		Move rochade = new Move(white_koenig,new EmptyField(),new Position(0,3),new Position(0,1),MoveTyp.Rochade,'K');
		b.makeChange(rochade);
		
		assertEquals(white_koenig,b.getFigure_at(0,1));
		assertEquals(white_turm,b.getFigure_at(0,2));
	}
	
	@Test
	void test_makeChange_Rochade_black() {
		Board b = makeChange_board();
		
		Koenig black_koenig = (Koenig) b.getFigure_at(7,3);
		Turm black_turm = (Turm) b.getFigure_at(7, 7);
		Move rochade = new Move(black_koenig,new EmptyField(),new Position(7,3),new Position(7,5),MoveTyp.Rochade,'D');
		b.makeChange(rochade);
		
		assertEquals(black_koenig,b.getFigure_at(7,5));
		assertEquals(black_turm,b.getFigure_at(7,4));
	}
	
	@Test
	void test_makeChange_EnPassant() {
		Board b = makeChange_board();
		
		Bauer white_bauer = (Bauer) b.getFigure_at(1,2);
		Bauer black_bauer = (Bauer) b.getFigure_at(3, 1);
		Move twice = new Move(white_bauer,new EmptyField(),new Position(1,2),new Position(3,2),MoveTyp.Twice,'-');
		b.makeChange(twice);
		
		assertEquals(white_bauer,b.getFigure_at(3,2));
		
		Move enPassant = new Move(black_bauer,white_bauer,new Position(3,1),new Position(2,2),MoveTyp.EnPassant,'-');
		b.makeChange(enPassant);

		assertEquals(black_bauer,b.getFigure_at(2,2));
		assertTrue(b.getFigure_at(3,2) instanceof EmptyField);
	}
	

	@Test
	void test_makeChange_BauerToWhatEver() {
		Board b = makeChange_board();
		
		Bauer white_bauer_atTheEnd = (Bauer) b.getFigure_at(6,0);
		Move bauerTo = new Move(white_bauer_atTheEnd,new EmptyField(),new Position(6,0),new Position(7,0),MoveTyp.BauerTo,'D');
		b.makeChange(bauerTo);
		
		assertTrue(b.getFigure_at(7,0) instanceof Dame);
	}
	



	@Test
	void test_reverseChange_Rochade() {
		Board b = makeChange_board();
		
		Koenig white_koenig = (Koenig) b.getFigure_at(0,3);
		Turm white_turm = (Turm) b.getFigure_at(0, 0);
		Move rochade = new Move(white_koenig,new EmptyField(),new Position(0,3),new Position(0,1),MoveTyp.Rochade,'K');
		b.makeChange(rochade);
		
		b.reverseChange(rochade);
		assertEquals(white_koenig,b.getFigure_at(0,3));
		assertEquals(white_turm,b.getFigure_at(0,0));
	}
	
	@Test
	void test_reverseChange_EnPassant() {
		Board b = makeChange_board();
		
		Bauer white_bauer = (Bauer) b.getFigure_at(1,2);
		Bauer black_bauer = (Bauer) b.getFigure_at(3, 1);
		Move twice = new Move(white_bauer,new EmptyField(),new Position(1,2),new Position(3,2),MoveTyp.Twice,'-');
		b.makeChange(twice);
		Move enPassant = new Move(black_bauer,white_bauer,new Position(3,1),new Position(2,2),MoveTyp.EnPassant,'-');
		
		b.makeChange(enPassant);
		b.reverseChange(enPassant);

		assertEquals(black_bauer,b.getFigure_at(3,1));
		assertTrue(b.getFigure_at(2,2) instanceof EmptyField);
		assertEquals(white_bauer, b.getFigure_at(3,2));

	}
	

	@Test
	void test_reverseChange_BauerToWhatEver() {
		Board b = makeChange_board();
		
		Bauer white_bauer_atTheEnd = (Bauer) b.getFigure_at(6,0);
		Move bauerTo = new Move(white_bauer_atTheEnd,new EmptyField(),new Position(6,0),new Position(7,0),MoveTyp.BauerTo,'D');
		b.makeChange(bauerTo);
		b.reverseChange(bauerTo);

		assertTrue(b.getFigure_at(7,0) instanceof EmptyField);
		assertEquals(white_bauer_atTheEnd,b.getFigure_at(6,0));
	}
	
	
	
}
