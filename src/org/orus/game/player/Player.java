package org.orus.game.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import org.orus.game.commons.*;

public class Player extends Sprite implements Variables {
	private int initialX = windowWidth / 2;
	private int initialY = windowHeight - ground;
	
	private int playerWidth;
	private int playerHeight;
	
	private Rectangle bounds;
	private final String player = "../images/littleMan.png";
	
	public Player() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(player));
		setWidth(icon.getImage().getWidth(null));
		setHeight(icon.getImage().getHeight(null));
		
		initialX -= getWidth();
		
		setImage(icon.getImage());
		setX(initialX);
		setY(initialY);

	}
	
	public void move() {
		x += directionX;
		
		if (x <= 10) {
			x = 10;
		}
		
		if (x >= windowWidth - getWidth() - 40) {
			x = windowWidth - getWidth() - 40;
		}
		
		setBounds(x, y, getWidth(), getHeight());
	}
	
	public void setBounds(int x, int y, int playerWidth, int playerHeight) {
		this.bounds = new Rectangle(x, y, playerWidth, playerHeight);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			directionX = -4;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			directionX = 4;
		}
	}
	
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			directionX = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			directionX = 0;
		}
	}
}
