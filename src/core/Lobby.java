package core;

import java.util.ArrayList;
import java.util.List;

import core.states.Home;
import games.*;

public class Lobby {

	private Display display = new Display();
	private IState state;
	private List<IGame> games = new ArrayList<IGame>();

	Lobby() {
		display.initialize();
		games.add(new Memory());
		games.add(new HopScotch());
		games.add(new ConnectFour());
		games.add(new RunAfterTheLight());
		games.add(new Dance());
		games.add(new TicTacToe());
	}

	public void setState(IState state) {
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
		l.setState(new Home());
	}
}
