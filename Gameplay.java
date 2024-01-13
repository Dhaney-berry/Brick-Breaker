package BrickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private Timer timer;
	private int delay = 6;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;

	public Gameplay() {
		map = new MapGenerator(5,9);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		///add background 
		g.setColor(Color.gray);
		g.fillRect(1, 1, 692, 592);
		
		//map
		map.draw((Graphics2D)g);
			
		//borders
		g.setColor(Color.black);
		g.fillRect(0,  0,  3,  592);
		g.fillRect(0,  0,  692,  3);
		g.fillRect(691,  0,  3,  592);
		
		//paddle
		g.setColor(Color.white);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.pink);
		g.fillOval(ballposX, ballposY, 17, 17);
		
		g.dispose();
	}
	

	 @Override
	    public void actionPerformed(ActionEvent e) {
	        timer.start();
	        if (play) {
	            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
	                ballYdir = -ballYdir;
	            }

	            A:
	            for (int i = 0; i < map.map.length; i++) {
	                for (int j = 0; j < map.map[0].length; j++) {
	                    if (map.map[i][j] > 0) {
	                        int brickX = j * map.brickWidth + 80;
	                        int brickY = i * map.brickHeight + 50;
	                        int bricksWidth = map.brickWidth;
	                        int bricksHeight = map.brickHeight;

	                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
	                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
	                        Rectangle brickrect = rect;

	                        if (ballrect.intersects(brickrect)) {
	                            map.setBrickValue(0, i, j);
	                            totalBricks--;
	                            score += 5;
	                            
	                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
	                                ballXdir = -ballXdir;
	                            } else {
	                                ballYdir = -ballYdir;
	                            }
	                            break A;
	                        }
	                    }


	                }
	            }


	            ballposX += ballXdir;
	            ballposY += ballYdir;
	            if (ballposX < 0) {
	                ballXdir = -ballXdir;
	            }
	            if (ballposY < 0) {
	                ballYdir = -ballYdir;
	            }
	            if (ballposX > 670) {
	                ballXdir = -ballXdir;
	            }
	        }
	        repaint();
	    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			}else {
				moveLeft();
			}
			
		}
		
	}
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
