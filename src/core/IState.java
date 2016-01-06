package core;

import java.awt.Graphics;

public interface IState {

	void onEnter(Lobby l);
	void onExit(Lobby l);

	void update(Lobby lobby, double dt);
	void drawOnTerminal(Graphics g);
	void drawOnBoard(Graphics g);
}
