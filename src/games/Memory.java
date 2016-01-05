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

}
