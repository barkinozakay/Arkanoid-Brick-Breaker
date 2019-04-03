import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PowerUp {
	// Fields
	private int x, y, dy, type, width, height;
	private boolean isOnScreen;
	private boolean wasUsed;
	private Color color;
	public final static int WIDEPADDLE = 4;
	public final static int NARROWPADDLE = 5;
	public final static int FASTBALL = 6;
	public final static int SLOWBALL = 7;
	public final static int FIREBALL = 8;
	public final static int DEADBALL = 9;

	public final static Color WIDECOLOR = Color.GREEN;
	public final static Color FASTCOLOR = Color.RED;

	Image powerUpImage = null;

	// Constructor
	public PowerUp(int xStart, int yStart, int theType, int theWidth, int theHeight) {

		x = xStart;
		y = yStart;
		type = theType;
		width = theWidth;
		height = theHeight;

		loadPowerUpImage(type);

		/*
		 * if(type < 4) { type = 4; } else if (type > 5) { type = 5; }
		 */

		/*
		 * if(type == WIDEPADDLE) { color = WIDECOLOR; } else if (type == FASTBALL) {
		 * color = FASTCOLOR; }
		 */

		dy = (int) (Math.random() * 6 + 1); // Power-up random fall speed.

		wasUsed = false;
	}

	public void loadPowerUpImage(int type) {
		if (type == WIDEPADDLE) {
			powerUpImage = new ImageIcon(this.getClass().getResource("paddleExpandPowerUp.png")).getImage();
		}
		if (type == NARROWPADDLE) {
			powerUpImage = new ImageIcon(this.getClass().getResource("paddleShrinkPowerUp.png")).getImage();
		}
		if (type == FASTBALL) {
			powerUpImage = new ImageIcon(this.getClass().getResource("increaseBallSpeedPowerUp.png")).getImage();
		}
		if (type == SLOWBALL) {
			powerUpImage = new ImageIcon(this.getClass().getResource("decreaseBallSpeedPowerUp.png")).getImage();
		}
		if (type == FIREBALL) {
			powerUpImage = new ImageIcon(this.getClass().getResource("fireBallPowerUp.png")).getImage();
		}
		if (type == DEADBALL) {
			powerUpImage = new ImageIcon(this.getClass().getResource("deadBallPowerUp.png")).getImage();
		}
	}

	// Draw
	public void draw(Graphics2D g) {

		g.drawImage(powerUpImage, x, y, width, height, null);
	}

	// Update
	public void update() {
		y += dy;

		if (y > Main.HEIGHT) {
			isOnScreen = false;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		x = newX;
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		int y = newY;
	}

	public int getDY() {
		return dy;
	}

	public void setDY(int newDY) {
		dy = newDY;
	}

	public int getType() {
		return type;
	}

	public boolean getIsOnScreen() {
		return isOnScreen;
	}

	public void setIsOnScreen(boolean onScreen) {
		isOnScreen = onScreen;
	}

	public boolean getWasUsed() {
		return wasUsed;
	}

	public void setWasUsed(boolean newWasUsed) {
		wasUsed = newWasUsed;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

}
