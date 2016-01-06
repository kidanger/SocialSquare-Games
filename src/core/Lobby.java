package core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.states.Home;
import core.states.Running;
import games.*;

public class Lobby {

	private Display display = new Display();
	private IState state;
	private List<Game> games = new ArrayList<Game>();

	Lobby() {
		display.initialize(this);
		games.add(new Memory(this));
		games.add(new HopScotch());
		games.add(new ConnectFour());
		games.add(new RunAfterTheLight());
		games.add(new Dance());
		games.add(new TicTacToe());
	}

	public void setState(IState state) {
		if (this.state != null) {
			this.state.onExit(this);
		}
		display.terminal.removeAll();
		this.state = state;
		this.state.onEnter(this);
		display.terminal.repaint();
	}

	public Display getDisplay() {
		return display;
	}

	public List<Game> getGameList() {
		return games;
	}
	
	static public void main(String args[]) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Morningtype.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		Lobby l = new Lobby();
		l.setState(new Home());
	}

	public void drawOnBoard(Graphics g) {
		state.drawOnBoard(g);
	}

	public void drawOnTerminal(Graphics g) {
		state.drawOnTerminal(g);
	}
}
