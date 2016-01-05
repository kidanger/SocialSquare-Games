package core;

import java.awt.Graphics;

public interface IGame {

	void start(String players[]);
	String getName();
	String getIconPath();

	void update(double dt);
	void drawOnTerminal(Graphics g);
	void drawOnBoard(Graphics g);

	void onPlayerMove(int x, int y);
	void onPlayerClick(int x, int y);
}
