import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int DELAY = 75;
	static final int PLAYER_SPEED = 15;
	static final int BALL_SPEED = 5;
	static final int BALL_DIAMETER = 15;
	static final int PLAYER_WIDTH = 10;
	static final int PLAYER_HEIGHT = 100;
	static final int WINNING_SCORE = 1;
	boolean ballMovingUp, ballMovingLeft;
	boolean wIsDown, sIsDown, upIsDown, downIsDown;
	int playerOneY, playerTwoY;
	int ballX, ballY;
	int scoreOne = 0;
	int scoreTwo = 0;
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
			g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
			g.fillRect(25, playerOneY, PLAYER_WIDTH, PLAYER_HEIGHT);
			g.fillRect(SCREEN_WIDTH-25-PLAYER_WIDTH, playerTwoY, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		else {
			gameOver(g);
		}
	}
	public void resetBoard() {
		playerOneY = SCREEN_HEIGHT/2 - PLAYER_HEIGHT/2;
		playerTwoY = playerOneY;
		
		//Set the ball's position to the middle of the playing area
		ballX = (SCREEN_WIDTH/2)-(BALL_DIAMETER/2);
		ballY = (SCREEN_HEIGHT/2)-(BALL_DIAMETER/2);
		
		//Determines the way the ball will move when the round starts
		if(random.nextBoolean()) {
			ballMovingUp = true;
		}
		else {
			ballMovingUp = false;
		}
		
		if(random.nextBoolean()) {
			ballMovingLeft = true;
		}
		else {
			ballMovingLeft = false;
		}
	}
	public void moveBall() {
		if(ballMovingUp) {
			ballY -= BALL_SPEED;
		}
		else {
			ballY += BALL_SPEED;
		}
		
		if(ballMovingLeft) {
			ballX -= BALL_SPEED;
		}
		else {
			ballX += BALL_SPEED;
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
		if(ballX <= 0) {
			scoreTwo++;
			resetBoard();
		}
		if(ballX >= SCREEN_WIDTH) {
			scoreOne++;
			resetBoard();
		}
		
		if(scoreOne == WINNING_SCORE || scoreTwo == WINNING_SCORE) {
			running = false;
		}
//		else {
//			resetBoard();
//		}
		
	}
	public void checkCollisions() {
		if(ballY <= 50) {
			ballMovingUp = false;
		}
		if(ballY >= SCREEN_HEIGHT) {
			ballMovingUp = true;
		}
		
		if(playerOneY < 50)
			playerOneY = 50;
		if(playerOneY+PLAYER_HEIGHT > SCREEN_HEIGHT)
			playerOneY = SCREEN_HEIGHT-PLAYER_HEIGHT;
		if(playerTwoY < 50)
			playerTwoY = 50;
		if(playerTwoY+PLAYER_HEIGHT > SCREEN_HEIGHT)
			playerTwoY = SCREEN_HEIGHT-PLAYER_HEIGHT;
		
		if(ballX <= 25+PLAYER_WIDTH &&
			 (ballY >= playerOneY && ballY <= playerOneY + PLAYER_HEIGHT)) {
			ballMovingLeft = false;
		}
		if(ballX >= SCREEN_WIDTH-25-PLAYER_WIDTH*2 &&
				 (ballY >= playerTwoY && ballY <= playerTwoY + PLAYER_HEIGHT)) {
				ballMovingLeft = true;
		}
	}
	public void gameOver(Graphics g) {
		String winner;
		if(scoreOne == WINNING_SCORE) {
			winner = "Player 1";
		}
		else {
			winner = "Player 2";
		}
		String winText = winner + " wins!";
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString(winText, (SCREEN_WIDTH - metrics.stringWidth(winText)) / 2, SCREEN_HEIGHT/2);
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