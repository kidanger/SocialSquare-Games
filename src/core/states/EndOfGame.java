package core.states;
import java.awt.Graphics;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Game;
import core.IState;
import core.Lobby;

public class EndOfGame implements IState {

	private Game game;

	public EndOfGame(Game game) {
		this.game = game;
	}

	public void onEnter(final Lobby l) {
		JPanel terminal = l.getDisplay().terminal;
		JLabel congrats = new JLabel(" Joueur 1 a gagné, bravo :)");
		congrats.setBounds(100, 100, 200, 25);
		terminal.add(congrats);

		JButton newGame =  new JButton("Commencer une nouvelle partie");
		newGame.setBounds(200,  250,  300, 60);
		terminal.add(newGame);

		JButton backToMenu =  new JButton("Retour au menu");
		backToMenu.setBounds(50,  400,  200, 40);
		terminal.add(backToMenu);

		JLabel scores = new JLabel("<html>Scores : <br>\nJoueur 1 : 10 <br>\nJoueur 2 : 8</html>");
		scores.setBounds(100, 150, 200, 100);
		terminal.add(scores);

		backToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new Home());
			}
		});
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new Configuration(game));
			}
		});
		terminal.repaint();
	}

	@Override
	public void onExit(Lobby l) {
	}

	@Override
	public void drawOnTerminal(Graphics g) {
	}

	@Override
	public void drawOnBoard(Graphics g) {
	}
}


