import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int DELAY = 75;
	static final int PLAYER_SPEED = 10;
	static final int BALL_SPEED = 10;
	static final int BALL_DIAMETER = 15;
	static final int PLAYER_WIDTH = 10;
	static final int PLAYER_HEIGHT = 100;
	char ballDirectionX = ' ';
	char ballDirectionY = ' ';
	char playerOneDirection, playerTwoDirection;
	boolean wIsDown, sIsDown, upIsDown, downIsDown;
	int playerOneY, playerTwoY;
	int ballX, ballY;
	int scoreOne, scoreTwo;
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		running = true;
		resetBoard(); 
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.white);
			g.drawLine(0, 50, SCREEN_WIDTH, 50);
			g.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
			
			//Display the score for player one and player two
			g.setColor(Color.red);
			g.setFont(new Font("TimesRoman", Font.BOLD, 25));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+scoreOne, metrics.stringWidth("Score: "+scoreOne), g.getFont().getSize());
			g.drawString("Score: "+scoreTwo, (SCREEN_WIDTH - metrics.stringWidth("Score: "+scoreTwo)*2), g.getFont().getSize());
		
			g.setColor(Color.white);
			g.fillOval((SCREEN_WIDTH/2)-(BALL_DIAMETER/2), (SCREEN_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
			g.fillRect(25, playerOneY, PLAYER_WIDTH, PLAYER_HEIGHT);
			g.fillRect(SCREEN_WIDTH-25-PLAYER_WIDTH, playerTwoY, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		else {
			gameOver(g);
		}
	}
	public void resetBoard() {
		//Set the players position
		playerOneDirection = ' ';
		playerTwoDirection = ' ';
		playerOneY = SCREEN_HEIGHT/2 - PLAYER_HEIGHT/2;
		playerTwoY = playerOneY;
		
		//Set the ball's position to the middle of the playing area
		ballDirectionX = ' ';
		ballDirectionY = ' ';
		ballX = (SCREEN_WIDTH/2)-(BALL_DIAMETER/2);
		ballY = (SCREEN_HEIGHT/2)-(BALL_DIAMETER/2);
		
		//Determines the way the ball will move when the round starts
		if(random.nextInt(1) == 0) {
			ballDirectionX = 'L';
		} else {
			ballDirectionX = 'R';
		}
		
		if(random.nextInt(1) == 0) {
			ballDirectionY = 'U';
		} else {
			ballDirectionY = 'D';
		}
	}
	public void moveBall() {
		if(ballDirectionX != ' ' && ballDirectionY != ' ') {
			switch(ballDirectionX) {
			case 'L':
				ballX -= BALL_SPEED;
				break;
			case 'R':
				ballX += BALL_SPEED;
				break;
			}
			switch(ballDirectionY) {
			case 'U':
				ballY -= BALL_SPEED;
				break;
			case 'D':
				ballY += BALL_SPEED;
				break;
			}
		}
	}
	public void move() {
		
		if(wIsDown)
			playerOneY -= PLAYER_SPEED;
		if(sIsDown)
			playerOneY += PLAYER_SPEED;
		
		if(upIsDown)
			playerTwoY -= PLAYER_SPEED;
		if(downIsDown)
			playerTwoY += PLAYER_SPEED;
	}
	public void checkBall() {
		
	}
	public void checkCollisions() {
		
	}
	public void gameOver(Graphics g) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			moveBall();
			checkCollisions();
			checkBall();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upIsDown = true;
				break;
			case KeyEvent.VK_DOWN:
				downIsDown = true;
				break;
			case KeyEvent.VK_W:
				wIsDown = true;
				break;
			case KeyEvent.VK_S:
				sIsDown = true;
				break;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upIsDown = false;
				break;
			case KeyEvent.VK_DOWN:
				downIsDown = false;
				break;
			case KeyEvent.VK_W:
				wIsDown = false;
				break;
			case KeyEvent.VK_S:
				sIsDown = false;
				break;
			}
			}
		}
	}