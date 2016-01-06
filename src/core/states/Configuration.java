package core.states;

import java.awt.Graphics;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import core.Game;
import core.IState;
import core.Lobby;
import core.ServerUtils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements IState {
	private final Game game;
	
	public Configuration(Game game) {
		this.game = game;	
	}

	public void onEnter(final Lobby l) {
		ServerUtils.updateTerminal(l.getID(), true, game.getName());
		final JPanel terminal = l.getDisplay().terminal;
		
		JLabel numberOfPlayers = new JLabel("Nombre de joueurs");
		numberOfPlayers.setBounds(100, 150, 200, 100);
		terminal.add(numberOfPlayers);

		final List<JLabel> labels = new ArrayList<JLabel>();
		final List<JTextField> fields = new ArrayList<JTextField>();
		final List<JRadioButton> buttons = new ArrayList<JRadioButton>();

		class StateListener implements ActionListener {
			private int nb;

			public StateListener(int nb) {
				this.nb = nb;
			}

			public void actionPerformed(ActionEvent e) {
				if (buttons.get(nb - game.getNumberOfPlayersMin()).isSelected()) {
					for (int i = 0; i < labels.size(); i++) {
						labels.get(i).setVisible(i < nb);
						fields.get(i).setVisible(i < nb);
					}
					terminal.repaint();
				}
			}
		}

		final JButton validate = new JButton("Commencer le jeu !");
		validate.setBounds(300,  400,  200, 40);
		terminal.add(validate);
		validate.setEnabled(false);

		ButtonGroup group = new ButtonGroup();
		int idx = 0;
		for (int i = game.getNumberOfPlayersMin(); i <= game.getNumberOfPlayersMax(); i++) {
			JRadioButton button = new JRadioButton(Integer.toString(i));
			button.setBounds(300 + 55 * idx, 175, 50, 30);
			terminal.add(button);
			group.add(button);
			button.addActionListener(new StateListener(i));
			buttons.add(button);
			idx++;
		}
		for (idx = 0; idx <= game.getNumberOfPlayersMax(); idx++) {
			JLabel label = new JLabel("Joueur " + (idx + 1));
			label.setBounds(300, 230 + idx * 40, 200, 30);
			terminal.add(label);
			label.setVisible(false);
			labels.add(label);

			JTextField field = new JTextField("");
			field.setBounds(400, 230 + idx * 40, 150, 30);
			terminal.add(field);
			field.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent documentEvent) {
					check();
				}

				@Override
				public void removeUpdate(DocumentEvent documentEvent) {
					check();
				}

				@Override
				public void changedUpdate(DocumentEvent documentEvent) {
					check();
				}

				private void check() {
					boolean ok = true;
					for (JTextField field : fields) {
						if (field.getText().isEmpty() && field.isVisible())
							ok = false;
					}
					validate.setEnabled(ok);
				}
			});
			field.setVisible(false);
			fields.add(field);
		}
		
		JButton back = new JButton();
		back.setBounds(30, 20,  70, 70);
		back.setIcon(new ImageIcon("icons/retour.png"));
		back.setBorderPainted(false);
		terminal.add(back);

		validate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int number = 0;
				for (JTextField field : fields) {
					if (field.isVisible())
						number++;
				}
				String[] players = new String[number];
				for (int i = 0; i < players.length; i++) {
					players[i] = fields.get(i).getText();
				}
				game.start(players);
				l.setState(new Running(game));
				//l.setState(new EndOfGame(game));
			}
		});
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setState(new GameList());
			}
		});
		
		terminal.repaint();
	}

	@Override
	public void update(Lobby lobby, double dt) {
		lobby.getDisplay().idle(dt);
	}

	@Override
	public void onExit(Lobby l) {
	}

	public void drawOnTerminal(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(240, 100, 240, 480 - 10);
		g.drawLine(10, 100, 640 - 10, 100);
	}

	@Override
	public void drawOnBoard(Graphics g) {
	}
}
