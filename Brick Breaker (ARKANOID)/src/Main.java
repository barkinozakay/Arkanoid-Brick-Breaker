import javax.swing.JFrame;

public class Main {
	public static final int WIDTH = 1080, HEIGHT = 720;

	public static void main(String[] args) {

		JFrame theFrame = new JFrame("ARKANOID");

		GamePanel thePanel = new GamePanel();

		theFrame.setLocation(500, 200);
		theFrame.setResizable(false);
		theFrame.setSize(WIDTH, HEIGHT);

		theFrame.add(thePanel);
		
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		thePanel.playGame();

	}
}
