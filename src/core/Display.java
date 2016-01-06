package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Display {

	private Random random = new Random();
	private Lobby lobby;
	private boolean idling;
	private Timer timer;
	private double[][] cells = new double[10][10];
	private float[][] hue = new float[10][10];

	public JPanel board = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			super.paintComponent(g);
			lobby.drawOnBoard(g);
			if (idling)
				drawIdle(g);
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

		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				idling = false;
				lobby.update(0.1);
				terminal.repaint();
				board.repaint();
			}
		});
		timer.start();

		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			cells[x][y] += random.nextDouble();
			hue[x][y] = random.nextFloat();
		}
	}

	public void idle(double dt) {
		idling = true;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (cells[x][y] > 0) {
					cells[x][y] += dt / 3;
				}
				if (cells[x][y] >= 1) {
					cells[x][y] = 0;
					int xx = random.nextInt(10);
					int yy = random.nextInt(10);
					while (cells[xx][yy] > 0) {
						xx = random.nextInt(10);
						yy = random.nextInt(10);
					}
					cells[xx][yy] = 0.0001;
					hue[xx][yy] = random.nextFloat();
				}
			}
		}
	}

	public void drawIdle(Graphics g) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Color c;
				if (cells[x][y] > 0) {
					c = Color.getHSBColor(hue[x][y], 0.8f, (float) Math.sin(cells[x][y]*Math.PI));
				} else {
					c = Color.black;
				}
				g.setColor(c);
				g.fillRect(x*64, y*64, 64, 64);
			}
		}
	}
}
