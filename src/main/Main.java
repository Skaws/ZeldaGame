package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		// make the window closeable when the x button is pressed
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make it so we can't resize the window. fixed resolution
		window.setResizable(false);
		// window title (top left corner)
		window.setTitle("2D Adventure");

		GamePanel gamePanel = new GamePanel();
		// add the gamepanel to the window
		window.add(gamePanel);
		//make the window's size and layout fit that of its subcomponents (in this case gamePanel as it just got added)
		window.pack();

		
		// Display window at center of screen (by not specifying the window location, thus it takes default)
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
