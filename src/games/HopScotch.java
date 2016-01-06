package games;

import java.awt.Graphics;

import core.Game;

public class HopScotch extends Game {

	@Override
	public void start(String[] players) {
	}

	@Override
	public String getName() {
		return "Marelle";
	}

	@Override
	public String getIconPath() {
		return "icons/marelle.png";
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

	@Override
	public int getNumberOfPlayersMin() {
		return 1;
	}

	@Override
	public int getNumberOfPlayersMax() {
		return 1;
	}

}
