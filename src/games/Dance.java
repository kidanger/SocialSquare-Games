package games;

import java.awt.Graphics;

import core.Game;

public class Dance extends Game {

	@Override
	public void start(String[] players) {
	}

	@Override
	public String getName() {
		return "Danse!";
	}

	@Override
	public String getIconPath() {
		return "icons/dance.png";
	}

	@Override
	public void update(double dt) {
	}

	@Override
	public void drawOnTerminal(Graphics g) {
	}

	@Override
	public void drawOnBoard(Graphics g) {
	}

	@Override
	public void onPlayerMove(int x, int y) {
	}

	@Override
	public void onPlayerClick(int x, int y) {
	}

	public int getNumberOfPlayersMin() {
		return 1;
	}

	@Override
	public int getNumberOfPlayersMax() {
		return 20;
	}

}
