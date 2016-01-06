package core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display {
	//public JPanel board = new JPanel();
	public JPanel terminal = new JPanel();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		//JFrame boardFrame = new JFrame();
		JFrame terminalFrame = new JFrame();
		//boardFrame.setSize(640, 640);
		terminalFrame.setSize(640, 480);

		//board.setLayout(null);
		terminal.setLayout(null);
		
		//boardFrame.setContentPane(board);
		terminalFrame.setContentPane(terminal);
		// terminal.add(new JLabel(new ImageIcon("/Users/Anta/Documents/SocialSquare-Games/presentation/image3.JPG")));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//boardFrame.setVisible(true);
				terminalFrame.setVisible(true);
			}
		});
	}
}
