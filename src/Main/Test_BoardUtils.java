package Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import Figures.*;

public class Test_BoardUtils {
	
	private Board getBasicBoard() {
		char[][] b = new char[8][8];
		b[0] = "-----dbK".toCharArray();
		b[1] = "--L---b-".toCharArray();
		b[2] = "-d---Tb-".toCharArray();
		b[3] = "---Sb-b-".toCharArray();
		b[4] = "------b-".toCharArray();
		b[5] = "--b---b-".toCharArray();
		b[6] = "------b-".toCharArray();
		b[7] = "k------t".toCharArray();
		return new Board(b);
	}
	
	@Test
	void test_find_King() {
		Board b = getBasicBoard();
		Figure white_koenig = BoardUtils.find_whiteKoenig(b);
		assertEquals(b.getFigure_at(0, 7),white_koenig);	
	}
	
	@Test
	void test_getWhiteFigures() {
		Board b = getBasicBoard();
		List<Figure> white_figures = BoardUtils.getWhiteFigures(b);
		
		Figure white_koenig = b.getFigure_at(0, 7);
		Figure white_laeufer = b.getFigure_at(1, 2);
		Figure white_turm = b.getFigure_at(2, 5);
		Figure white_springer = b.getFigure_at(3, 3);
		
		List<Figure> real_white_figures = new ArrayList();
		real_white_figures.add(white_springer);
		real_white_figures.add(white_turm);
		real_white_figures.add(white_laeufer);
		real_white_figures.add(white_koenig);
		

		failIf_listAreDifferent(white_figures,real_white_figures);

	}
	
	@Test
	void test_getWhiteMoves() {
		Board b = getBasicBoard();
		List<Figure> white_figures = BoardUtils.getWhiteFigures(b);
		List<Move> real_whitemoves = new ArrayList<Move>();
		white_figures.forEach(f -> real_whitemoves.addAll(f.getMoves()));
		
		List<Move> white_moves = BoardUtils.getWhiteMoves(b);
		failIf_listAreDifferent(white_moves,real_whitemoves);
		
	}
	
	@Test
	void white_threats_at_position() {
		Board b = getBasicBoard();
		Position p = new Position(2, 1);
		
		List<Figure> white_threats = BoardUtils.threads_byWhite_atPosition(b,p);
		
		Figure white_laeufer = b.getFigure_at(1, 2);
		Figure white_turm = b.getFigure_at(2, 5);
		Figure white_springer = b.getFigure_at(3, 3);
		
		List<Figure> real_white_threats = new ArrayList();
		real_white_threats.add(white_springer);
		real_white_threats.add(white_turm);
		real_white_threats.add(white_laeufer);
		
		failIf_listAreDifferent(real_white_threats,white_threats);
	}
	
	private void failIf_listAreDifferent(List one,List two) {
		if(one.stream().anyMatch(f  -> !two.contains(f))) {
			fail("Lists are different");
		}
		if(two.stream().anyMatch(f  -> !one.contains(f))) {
			fail("Lists are different");
		}
	}
	
	@Test
	void is_whiteKoenig_check() {
		Board b = getBasicBoard();
		
		boolean isCheck = BoardUtils.is_whiteKoenig_check(b);
		assertTrue(isCheck);
	}
	
	@Test
	void is_whiteKoenig_checkMate() {
		Board b = getBasicBoard();
		
		boolean isCheckMate = BoardUtils.is_whiteKoenig_checkMate(b);
		assertTrue(isCheckMate);
	}
	
	@Test
	void test_isMoveLegal() {
		Board b = getBasicBoard();
		Move moveLaeufer = new Move(b,new Position(1,2),new Position(0,1));
		assertFalse(BoardUtils.is_ownKingThreathend_afterMove(b,moveLaeufer));
	}
	
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
		
		List<Move> sM = BoardUtils.getSpecialMoves_forMe(b, white_koenig);
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.Rochade,sM.get(0).getType());
		assertEquals('K',sM.get(0).getFigure());
	}
	
	@Test
	void test_getSpecialMoves_forMe_white_Bauer() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer = b.getFigure_at(1, 3);
		
		List<Move> sM = BoardUtils.getSpecialMoves_forMe(b, white_bauer);
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.Twice,sM.get(0).getType());
		assertEquals('-',sM.get(0).getFigure());
		Position expected = new Position(3, 3);
		assertEquals(expected,sM.get(0).toPosition());
	}
	
	@Test
	void test_getSpecialMoves_forMe_black_bauer() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer = b.getFigure_at(1, 3);
		
		List<Move> sM = BoardUtils.getSpecialMoves_forMe(b, white_bauer);
		b.makeChange(sM.get(0));
		
		Figure black_bauer = b.getFigure_at(3,2);
		sM = BoardUtils.getSpecialMoves_forMe(b, black_bauer);
		
		assertEquals(1,sM.size());
		assertEquals(MoveTyp.EnPassant,sM.get(0).getType());
		assertEquals('-',sM.get(0).getFigure());
		Position expected = new Position(2, 3);
		assertEquals(expected,sM.get(0).toPosition());
	}
	
	@Test
	void test_getSpecialMoves_forMe_white_bauer_near_end() {
		Board b = getBasic_specialMove_Board();
		Figure white_bauer_near_end = b.getFigure_at(6, 0);
		
		List<Move> sM = BoardUtils.getSpecialMoves_forMe(b, white_bauer_near_end);		
		
		assertEquals(4,sM.size());
		assertEquals(MoveTyp.BauerTo,sM.get(0).getType());
		assertEquals('D',sM.get(0).getFigure());
		Position expected = new Position(7, 0);
		assertEquals(expected,sM.get(0).toPosition());
	}
}
