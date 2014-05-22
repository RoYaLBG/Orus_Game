package org.orus.game.commons;

import java.awt.Image;

public class Sprite {
	private boolean visible;
	private Image img;
	protected int x;
	protected int y;
	protected String type;
	protected boolean dying;
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
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
