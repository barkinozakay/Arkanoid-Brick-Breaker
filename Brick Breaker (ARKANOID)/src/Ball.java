import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Ball {

	// Fields
	private double x, y, dx, dy; // Speed of the ball. (Up-Down & Side to side)
	private int ballSize = 16;
	private Image ballImage = new ImageIcon(this.getClass().getResource("mainBall.png")).getImage();

	public static int ballDamage = 1;

	private boolean deadBallOn;
	private long deadBallTimer;

	// Constructor
	public Ball() {

		x = Main.WIDTH / 2 - Paddle.x / 2; // Starting position in x-axis.
		y = 350; // Starting position in y-axis.
		dx = 2; // How fast the ball in x-direction.
		dy = 4; // How fast the ball in y-direction.

		deadBallOn = false;

	}

	public void update() {

		setPosition();
		if ((System.nanoTime() - deadBallTimer) / 1000 > 12000000) {
			deadBallOn = false;
			setMainBallImage();
			setBallDamage(1);

		}
	}

	public void setPosition() {

		x += dx;
		y += dy;

		if (x < 0 || x > Main.WIDTH - ballSize) // Change the ball's position if it hits a wall on left wall or right
												// wall .
			dx = -dx;
		if (y < 0 || y > Main.HEIGHT - ballSize) // Change the ball's position if it hits a wall on top wall or bottom
													// wall .
			dy = -dy;
	}

	public void draw(Graphics2D g) {

		g.drawImage(ballImage, (int) x, (int) y, ballSize, ballSize, null);
		if (deadBallOn) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 25));
			g.drawString("Energy: " + (12 - ((System.nanoTime() - deadBallTimer) / 1000000000)), 750, 30);
		}

	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, ballSize, ballSize);
	}

	public boolean loseCheck() {

		boolean loser = false;

		if (y > Main.HEIGHT - ballSize * 2) {
			loser = true;
		}

		return loser;
	}

	public void setDY(double theDY) {
		dy = theDY;
	}

	public double getDY() {
		return dy;
	}

	public void setDX(double theDX) {
		dx = theDX;
	}

	public double getDX() {
		return dx;
	}

	public double getX() {
		return x;
	}

	public void setBallDamage(int theDamage) {
		ballDamage = theDamage;
		if (theDamage == 0)  // Set a timer for DEAD BALL.
			deadBallOn = true;
		setDeadBallTimer();		// Fire-ball has limited time!
	}

	public int getBallDamage() {
		return ballDamage;
	}

	public void setMainBallImage() {
		ballImage = new ImageIcon(this.getClass().getResource("mainBall.png")).getImage();
	}

	public void setFireBallImage() {
		ballImage = new ImageIcon(this.getClass().getResource("fireBall.png")).getImage();
	}

	public void setDeadBallImage() {
		ballImage = new ImageIcon(this.getClass().getResource("deadBall.png")).getImage();
	}

	public void setDeadBallTimer() {
		deadBallTimer = System.nanoTime();
	}

	public long getDeadBallTimer() {
		return System.nanoTime();
	}

}
