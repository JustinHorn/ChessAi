package Graphics;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Graphics.JChessPanel.Appearance;
import abstractFigure.Figure;
import board.Board;
import board.BoardUtils;
import figureWithIn.EmptyField;
import positionAndMove.Move;
import positionAndMove.Position;

public class ChessGame extends JFrame {

	private JChessPanel[][] fields = new JChessPanel[8][8];
	private Board b;
	private List<Move> moves;

	public static void main(String... args) {
		new ChessGame();

	}

	public ChessGame() {
		super("Chess");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		b = new Board();
		setBounds(0, 0, 500, 500);
		setLayout(new GridLayout(8, 8));
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				fields[i][j] = new JChessPanel(new Position(i, j), b.getFigure_at(i, j).firstChar());
				fields[i][j].addMouseListener(getMouseListener_forPanel(i, j));
				add(fields[i][j]);
			}
		}
		setVisible(true);
		setEnabled(true);
	}

	public MouseListener getMouseListener_forPanel(int i, int j) {
		return new MouseListener() {
			Position myP = new Position(i, j);
			JChessPanel me = fields[i][j];

			@Override
			public void mouseClicked(MouseEvent e) {
				ifClicked();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ifClicked();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				ifClicked();
			}

			private void ifClicked() {
				Figure f = b.getFigure_at(myP);
				
				deactivate_orActivate_ifNotEmptyField(me,f);
				
				if (isAFieldActive_and_amI_not_active_nor_passiv(me)) {
					makeMove(myP);
				}
				repaint();
			}

		};
	}
	
	private void deactivate_orActivate_ifNotEmptyField(JChessPanel me, Figure f) {
		if (amIActivated(me)) {
			deactivateMe(me, f);
		} else if(amI_notAnEmptyField_passive_andThereIs_noFieldActivated(me, f)) {
			activateMe(me, f);
		}
	}

	private boolean isAFieldActive_and_amI_not_active_nor_passiv(JChessPanel me) {
		return isThereAnActivedField()
				&& !(me.getAppearance() == Appearance.ACTIVATED || me.getAppearance() == Appearance.PASSIV);
	}

	private boolean amI_notAnEmptyField_passive_andThereIs_noFieldActivated(JChessPanel me, Figure f) {
		return !(f instanceof EmptyField) && me.getAppearance() == Appearance.PASSIV && !isThereAnActivedField();
	}

	private boolean amIActivated(JChessPanel me) {
		return me.getAppearance() == Appearance.ACTIVATED;
	}
	
	private void activateMe(JChessPanel me, Figure f) {
		activateFields(f);
		me.setAppearance(Appearance.ACTIVATED);
	}

	private void deactivateMe(JChessPanel me, Figure f) {
		deactivateFields(f);
		me.setAppearance(Appearance.PASSIV);
	}

	private void activateFields(Figure f) {
		moves = f.getMoves();
		for (Move m : moves) {
			Position to = m.toPosition();
			if (m.getDefeatedFigure() instanceof EmptyField) {
				fields[to.getRow()][to.getCol()].setAppearance(Appearance.ACTIVE_EMPTY);
			} else {
				fields[to.getRow()][to.getCol()].setAppearance(Appearance.ACTIVE_HOSTILE);
			}
		}
	}


	private void deactivateFields(Figure f) {
		for (Move m : moves) {
			Position to = m.toPosition();
			fields[to.getRow()][to.getCol()].setAppearance(Appearance.PASSIV);
		}
	}

	private void makeMove(Position me) {
		Move m = getMoveWithPositionTo(me);
		BoardUtils.changeBoard(b, m);
		makePanelsPassiv_and_updateFigure();
	}

	private Move getMoveWithPositionTo(Position to) {
		for (Move m : moves) {
			if (m.toPosition().equals(to)) {
				return m;
			}
		}
		throw new IllegalArgumentException("Position not in moves: " + to);
	}

	private boolean isThereAnActivedField() {
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (fields[i][j].getAppearance() == Appearance.ACTIVATED) {
					return true;
				}
			}
		}
		return false;
	}

	private void makePanelsPassiv_and_updateFigure() {
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				fields[i][j].setChar(b.getFigure_at(i, j).firstChar());
				fields[i][j].setAppearance(Appearance.PASSIV);
			}
		}
		repaint();
	}

}
