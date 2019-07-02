package abstractFigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import board.Board;
import figureTypes.EmptyField;

public class Test_GetAccessableFigures {
	
	private Board basicBoard() {
		char[][] b = new char[8][8];
		b[0] = "-------K".toCharArray();
		b[1] = "--L-----".toCharArray();
		b[2] = "-d---T--".toCharArray();
		b[3] = "---Sb---".toCharArray();
		b[4] = "--------".toCharArray();
		b[5] = "--b-----".toCharArray();
		b[6] = "--------".toCharArray();
		b[7] = "k-------".toCharArray();
		return new Board(b);
	}
	
	@Test
	void test_white_Koenig() {
		Board b = basicBoard();
		Figure white_koenig = b.getFigure_at(0,7);
		List<Figure> figures = white_koenig.getAccessableFigures();
		
		assertEquals(3,figures.size());
		assertTrue(figures.stream().allMatch(f -> f instanceof EmptyField));	
	}
	
	@Test
	void test_black_Dame() {
		Board b = basicBoard();
		Figure black_dame = b.getFigure_at(2, 1);
		Figure white_turm = b.getFigure_at(2, 5);
		Figure white_lauefer = b.getFigure_at(1,2);
		Figure white_springer = b.getFigure_at(3, 3);
		
		List<Figure> figures = black_dame.getAccessableFigures();
		
		assertTrue(figures.contains(white_turm));
		assertTrue(figures.contains(white_lauefer));
		assertFalse(figures.contains(white_springer));
		assertFalse(figures.contains(black_dame));

	}
	
	void test_white_turm() {
		Board b = basicBoard();

		Figure white_turm = b.getFigure_at(2, 5);
		Figure black_dame = b.getFigure_at(2, 1);
		Figure black_bauer = b.getFigure_at(3, 4);
		
		List<Figure> figures = white_turm.getAccessableFigures();

		assertTrue(figures.contains(black_dame));
		assertFalse(figures.contains(black_bauer));
		assertFalse(figures.contains(white_turm));

	}
	
	@Test
	void test_white_lauefer() {
		Board b = basicBoard();
		Figure white_lauefer = b.getFigure_at(1,2);
		Figure black_dame = b.getFigure_at(2, 1);

		Figure black_bauer = b.getFigure_at(3, 4);

		List<Figure> figures = white_lauefer.getAccessableFigures();

		assertTrue(figures.contains(black_dame));
		assertTrue(figures.contains(black_bauer));

		assertFalse(figures.contains(white_lauefer));

	}
	
	@Test
	void test_white_springer() {
		Board b = basicBoard();
		Figure white_springer = b.getFigure_at(3, 3);

		Figure white_lauefer = b.getFigure_at(1,2);
		Figure black_dame = b.getFigure_at(2, 1);
		Figure black_bauer = b.getFigure_at(5, 2);
		Figure white_turm = b.getFigure_at(2, 5);
		
		
		List<Figure> figures = white_springer.getAccessableFigures();

		
		assertTrue(figures.contains(black_dame));
		assertTrue(figures.contains(black_bauer));
		assertTrue(figures.contains(white_lauefer));
		assertTrue(figures.contains(white_turm));

		assertFalse(figures.contains(white_springer));
	}
	
	@Test
	void test_black_bauer_nextTo_white_springer() {
		Board b = basicBoard();

		
		Figure black_bauer =  b.getFigure_at(3, 4);
		Figure white_springer = b.getFigure_at(3, 3);

		Figure emptyfield_infront = b.getFigure_at(2, 4);
		Figure emptyfield_left_front = b.getFigure_at(2, 3);

		Figure white_turm = b.getFigure_at(2, 5);


		List<Figure> figures = black_bauer.getAccessableFigures();

		assertTrue(figures.contains(emptyfield_infront));
		assertTrue(figures.contains(white_turm));

		assertFalse(figures.contains(emptyfield_left_front));
		assertFalse(figures.contains(black_bauer));
		assertFalse(figures.contains(white_springer));

	}
	
}
