package games;

import core.IGame;

public class Memory implements IGame {

	@Override
	public String getName() {
		return "Memory";
	}

	@Override
	public String getIconPath() {
		return "icons/memory.png";
	}

	@Override
	public int getNumberOfGamersMin() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getNumberOfGamersMax() {
		// TODO Auto-generated method stub
		return 4;
	}

}
