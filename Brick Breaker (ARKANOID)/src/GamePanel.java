
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener {

    // Fields
    private boolean running;
    private BufferedImage image; // It's able to store buffered images in memory.
    private Graphics2D g;    // The artist for our entire game.
    private MyMouseMotionListener theMouseListener;
    private MyKeyListener theKeyListener;
    private int mouseX;
    Image backgroundImage = null;

    // Entities
    private Ball theBall;
    private Paddle thePaddle;
    private Map theMap;
    private HUD theHUD;
    private ArrayList<PowerUp> powerUps;

    // Constructor
    public GamePanel() {
        backgroundImage = new ImageIcon(this.getClass().getResource("background.png")).getImage();

        initialize();
    }

    public void initialize() {
        mouseX = 0;
        theBall = new Ball();
        thePaddle = new Paddle();
        theMap = new Map(6, 8);
        theHUD = new HUD();
        powerUps = new ArrayList<PowerUp>();

        this.setFocusable(true);

        theMouseListener = new MyMouseMotionListener();
        addMouseMotionListener(theMouseListener);

        theKeyListener = new MyKeyListener();
        addKeyListener(theKeyListener);

        running = true;

        image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) image.getGraphics(); // Draw everything on image.

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    public void playGame() {
        // Game Loop
    	
    	
    	update();
    	
    	draw();
    	
    	repaint();
    	
   
    	try {			// Wait for 3sec to start.
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	

        while (running) {
            // Update
            update();

            // Render or draw
            draw();
            
            // Display
            repaint(); // JPanel method

            try {
                Thread.sleep(12);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void pause(int x) {
        //Pause for x milliseconds at the beginning
        try {
            Thread.sleep(x);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public void checkCollisions() {
        Rectangle ballRect = theBall.getRectangle();
        Rectangle paddleRect = thePaddle.getRectangle();

        for (int i = 0; i < powerUps.size(); i++) {
            Rectangle puRect = powerUps.get(i).getRect();

            if (paddleRect.intersects(puRect)) {
                if (powerUps.get(i).getType() == PowerUp.WIDEPADDLE && !powerUps.get(i).getWasUsed()) {
                    thePaddle.setWidth(thePaddle.getWidth() * 2);
                    powerUps.get(i).setWasUsed(true);
                }
                if (powerUps.get(i).getType() == PowerUp.FASTBALL && !powerUps.get(i).getWasUsed()) {
                    theBall.setDX(theBall.getDX() * 1.5);
                    theBall.setDY(theBall.getDY() * 1.5);
                    powerUps.get(i).setWasUsed(true);
                }
                if (powerUps.get(i).getType() == PowerUp.NARROWPADDLE && !powerUps.get(i).getWasUsed()) {
                    thePaddle.setWidth(thePaddle.getWidth() / 2);
                    powerUps.get(i).setWasUsed(true);
                }
                if (powerUps.get(i).getType() == PowerUp.SLOWBALL && !powerUps.get(i).getWasUsed()) {
                    theBall.setDX(theBall.getDX() / 1.5);
                    theBall.setDY(theBall.getDY() / 1.5);
                    powerUps.get(i).setWasUsed(true);
                }
                if (powerUps.get(i).getType() == PowerUp.FIREBALL && !powerUps.get(i).getWasUsed()) {
                    theBall.setBallDamage(3);
                    theBall.setFireBallImage();
                    powerUps.get(i).setWasUsed(true);
                }
                if (powerUps.get(i).getType() == PowerUp.DEADBALL && !powerUps.get(i).getWasUsed()) {
                    theBall.setBallDamage(0);
                    theBall.setDeadBallImage();
                    powerUps.get(i).setWasUsed(true);
                }


            }
        }

        if (ballRect.intersects(paddleRect)) {
            theBall.setDY(-theBall.getDY());

            if (theBall.getX() < mouseX + thePaddle.getWidth() / 4) {    // If the ball hits the paddle's left-end side, move the ball towards in left-end direction.
                theBall.setDX(theBall.getDX() - 1);
            }
            if (theBall.getX() < mouseX + thePaddle.getWidth() && theBall.getX() > mouseX + thePaddle.getWidth() / 4) {    // If the ball hits the paddle's right-end side, move the ball towards in right-end direction.
                theBall.setDX(theBall.getDX() + 1);
            }
        }
        A:
        for (int row = 0; row < theMap.getMapArray().length; row++) {
            for (int column = 0; column < theMap.getMapArray()[0].length; column++) {
                if (theMap.getMapArray()[row][column] > 0) {

                    int brickX = column * theMap.getBrickWidth() + theMap.HOR_PAD;
                    int brickY = row * theMap.getBrickHeight() + theMap.VER_PAD;
                    int brickWidth = theMap.getBrickWidth();
                    int brickHeight = theMap.getBrickHeight();

                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                    if (ballRect.intersects(brickRect)) {
                        if (theMap.getMapArray()[row][column] > 3) {    // If the brick holds a power-up.
                            powerUps.add(new PowerUp(brickX, brickY, theMap.getMapArray()[row][column], brickWidth, brickHeight));
                            theMap.setBrick(row, column, 3);
                        } else {
                            theMap.hitBrick(row, column);
                        }
                        theMap.hitBrick(row, column);
                        if (Ball.ballDamage == 0 || Ball.ballDamage == 1) {
                            theBall.setDY(-theBall.getDY());
                        }
                        theHUD.addScore(100);

                        break A;    // Once you found a collision, stop checking everything else and let the ball move forward.
                    }
                }
            }
        }
    }

    public void update() {
        checkCollisions();

        theBall.update();

        thePaddle.update();

        for (PowerUp pu : powerUps) {
            pu.update();
        }
    }

    public void draw() {
        // Draw Background

        g.drawImage(backgroundImage, 0, 0, Main.WIDTH, Main.HEIGHT, null);

		/*g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);*/

        theMap.draw(g);
        theBall.draw(g);
        thePaddle.draw(g);
        theHUD.draw(g);
        drawPowerUps();

        if (theMap.winCheck() == true) {
            drawWin();
            running = false;
        }
        if (theBall.loseCheck()) {
            drawLose();
            running = false;
        }
    }

    public void drawWin() {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("YOU WON!", Main.WIDTH / 2 - 125, Main.HEIGHT / 2);
    }

    public void drawLose() {
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("YOU LOST!", Main.WIDTH / 2 - 125, Main.HEIGHT / 2);
    }

    public void drawPowerUps() {
        for (PowerUp pu : powerUps) {
            pu.draw(g);
        }
    }

    public void paintComponent(Graphics g) {    //
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, Main.WIDTH, Main.HEIGHT, null);

        g2.dispose();  // Clear memory due to numerous times of calling this method and avoid creating g2 object over and over again.

    }

    private class MyMouseMotionListener implements MouseMotionListener {    // Mouse movement controls paddle movement.

        @Override
        public void mouseDragged(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            thePaddle.mouseMoved(e.getX());
        }
    }

    private class MyKeyListener implements KeyListener {
        private boolean right, left;

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    right = true;
                    thePaddle.move(right, left);
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    thePaddle.move(right, left);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    right = false;
                    thePaddle.move(right, left);
                    break;
                case KeyEvent.VK_LEFT:
                    left = false;
                    thePaddle.move(right, left);
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
    }

}
