package games;

import core.IGame;

public class TicTacToe implements IGame {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIconPath() {
		return "icons/ticTacToe.png";
	}

	@Override
	public int getNumberOfGamersMin() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getNumberOfGamersMax() {
		// TODO Auto-generated method stub
		return 2;
	}

}
