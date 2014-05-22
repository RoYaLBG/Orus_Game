package org.orus.game.commons;

import java.awt.Image;

public class Sprite {
	private Image img;
	protected String type;
	
	private boolean visible;
	protected boolean dying;
	
	protected int x;
	protected int y;
	
	protected int width;
	protected int height;
	
	protected int directionX;
	
	public Sprite() {
		visible = true;
	}
	
	public void die() {
		visible = false;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setImage(Image image) {
		this.img = image;
	}
	
	public Image getImage() {
		return img;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
