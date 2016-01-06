package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display {

	private Lobby lobby;
	public JPanel board = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			super.paintComponent(g);
			lobby.drawOnBoard(g);
			g.setColor(Color.black);
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					g.drawLine(x * 64, 0, x * 64, 640);
					g.drawLine(0, y * 64, 640, y * 64);
				}
			}
		}
	};
	public JPanel terminal = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			super.paintComponent(g);
			lobby.drawOnTerminal(g);
		}
	};

	public void initialize(final Lobby lobby) {
		this.lobby = lobby;
		final JFrame boardFrame = new JFrame();
		final JFrame terminalFrame = new JFrame();

		board.setPreferredSize(new Dimension(640, 640));
		terminal.setPreferredSize(new Dimension(640, 480));

		board.setLayout(null);
		terminal.setLayout(null);

		boardFrame.setContentPane(board);
		terminalFrame.setContentPane(terminal);

		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardFrame.setLocationRelativeTo(null);
		boardFrame.pack();
		terminalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		terminalFrame.setLocationRelativeTo(null);
		terminalFrame.pack();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boardFrame.setVisible(true);
				terminalFrame.setVisible(true);
			}
		});
	}

}
