package core.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import core.Game;
import core.IState;
import core.Lobby;
import core.ServerUtils;

public class GameList implements IState {

	public void onEnter(final Lobby l) {
		ServerUtils.updateTerminal(l.getID(), true, null);
		JPanel terminal = l.getDisplay().terminal;

		Font font = new Font("Morningtype", Font.PLAIN, 20);
		int pos[][] = {{67, 125}, {255, 125}, {443, 125}, {67, 302}, {255, 302}, {443, 302}};
		int i = 0;
		
		JButton back = new JButton();
		back.setBounds(30, 20, 70, 70);
		back.setBorderPainted(false);
		back.setIcon(new ImageIcon("icons/retour.png"));
		terminal.add(back);
		
		JLabel labelLogo = new JLabel();
		labelLogo.setIcon(new ImageIcon("icons/logoMiniature.png"));
		labelLogo.setBounds(245, 10, 150, 89);
		terminal.add(labelLogo);
		
		for (final Game game : l.getGameList()) {
			JButton button = new JButton();
			button.setIcon(new ImageIcon(game.getIconPath()));
			button.setBorderPainted(false);
			button.setBounds(pos[i][0], pos[i][1], 128, 128);
			button.setContentAreaFilled(false);
			button.setOpaque(false);
			terminal.add(button);

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					l.playSound("clic.wav");
					l.setState(new Configuration(game));
				}
			});
			button.setEnabled(i == 0);

			JLabel label = new JLabel(game.getName(), SwingConstants.CENTER);
			label.setFont(font);
			label.setBounds(pos[i][0], pos[i][1] + 76, 128, 128);
			terminal.add(label);

			i++;
		}
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.playSound("clic.wav");
				l.setState(new Home());
			}
		});
		terminal.repaint();
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
