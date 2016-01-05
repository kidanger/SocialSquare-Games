package core;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display {
	private Lobby lobby;

	public JPanel board = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			lobby.drawOnBoard(g);
		}
	};
	public JPanel terminal = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			lobby.drawOnTerminal(g);
		}
	};

	public void initialize(final Lobby lobby) {
		this.lobby = lobby;
		final JFrame boardFrame = new JFrame();
		final JFrame terminalFrame = new JFrame();

		boardFrame.setSize(640, 640);
		terminalFrame.setSize(640, 480);

		board.setLayout(null);
		terminal.setLayout(null);

		boardFrame.setContentPane(board);
		terminalFrame.setContentPane(terminal);

		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardFrame.setLocationRelativeTo(null);
		terminalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		terminalFrame.setLocationRelativeTo(null);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boardFrame.setVisible(true);
				terminalFrame.setVisible(true);
			}
		});
	}

}
