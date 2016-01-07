package core;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Game {

	protected String[] players;
	protected int currentPlayer;
	protected int[] scores;
	private Color[] colors = {
			new Color(0, 69, 137),
			new Color(75, 149, 0),
			new Color(149, 52, 0),
			new Color(149, 141, 0),
	};

	public void start(String players[]) {
		this.players = players;
		this.scores = new int[players.length];
	}

	public abstract String getName();
	public abstract String getIconPath();

	public abstract void update(double dt);
	public abstract void drawOnTerminal(Graphics g);
	public abstract void drawOnBoard(Graphics g);

	public abstract int getNumberOfPlayersMin();
	public abstract int getNumberOfPlayersMax();

	public abstract void onPlayerMove(int x, int y);
	public abstract void onPlayerClick(int x, int y);

	public String getCurrentPlayerName() {
		return players[currentPlayer];
	}

	public String getPlayerName(int i) {
		return players[i];
	}

	public int getPlayerScore(int i) {
		return scores[i];
	}

	public int getNumberOfPlayers() {
		return players.length;
	}

	public Color getPlayerColor(int i) {
		return colors[i];
	}

	public int getWinner(){
		int maxScore = 0;
		int nb = 0;
		int winner = -1;

		for (int score : scores) {
			maxScore = Math.max(score, maxScore);
		}
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] == maxScore) {
				nb++;
				winner = i;
			}
		}
		return nb == 1 ? winner : -1;
	}
}