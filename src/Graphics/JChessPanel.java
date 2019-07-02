package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import abstractFigure.Figure;
import figureWithIn.EmptyField;
import positionAndMove.*;

public class JChessPanel extends JPanel{
	
	private Position p;
	private char f;
	private Appearance myApp = Appearance.PASSIV;
	
	public enum Appearance {
		PASSIV,ACTIVATED, ACTIVE_EMPTY, ACTIVE_FRIENDLY,ACTIVE_HOSTILE
	}
	
	public JChessPanel(Position p,char f) {
		this.p = p;
		this.f = f;
	}
	
	public char getChar() {
		return f;
	}
	
	public void setChar(char fi) {
		f = fi;
	}
	
	public void setAppearance(Appearance ap) {
		myApp = ap;
	}
	
	public Appearance getAppearance() {
		return myApp;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(5));
	    
	    int w = this.getWidth();
	    int h =this.getHeight();
	    if((p.getRow() +p.getCol()) %2 == 0) {
	    	setBackground(Color.BLACK);
	    } else {
	    	setBackground(Color.WHITE);
	    }
	    
	    switch(myApp) {
	    	case ACTIVE_EMPTY: g2.setColor(Color.BLUE);
	    	break;
	    	case ACTIVE_FRIENDLY: g2.setColor(Color.GREEN);
	    	break;
	    	case ACTIVE_HOSTILE: g2.setColor(Color.RED);
	    	break;
	    	case PASSIV: g2.setColor(getBackground());
	    	break;
	    	case ACTIVATED: g2.setColor(Color.YELLOW);
	    	break;
	    }
	    g2.drawRect(w/10, h/10, w*9/10, h*9/10); 
	    
	    if(f != '-') {
	    	g2.setColor(Color.RED);
	    	addaptFontSize(f+"");
	    	g2.drawString(f+"", w/4, h*7/8);
	    }
	}
	
	private void addaptFontSize(String text) {
		  Font labelFont = this.getFont();

		    int stringWidth = this.getFontMetrics(labelFont).stringWidth(text);
		    int componentWidth = this.getWidth();

		    double widthRatio = (double)componentWidth / (double)stringWidth;

		    int newFontSize = (int)(labelFont.getSize() * widthRatio);

		    int fontSizeToUse = Math.min(newFontSize, getHeight());
		    this.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}
}

