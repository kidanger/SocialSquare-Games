package core.states;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Game;
import core.IState;
import core.Lobby;
import core.Score;
import core.ServerUtils;

public class EndOfGame implements IState {

	private Game game;

	public EndOfGame(Game game) {
		this.game = game;
	}

	public void onEnter(final Lobby l) {
		int winner = game.getWinner();
		JPanel terminal = l.getDisplay().terminal;
		List<Score> oldScores = ServerUtils.getScoresOfGame(game.getName());

		String congratsText;
		if (winner != -1) {
			congratsText = "Bravo " + game.getPlayerName(winner) +", vous avez gagné !";
		} else {
			congratsText = "Égalité";
		}
		
		JLabel congrats = new JLabel(congratsText);
		congrats.setFont(new Font("Morningtype", Font.BOLD, 34));
		congrats.setBounds(100, 100, 500, 60);
		terminal.add(congrats);

		JButton newGame =  new JButton("Commencer une nouvelle partie");
		newGame.setFont(new Font("Morningtype", Font.BOLD, 15));
		newGame.setBounds(200,  300,  300, 60);
		terminal.add(newGame);

		JButton backToMenu =  new JButton("Retour à l'accueil");
		backToMenu.setBounds(50,  400,  200, 40);
		terminal.add(backToMenu);
		
		String scoreText = "<html>Scores <br> ";
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			boolean betterScore = true;
			String username = game.getPlayerName(i);
			if (oldScores != null) {
				for (Score score : oldScores) {
					if (score.username.equals(username)) {
						if (game.getPlayerScore(i) <= score.score) {
							betterScore = false;
						}
					}
				}
			}
			scoreText += " &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; " + username
					+ " : " + game.getPlayerScore(i) ;
			if (betterScore) {
				scoreText += " (nouveau record !)";
			}
			scoreText += "<br>";
		}
		scoreText += "</html>";
		
		JLabel scores = new JLabel(scoreText);
		scores.setFont(new Font("Morningtype", Font.BOLD, 20));
		scores.setBounds(100, 150, 600, 100);
		terminal.add(scores);

		backToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new Home());
				l.playSound("memory/clic.wav");
			}
		});
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new Configuration(game));
				l.playSound("memory/clic.wav");
			}
		});
		terminal.repaint();

		int scoreMax = 0;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (game.getPlayerScore(i) > scoreMax) {
				scoreMax = game.getPlayerScore(i);
			}
		}
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (game.getPlayerScore(i) == scoreMax) {
				ServerUtils.postScore(game.getName(), game.getPlayerName(i), scoreMax);
			}
		}
	}

	@Override
	public void onExit(Lobby l) {
	}

	@Override
	public void update(Lobby lobby, double dt) {
		lobby.getDisplay().idle(dt);
	}

	@Override
	public void drawOnTerminal(Graphics g) {
	}

	@Override
	public void drawOnBoard(Graphics g) {
	}
}


