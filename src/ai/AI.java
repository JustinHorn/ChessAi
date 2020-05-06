package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import abstractFigure.Figure.*;
import abstractFigure.*;
import board.*;
import positionAndMove.*;

public class AI {

	public static int count = 0;
	
	public static ValueAndMoveContainer calc(Board b, int maxDepth,int currentDepth, double betterThanThisWontBeTaken) {
		Team t = b.getWhosTurn();
		List<Move> children = BoardUtils.getMoves_byTeam(b, t);
		if(children.size() > 0 && maxDepth > currentDepth) {
			//children.sort(new CompareMoves());
			ValueAndMoveContainer marker = new ValueAndMoveContainer(Integer.MIN_VALUE);
			marker.addMove(children.get(0));
			double currentMax = Integer.MIN_VALUE;
			currentDepth++;
			for(Move m: children) {
				count++;
				BoardUtils.changeBoard(b, m);
				ValueAndMoveContainer w=  calc( b,  maxDepth, currentDepth,-1* currentMax);
				BoardUtils.reverseBoard(b, m);
				w.invertValue();
				w.addMove(m);
				if(currentMax < w.getValue()) {
					currentMax = w.getValue();
					marker = w;
				}
				if(betterThanThisWontBeTaken <= w.getValue()) {
					return w;
				}
			}
			currentDepth--;
			return marker;
		} else {
			if(BoardUtils.is_checkMate(b,t)) {
				return new ValueAndMoveContainer(Integer.MIN_VALUE);
			} else {
				return new ValueAndMoveContainer(calcValue(b));
			}
		}
		
	}
	
	public static void setCount() {
		count = 0;
	}
	
	public static int getCount() {
		return count;
	}
	
	public static double calcValue(Board b) {
		long time = System.currentTimeMillis();
		List<Figure> figures =BoardUtils.find_figures(b, b.getWhosTurn());
		List<Figure> otherFigures =BoardUtils.find_figures(b, b.getNextTeam(b.getWhosTurn()));

		int v = 0;
		List<Move> m = new ArrayList<Move>();
		for(Figure f: figures) {
			v+=f.getValue();
		}
		m.addAll(BoardUtils.getMoves_byTeam(b,  b.getWhosTurn()));

		v+= m.size()/figures.size();
		m = new ArrayList<Move>();
		for(Figure f: otherFigures) {
		
			v-=f.getValue();
		}
		m.addAll(BoardUtils.getMoves_byTeam(b,  b.getNextTeam(b.getWhosTurn())));
		v-= m.size()/otherFigures.size();
		
		return  v;
	}
	

	
}

final class CompareMoves implements Comparator<Move> {

	@Override
	public int compare(Move m1, Move m2) {
		if(m1.getDefeatedFigure().getValue() < m2.getDefeatedFigure().getValue()) {
			return +1;
		}
		if(m1.getDefeatedFigure().getValue() > m2.getDefeatedFigure().getValue()) {
			return -1;
		}
		return 0;
	}
	
}
