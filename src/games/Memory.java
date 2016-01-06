package games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import core.Game;
import core.Lobby;
import core.states.EndOfGame;

class Cell {
	int identifier;
	boolean shown;
}

public class Memory extends Game {

	final static private double LOCK_POSITION_TIMER_DURATION = 2. /* seconds */;
	final static private double RETURNING_TIMER_DURATION = 1. /* seconds */;

	private Lobby lobby;
	private Cell[][] cells = new Cell[4][4];
	private Cell returned1;
	private Cell returned2;
	private int currentPositionX;
	private int currentPositionY;
	private double lockPositionTimer;
	private double returningTimer;
	private Color[] playersColor = {
			new Color(0, 69, 137),
			new Color(75, 149, 0),
	};
	private BufferedImage images[] = new BufferedImage[8];

	public Memory(Lobby lobby) {
		this.lobby = lobby;
		for (int i = 0; i < 8; i++) {
			try {
				String filename = "icons/memory/" + (i + 1) + ".png";
				images[i] = ImageIO.read(new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void start(String[] players) {
		super.start(players);
		returned1 = null;
		returned2 = null;
		lockPositionTimer = 0;
		currentPositionX = currentPositionY = 0;
		returningTimer = 0;
		randomizeCells();
	}

	private void randomizeCells() {
		List<Integer> positions = new ArrayList<Integer>(4*4);
		for (int i = 0; i < 4*4; i++) {
			positions.add(i);
		}
		Collections.shuffle(positions);

		for (int i = 0; i < 4*4; i++) {
			int x = positions.get(i) / 4;
			int y = positions.get(i) % 4;

			Cell c = new Cell();
			c.identifier = i / 2;
			c.shown = false;
			cells[x][y] = c;
		}
	}

	@Override
	public String getName() {
		return "Memory";
	}

	@Override
	public String getIconPath() {
		return "icons/memory.png";
	}

	@Override
	public void update(double dt) {
		if (returningTimer != 0) {
			returningTimer -= dt;
			if (returningTimer <= 0) {
				returningTimer = 0;

				if (returned1 != null && returned2 != null) {
					if (returned1.identifier != returned2.identifier) {
						returned1.shown = false;
						returned2.shown = false;
						currentPlayer = currentPlayer * -1 + 1;
					} else {
						scores[currentPlayer] += 1;
					}
					returned1 = null;
					returned2 = null;
					returningTimer = RETURNING_TIMER_DURATION;
					checkEndOfGame();
				}
			}
		} else if (lockPositionTimer != 0) {
			lockPositionTimer -= dt;
			if (lockPositionTimer <= 0) {
				lockPositionTimer = 0;
				playAt(currentPositionX, currentPositionY);
			}
		}
	}

	private void checkEndOfGame() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Cell c = cells[x][y];
				if (!c.shown)
					return;
			}
		}
		lobby.setState(new EndOfGame(this));
	}

	@Override
	public void drawOnTerminal(Graphics g) {
		Font font = new Font("Morningtype", Font.PLAIN, 30);
		g.setFont(font);
		g.setColor(Color.black);
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				g.drawString("Memory", 120+dx, 130+dy);
			}
		}
		g.setColor(playersColor[currentPlayer]);
		g.drawString("Memory", 120, 130);

		g.setColor(playersColor[currentPlayer]);
		g.fillRect(0, 160, 320, 380);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Cell c = cells[x][y];
				if (c.shown) {
					g.setColor(Color.getHSBColor(c.identifier / 8.f, 1, 1));
				} else {
					g.setColor(Color.gray.darker());
				}
				g.fill3DRect((x + 3) * 32, 160 + (y + 3) * 32, 32, 32, true);
				if (c.shown) {
					g.drawImage(images[c.identifier], (x + 3) * 32, 160 + (y + 3) * 32, 32, 32, null);
				}
				if (!c.shown && x == currentPositionX && y == currentPositionY) {
					Color color = new Color(.8f, .8f, .8f, (float) (lockPositionTimer / LOCK_POSITION_TIMER_DURATION));
					g.setColor(color);
					g.fillRect((x + 3) * 32, 160 + (y + 3) * 32, 32, 32);
				}
			}
		}
	}

	@Override
	public void drawOnBoard(Graphics g) {
		g.setColor(playersColor[currentPlayer]);
		g.fillRect(0, 0, 640, 640);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Cell c = cells[x][y];
				if (c.shown) {
					g.setColor(Color.getHSBColor(c.identifier / 8.f, 1, 1));
				} else {
					g.setColor(Color.gray.darker());
				}
				g.fill3DRect((x + 3) * 64, (y + 3) * 64, 64, 64, true);
				if (c.shown) {
					g.drawImage(images[c.identifier], (x + 3) * 64, (y + 3) * 64, 64, 64, null);
				}
				if (!c.shown && x == currentPositionX && y == currentPositionY) {
					Color color = new Color(.8f, .8f, .8f, (float) (lockPositionTimer / LOCK_POSITION_TIMER_DURATION));
					g.setColor(color);
					g.fillRect((x + 3) * 64, (y + 3) * 64, 64, 64);
				}
			}
		}
	}

	@Override
	public void onPlayerMove(int x, int y) {
		x -= 3;
		y -= 3;
		if (x >= 0 && x < 4 && y >= 0 && y < 4) {
			if (currentPositionX != x || currentPositionY != y) {
				currentPositionX = x;
				currentPositionY = y;
				lockPositionTimer = LOCK_POSITION_TIMER_DURATION;
			}
		} else {
			lockPositionTimer = 0;
			currentPositionX = -1;
			currentPositionY = -1;
		}
	}

	@Override
	public void onPlayerClick(int x, int y) {
		//playAt(x, y);
	}

	private void playAt(int x, int y) {
		if (returningTimer > 0)
			return;
		if (cells[x][y].shown)
			return;

		if (returned1 == null) {
			returned1 = cells[x][y];
			returned1.shown = true;
			returningTimer = RETURNING_TIMER_DURATION;
		} else {
			returned2 = cells[x][y];
			returned2.shown = true;
			returningTimer = RETURNING_TIMER_DURATION;
		}
	}

	@Override
	public int getNumberOfPlayersMin() {
		return 1;
	}

	@Override
	public int getNumberOfPlayersMax() {
		return 4;
	}

}
