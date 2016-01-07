package core.states;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.IState;
import core.Lobby;
import core.ServerUtils;

public class Home implements IState {

	public Home (){
	}

	public void onEnter(final Lobby l) {
		JPanel terminal = l.getDisplay().terminal;
		
		JButton selectGame = new JButton("Sélectionner un jeu");
		selectGame.setFont(new Font("Morningtype", Font.PLAIN, 20));
		ServerUtils.updateTerminal(l.getID(), false, null);
		selectGame.setBounds(150, 380, 340, 80);
		terminal.add(selectGame);
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("icons/logo.png"));
		label.setBounds(10, 10, 600, 356);
		terminal.add(label);
		
		selectGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.playSound("clic.wav");
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
