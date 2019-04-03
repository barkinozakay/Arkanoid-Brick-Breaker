import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Paddle extends JLabel {

	// Fields
	public static double x; // Position of the paddle
	private int width = 150, height = 20; // Width and Height of the paddle.
	private int startWidth;
	private long widthTimer;
	private int dp = 35; // Speed of the paddle.

	public final int YPOS = Main.HEIGHT - 100; // Paddle fixed in y-direction.

	private Image paddleImage = null;

	// paddleImage = new
	// ImageIcon(this.getClass().getResource("mainPaddle.png")).getImage();

	/*
	 * Take the image and resize it to an icon. Image resizedImage =
	 * paddleImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
	 * ImageIcon normalPaddle = new ImageIcon(resizedImage);;
	 */

	// Constructor
	public Paddle() {

		paddleImage = new ImageIcon(this.getClass().getResource("mainPaddle.png")).getImage();
		initialize();

	}

	public void initialize() {

		startWidth = width;

		x = Main.WIDTH / 2 - width / 2; // Middle of the screen.

	}

	// Update
	public void update() {

	}

	// Draw
	public void draw(Graphics2D g) {

		g.drawImage(paddleImage, (int) x, YPOS, width, height, null);

	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, YPOS, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int newWidth) {
		width = newWidth;
	}

	public void setWidthTimer() {
		widthTimer = System.nanoTime();
	}

	public long getWidthTimer() {
		return System.nanoTime();
	}

	public void mouseMoved(int mouseXPos) {
		x = mouseXPos;

		if (x > Main.WIDTH - width) { // Limit paddle's movement on the right hand side.
			x = Main.WIDTH - width;
		}
	}

	public void move(boolean right, boolean left) {
		if (right) {
			x += dp;
		}
		if (left) {
			x -= dp;
		}
		if (x <= 0) {
			x = 0;
		}
		if (x >= Main.WIDTH - width) {
			x = Main.WIDTH - width;
		}
	}
}
