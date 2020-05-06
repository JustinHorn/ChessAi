package abstractFigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import board.*;
import figureWithIn.Laeufer;
import positionAndMove.Move;


public class TestFigureUtils {

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
	void test_accessableFigures_toMoves() {
		Board b = basicBoard();
		Figure white_laeufer = b.getFigure_at(1,2);
		if(!(white_laeufer instanceof Laeufer)) {
			fail("wrong Figure");
		}
		List<Figure> accessableFigures = white_laeufer.getAccessableFigures();
		List<Move> moves = FigureUtils.movesInRange(white_laeufer);
		
		assertEquals(moves.size(),accessableFigures.size());
		
	}
	

}
