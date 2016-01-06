package core.states;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.IState;
import core.Lobby;
import core.ServerUtils;

public class Home implements IState {

	public JButton selectGame = new JButton("Sélectionner un jeu");

	public Home (){
	}

	public void onEnter(final Lobby l) {
		ServerUtils.updateTerminal(l.getID(), false, null);
		JPanel terminal = l.getDisplay().terminal;
		selectGame.setBounds(150, 180, 340, 80);
		terminal.add(selectGame);
		selectGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new GameList());
			}
		});
	}

	@Override
	public void onExit(Lobby l) {
	}

	@Override
	public void update(Lobby lobby, double dt) {
		lobby.getDisplay().idle(dt);
	}

	@Override
	public void drawOnTerminal(Graphics g) {
	}

	@Override
	public void drawOnBoard(Graphics g) {
	}
}
