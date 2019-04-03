import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Map {

	// Fields
	private int[][] theMap;
	private int brickHeight, brickWidth;

	public final int HOR_PAD = 80, VER_PAD = 50;

	public Map(int row, int column) {

		initializeMap(row, column);

		brickWidth = (Main.WIDTH - 2 * HOR_PAD) / column;
		brickHeight = (Main.HEIGHT / 2 - VER_PAD * 2) / row;
	}

	public void initializeMap(int row, int column) {

		theMap = new int[row][column];

		for (int i = 0; i < theMap.length; i++) {
			for (int j = 0; j < theMap[0].length; j++) {
				int r = (int) (Math.random() * 3 + 1);
				theMap[i][j] = r;
			}
		}

		theMap[5][2] = 4;
		theMap[5][3] = 5;
		theMap[5][4] = 6;
		theMap[5][5] = 7;
		theMap[5][6] = 8;
		theMap[5][7] = 9;
	}

	public void draw(Graphics2D g) {

		for (int row = 0; row < theMap.length; row++) {
			for (int column = 0; column < theMap[0].length; column++) {
				if (theMap[row][column] > 0) {
					if (theMap[row][column] == 1) {
						g.setColor(new Color(0, 200, 200));
					} else if (theMap[row][column] == 2) {
						g.setColor(new Color(00, 150, 150));
					} else if (theMap[row][column] == 3) {
						g.setColor(new Color(00, 100, 100));
					} else if (theMap[row][column] == PowerUp.WIDEPADDLE) { // Switch to colorless.
						g.setColor(PowerUp.WIDECOLOR);
					} else if (theMap[row][column] == PowerUp.FASTBALL) {
						g.setColor(PowerUp.FASTCOLOR);
					}

					g.fillRect(column * brickWidth + HOR_PAD, row * brickHeight + VER_PAD, brickWidth, brickHeight);

					g.setStroke(new BasicStroke(2));

					g.setColor(Color.WHITE);
					g.drawRect(column * brickWidth + HOR_PAD, row * brickHeight + VER_PAD, brickWidth, brickHeight);
				}
			}
		}
	}

	public boolean winCheck() {
		boolean isWinner = false;
		int bricksRemaining = 0;
		for (int row = 0; row < theMap.length; row++) {
			for (int column = 0; column < theMap[0].length; column++) {
				bricksRemaining += theMap[row][column];
			}
		}
		if (bricksRemaining == 0) {
			isWinner = true;
		}

		return isWinner;
	}

	public int[][] getMapArray() {
		return theMap;
	}

	public void setBrick(int row, int column, int value) {
		theMap[row][column] = value;
	}

	public int getBrickWidth() {
		return brickWidth;
	}

	public int getBrickHeight() {
		return brickHeight;
	}

	public void hitBrick(int row, int column) {
		if (Ball.ballDamage == 0) { // DEAD BALL
			theMap[row][column] -= 0;
		} else if (Ball.ballDamage == 1) { // NORMAL BALL
			theMap[row][column] -= 1;
			if (theMap[row][column] < 0) {
				theMap[row][column] = 0;
			}
		} else if (Ball.ballDamage == 3) { // FIRE BALL
			if (theMap[row][column] == 1) {
				theMap[row][column] -= 1;
			} else if (theMap[row][column] == 2) {
				theMap[row][column] -= 2;
			} else if (theMap[row][column] == 3) {
				theMap[row][column] -= 3;
			}
			if (theMap[row][column] < 0) {
				theMap[row][column] = 0;
			}
		}

	}
}
