import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HUD {

	// Fields
	private int score;

	// Constructor
	public HUD() {
		initialize();
	}

	public void initialize() {
		score = 0;
	}

	public void draw(Graphics2D g) {
		
		g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		g.setColor(Color.GREEN);
		g.drawString("Score: " + score, 20, 20);
		
	}

	public int getScore() {
		return score;
	}

	public void addScore(int scoreToAdd) {
		score += scoreToAdd;
	}
}
