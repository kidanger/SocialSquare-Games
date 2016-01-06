package core;

import java.awt.Graphics;

public abstract class Game {

	protected String[] players;
	protected int currentPlayer;
	protected int[] scores;

	public void start(String players[]) {
		this.players = players;
		this.scores = new int[players.length];
	}

	public abstract String getName();
	public abstract String getIconPath();

	public abstract void update(double dt);
	public abstract void drawOnTerminal(Graphics g);
	public abstract void drawOnBoard(Graphics g);

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
}