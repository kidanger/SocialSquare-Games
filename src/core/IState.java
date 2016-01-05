package core;

import java.awt.Graphics;

public interface IState {

	void onEnter(Lobby l);
	void onExit(Lobby l);

	void drawOnTerminal(Graphics g);
	void drawOnBoard(Graphics g);
}
