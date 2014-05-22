package org.orus.game.field;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.orus.game.commons.FoodsCollection;
import org.orus.game.commons.Variables;
import org.orus.game.food.Food;
import org.orus.game.player.Player;

public class Table extends JPanel implements Runnable, Variables, FoodsCollection {
	private Player player;
	private Food food;
	String[] fruitsCollection = {foodAnanas, foodBanana, foodPear, foodWaterMelon};
	String[] normalFoodCollection = {foodCake, foodCheese, foodDonuts, foodSomeFood};
	private ArrayList<Food> foods;
	private Image backgroundImg;
	private String backgroundImage = "../images/tableBackground.png";
	private boolean playing = true;
	
	private Thread animation;
	
	public Table() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		backgroundImg = (new ImageIcon(getClass().getResource(backgroundImage)).getImage());
		
		initGame();
		setDoubleBuffered(true);
	}

//	public void addNotify() {
//		super.addNotify();
//		initGame();
//	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void initGame() {
		foods = new ArrayList<Food>();
		player = new Player();
		
		Random rnd = new Random();
		String foodType;
		ImageIcon icon;
		int foodChoice;
		int foodTypeChoice;
		int spawnPosition;
		
		for (int i = 0; i <= 30; i++) {
			foodChoice = rnd.nextInt(4);
			foodTypeChoice = rnd.nextInt(2);
			spawnPosition = 5 + rnd.nextInt(windowWidth - 5);
			
			if (foodChoice == 0) {
				foodType = "fruit";
				icon = new ImageIcon(getClass().getResource(fruitsCollection[foodChoice]));
				
			} else {
				foodType = "normal";
				icon = new ImageIcon(getClass().getResource(normalFoodCollection[foodChoice]));
			}
			
			Food food = new Food(spawnPosition, 0, foodType);
			food.setImage(icon.getImage());
			foods.add(food);
		}
		
		if (animation == null || !playing) {
			animation = new Thread(this);
			animation.start();
		}
	}
	
	//COLLISION CHECK
	
	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
	}
	
	public void drawFood(Graphics g) {
		Iterator iter = foods.iterator();
		
		while(iter.hasNext()) {
			Food food = (Food) iter.next();
			
			if (food.isVisible()) {
				g.drawImage(food.getImage(), food.getX(), food.getY(), this);
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if (playing) {
			drawPlayer(g);
			drawFood(g);
		}
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void collisionCheck() {
		Rectangle playerBounds = player.getBounds();
		
		for (int i = 0; i < foods.size(); i++) {
			Food food = foods.get(i);
			
			Rectangle foodBounds = food.getBounds();
			
			if (playerBounds.intersects(foodBounds)) {
				food.setVisible(false);
			}
		}
	}
	
	public void runAnimation() {
		player.move();
		
		for (int i = 0; i < foods.size(); i++) {
			Food food = foods.get(i);
			
			if(food.isVisible()) {
				food.fall(3);
			} else {
				foods.remove(i);
			}
		}
		
		collisionCheck();
	}
	
	public void run() {
		long initialTime = System.currentTimeMillis();
		long timeDifference;
		long sleep;		
		
		while (playing) {
			runAnimation();
			repaint();
			
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

		public void keyReleased(KeyEvent event) {
			player.keyReleased(event);
		}
		
		public void keyPressed(KeyEvent event) {
			player.keyPressed(event);
			
		}
		
	}
}
