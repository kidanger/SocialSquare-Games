package core.states;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.Timer;

import core.IGame;
import core.IState;
import core.Lobby;

public class Running implements IState {

	private IGame game;
	private Timer timer;

	public Running(IGame game) {
		this.game = game;
	}

	@Override
	public void onEnter(final Lobby l) {
		final JPanel terminal = l.getDisplay().terminal;
		final JPanel board = l.getDisplay().board;
		board.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				game.onPlayerMove(e.getX() / 64, e.getY() / 64);
			}
		});
		terminal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game.onPlayerClick(e.getX(), e.getY());
			}
		});

		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				game.update(0.1);
				terminal.repaint();
				board.repaint();
			}
		});
		timer.start();
	}

	@Override
	public void onExit(Lobby l) {
		timer.stop();
	}

	@Override
	public void drawOnTerminal(Graphics g) {
		game.drawOnTerminal(g);
	}

	@Override
	public void drawOnBoard(Graphics g) {
		game.drawOnBoard(g);
	}
}
