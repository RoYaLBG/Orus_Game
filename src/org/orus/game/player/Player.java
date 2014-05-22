package org.orus.game.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import org.orus.game.commons.*;

public class Player extends Sprite implements Variables {
	private int initialX = windowWidth / 2;
	private int initialY = windowHeight - ground;
	private final String player = "../images/littleMan.png";
	private int playerWidth;
	private int playerHeight;
	
	public Player() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(player));
		playerWidth = icon.getImage().getWidth(null);
		playerHeight = icon.getImage().getHeight(null);
		initialX -= playerWidth;
		
		
		setImage(icon.getImage());
		setX(initialX);
		setY(initialY);
	}
	
	public void move() {
		x += directionX;
		
		if (x <= 10) {
			x = 10;
		}
		
		if (x >= windowWidth - playerWidth - 50) {
			x = windowWidth - playerWidth - 50;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, playerWidth, playerHeight);
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
