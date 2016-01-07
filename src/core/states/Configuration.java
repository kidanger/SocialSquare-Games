package core.states;

import java.awt.Graphics;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import core.Game;
import core.IState;
import core.Lobby;
import core.Score;
import core.ServerUtils;

import java.awt.Color;
import java.awt.Font;
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
		numberOfPlayers.setBounds(300, 75, 200, 100);
		numberOfPlayers.setFont(new Font("Morningtype", Font.BOLD, 26));
		terminal.add(numberOfPlayers);

		JLabel ranking = new JLabel("Classement");
		ranking.setBounds(50, 75, 200, 100);
		ranking.setFont(new Font("Morningtype", Font.BOLD, 26));
		terminal.add(ranking);

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
					l.playSound("clic.wav");
					terminal.repaint();
				}
			}
		}

		final JButton validate = new JButton("Commencer le jeu !");
		validate.setFont(new Font("Morningtype", Font.PLAIN, 20));
		validate.setBounds(300,  400,  200, 40);
		terminal.add(validate);
		validate.setEnabled(false);

		ButtonGroup group = new ButtonGroup();
		int idx = 0;
		for (int i = game.getNumberOfPlayersMin(); i <= game.getNumberOfPlayersMax(); i++) {
			JRadioButton button = new JRadioButton(Integer.toString(i));
			button.setOpaque(false);
			button.setFont(new Font("Morningtype", Font.PLAIN, 20));
			button.setBounds(300 + 55 * idx, 175, 50, 30);
			terminal.add(button);
			group.add(button);
			button.addActionListener(new StateListener(i));
			buttons.add(button);
			idx++;
		}
		for (idx = 0; idx <= game.getNumberOfPlayersMax(); idx++) {
			JLabel label = new JLabel("Joueur " + (idx + 1));
			label.setFont(new Font("Morningtype", Font.PLAIN, 20));
			label.setBounds(350, 230 + idx * 40, 200, 30);
			terminal.add(label);
			label.setVisible(false);
			labels.add(label);

			JTextField field = new JTextField("");
			field.setBounds(450, 230 + idx * 40, 150, 30);
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
		JLabel labelLogo = new JLabel();
		labelLogo.setIcon(new ImageIcon("icons/logoMiniature.png"));
		labelLogo.setBounds(245, 10, 150, 89);
		terminal.add(labelLogo);
		
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
				l.playSound("clic.wav");
				l.setState(new Running(game));
			}
		});
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.playSound("clic.wav");
				l.setState(new GameList());
			}
		});

		List<Score> scores = ServerUtils.getScoresOfGame(game.getName());
		Font font = new Font("Morningtype", Font.PLAIN, 22);
		int i = 0;
		for (Score score : scores) {
			Color color = Color.black;
			if (i == 0) {
				color = new Color(217, 204, 18);
			} else if (i == 1) {
				color = new Color(195, 184, 182);
			} else if (i == 2) {
				color = new Color(203, 113, 0);
			}

			JLabel playerLabel = new JLabel(score.username);
			playerLabel.setBounds(50, 160 + 30 * i, 100, 25);
			playerLabel.setFont(font);
			playerLabel.setForeground(color);
			terminal.add(playerLabel);

			JLabel scoreLabel = new JLabel(String.valueOf(score.score));
			scoreLabel.setBounds(160, 160 + 30 * i, 100, 25);
			scoreLabel.setFont(font);
			playerLabel.setForeground(color);
			terminal.add(scoreLabel);
			i++;

			if (i == 10)
				break;
		}

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
