package core.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.IGame;
import core.IState;
import core.Lobby;

public class GameList implements IState {
	/**
	 * @wbp.parser.entryPoint
	 */
	public void onEnter(Lobby l) {
		/**
		 * @wbp.parser.entryPoint
		 */
		JPanel terminal = l.getDisplay().terminal;
		
		int pos[][] = {{67, 125}, {255, 125}, {443, 125}, {67, 302}, {255, 302}, {443, 302}};
		int i = 0;
		for (IGame game : l.getGameList()) {
			JButton button = new JButton();
			button.setIcon(new ImageIcon(game.getIconPath()));
			button.setBorderPainted(false);
			button.setBounds(pos[i][0], pos[i][1], 128, 128);
			terminal.add(button);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					l.setState(new Configuration(game));
				}
			});
			i++;
		}
		
		terminal.repaint();
	}
}
