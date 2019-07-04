package ai;

import java.util.ArrayList;
import java.util.List;

import abstractFigure.Figure.*;
import abstractFigure.*;
import board.*;
import positionAndMove.*;

public class AI {

	public static ValueAndMove_Container calc(Board b, int maxDepth,int currentDepth, double betterThanThisWontBeTaken) {
		Team t = b.getWhosTurn();
		List<Move> children = BoardUtils.getMoves_byTeam(b, t);
		if(children.size() > 0 && maxDepth > currentDepth) {
			ValueAndMove_Container marker = new ValueAndMove_Container(Integer.MIN_VALUE);
			double currentMax = Integer.MIN_VALUE;
			currentDepth++;
			for(Move m: children) {
				BoardUtils.changeBoard(b, m);
				ValueAndMove_Container w=  calc( b,  maxDepth, currentDepth,-1* currentMax);
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
				return new ValueAndMove_Container(Integer.MIN_VALUE);
			} else {
				return new ValueAndMove_Container(calcValue(b));
			}
		}
		
	}
	
	public static double calcValue(Board b) {
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
