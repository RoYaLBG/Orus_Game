package org.orus.game.food;
import org.orus.game.commons.*;

public class Food extends Sprite {
	private boolean eaten;
	
	public Food(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void fall(int direction) {
		this.y += direction;
	}
	
	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	
	public boolean isEaten() {
		return eaten;
	}
}
