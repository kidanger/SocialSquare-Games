package core.states;

import javax.swing.*;

import core.IGame;
import core.IState;
import core.Lobby;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuration implements IState {

	public Configuration(IGame game) {
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void onEnter(Lobby l) {
		
		JPanel terminal = l.getDisplay().terminal;
		
		JLabel numberOfGamers = new JLabel("Nombre de joueurs");
		numberOfGamers.setBounds(100, 150, 200, 100);
		terminal.add(numberOfGamers);
		
		String[] selectedNumber = {"1","2"};
		JComboBox<String> list = new JComboBox<String>(selectedNumber);
		list.setSelectedIndex(1);
		list.setBounds(300,  150,  200,  100);
		list.setBackground(Color.red);
		terminal.add(list);		
		int selected = Integer.parseInt(list.getSelectedItem().toString());
		System.out.println(selected);
		list.setVisible(true);
		
		JButton validate =  new JButton("Commencer le jeu !");
		validate.setBounds(200,  250,  200, 20);
		terminal.add(validate);

		validate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new EndOfGame()); ///// A modifier : Running
			}
		});
		
		terminal.repaint();
	}
}
