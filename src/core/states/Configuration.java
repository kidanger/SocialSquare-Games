package core.states;

import javax.swing.*;

import core.IGame;
import core.IState;
import core.Lobby;

public class Configuration implements IState {

	public Configuration(IGame game) {
	}

	public void onEnter(Lobby l) {
		JPanel terminal = l.getDisplay().terminal;
		JLabel numberOfGamers = new JLabel("Nombre de joueurs");
		numberOfGamers.setBounds(100, 150, 200, 100);
		terminal.add(numberOfGamers);
		
		String [] numbers = {"1","2"};
		JList selectedNumber = new JList(numbers);
		terminal.add(selectedNumber);
		terminal.repaint();
		//textField.setFont(textField.getFont().deriveFont(50f));
	}
}
