package org.orus.game.field;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.orus.game.commons.Variables;
import org.orus.game.player.Player;

public class Table extends JPanel implements Runnable, Variables {
	private Player player;
	private String backgroundImage = "../images/tableBackground.png";
	private Image backgroundImg;
	private Thread animation;
	private boolean playing = true;

	public Table() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		backgroundImg = (new ImageIcon(getClass().getResource(backgroundImage)).getImage());
		
		initGame();
		setDoubleBuffered(true);
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void initGame() {
		player = new Player();
		
		if (animation == null || !playing) {
			animation = new Thread();
			animation.start();
		}
	}

	//DRAW PLAYER
	//DRAW FOOD
	//PAINT ALL
	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		drawPlayer(g);
		
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void runAnimation() {
		player.move();
	}
	
	public void run() {
		long initialTime = System.currentTimeMillis();
		long timeDifference;
		long sleep;		
		
		while (true) {
			repaint();
			runAnimation();
			
			timeDifference = System.currentTimeMillis() - initialTime;
			sleep = delay - timeDifference;
			
			if (sleep < 0) {
				sleep = 2;
			}
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			
			initialTime = System.currentTimeMillis();			
		}
	}
	
	private class TAdapter extends KeyAdapter {
		
		public void keyPressed(KeyEvent event) {
			player.keyPressed(event);
			
			int x = player.getX();
			int y = player.getY();
		}
		
		public void keyReleased(KeyEvent event) {
			player.keyReleased(event);
		}
	}
}
