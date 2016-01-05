package core;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.states.Home;
import core.states.Running;
import games.*;

public class Lobby {

	private Display display = new Display();
	private IState state;
	private List<IGame> games = new ArrayList<IGame>();

	Lobby() {
		display.initialize(this);
		games.add(new Memory());
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
	}

	public Display getDisplay() {
		return display;
	}
	
	public List<IGame> getGameList() {
		return games;
	}
	
	static public void main(String args[]) {
		Lobby l = new Lobby();
		//l.setState(new Home());
		l.setState(new Running(l.games.get(0)));
		l.games.get(0).start(new String[]{"titi", "toto"});
	}

	public void drawOnBoard(Graphics g) {
		state.drawOnBoard(g);
	}

	public void drawOnTerminal(Graphics g) {
		state.drawOnTerminal(g);
	}
}
