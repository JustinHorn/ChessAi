package board;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import abstractFigure.Figure;
import positionAndMove.Move;
import positionAndMove.MoveTyp;
import positionAndMove.Position;

public class Test_specialMoves {
	private Board getBasic_specialMove_Board() {
		char[][] b = new char[8][8];
		b[0] = "T--K----".toCharArray();
		b[1] = "---B----".toCharArray();
		b[2] = "--------".toCharArray();
		b[3] = "--b-----".toCharArray();
		b[4] = "--------".toCharArray();
		b[5] = "--------".toCharArray();
		b[6] = "B-------".toCharArray();
		b[7] = "---k---t".toCharArray();
		return new Board(b);
	}
	
	@Test
	void test_getSpecialMoves_forMe_white_king() {
		Board b = getBasic_specialMove_Board();
		Figure white_koenig = b.getFigure_at(0, 3);
		
		List<Move> sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, white_koenig);
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.Rochade,sM.get(0).getType());
		assertEquals('K',sM.get(0).getTypeModifier());
	}
	
	@Test
	void test_getSpecialMoves_forMe_black_king() {
		Board b = getBasic_specialMove_Board();
		Figure black_koenig = b.getFigure_at(7, 3);
		
		List<Move> sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, black_koenig);
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.Rochade,sM.get(0).getType());
		assertEquals('D',sM.get(0).getTypeModifier());
	}
	
	@Test
	void test_getSpecialMoves_forMe_white_Bauer() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer = b.getFigure_at(1, 3);
		
		List<Move> sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, white_bauer);
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.Twice,sM.get(0).getType());
		assertEquals('-',sM.get(0).getTypeModifier());
		Position expected = new Position(3, 3);
		assertEquals(expected,sM.get(0).toPosition());
	}
	
	@Test
	void test_getSpecialMoves_forMe_black_bauer() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer = b.getFigure_at(1, 3);
		
		List<Move> sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, white_bauer);
		b.makeChange(sM.get(0));
		
		Figure black_bauer = b.getFigure_at(3,2);
		sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, black_bauer);
		
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.EnPassant,sM.get(0).getType());
		assertEquals('-',sM.get(0).getTypeModifier());
		Position expected = new Position(2, 3);
		assertEquals(expected,sM.get(0).toPosition());
	}
	
	@Test
	void test_getSpecialMoves_forMe_white_bauer_near_end() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer_near_end = b.getFigure_at(6, 0);
		
		List<Move> sM = GetSpecialMoves_FromBoard.getMySpecialMoves(b, white_bauer_near_end);		
		
		assertEquals(4,sM.size());
		assertEquals(MoveTyp.BauerTo,sM.get(0).getType());
		assertEquals('D',sM.get(0).getTypeModifier());
		Position expected = new Position(7, 0);
		assertEquals(expected,sM.get(0).toPosition());
	}
}
