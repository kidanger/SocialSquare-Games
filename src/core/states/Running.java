package core.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import core.Game;
import core.IState;
import core.Lobby;

public class Running implements IState {

	private Game game;
	private JLabel currentPlayer = new JLabel("", SwingConstants.CENTER);
	private List<JLabel> scores = new ArrayList<JLabel>();

	public Running(Game game) {
		this.game = game;
	}

	@Override
	public void onEnter(final Lobby l) {
		final JPanel terminal = l.getDisplay().terminal;
		
		JButton back = new JButton();
		back.setBounds(30, 20, 70, 70);
		back.setIcon(new ImageIcon("icons/retour.png"));
		back.setBorderPainted(false);
		terminal.add(back);
		
		final JPanel board = l.getDisplay().board;
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new Configuration(game));
			}
		});
		
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

		currentPlayer.setBounds(640/2 + 10, 80 + 20, 640/2 - 20, 100);
		currentPlayer.setFont(new Font("Morningtype", Font.BOLD, 26));
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			JLabel label = new JLabel();
			label.setBounds(640/2 + 30, 160 + 40 * i, 320, 100);
			label.setFont(new Font("Morningtype", Font.PLAIN, 20));
			scores.add(label);
		}

		terminal.add(currentPlayer);
		for (JLabel label : scores) {
			terminal.add(label);
		}
	}

	@Override
	public void onExit(Lobby l) {
	}

	public void update(Lobby lobby, double dt) {
		game.update(dt);

		currentPlayer.setText("Joueur courant : " + game.getCurrentPlayerName());
		for (int i = 0; i < scores.size(); i++) {
			JLabel label = scores.get(i);
			label.setText(game.getPlayerName(i) + " : " + game.getPlayerScore(i) + " points");
		}
	}

	@Override
	public void drawOnTerminal(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(640 / 2, 100, 640 / 2, 480 - 10);
		g.drawLine(10, 100, 640 - 10, 100);
		game.drawOnTerminal(g);
	}

	@Override
	public void drawOnBoard(Graphics g) {
		game.drawOnBoard(g);
	}
}
