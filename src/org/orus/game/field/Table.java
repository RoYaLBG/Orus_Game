package org.orus.game.field;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private ArrayList<Food> foods;
	
	String[] fruitsCollection = {foodAnanas, foodBanana, foodPear, foodWaterMelon};
	String[] normalFoodCollection = {foodCake, foodCheese, foodDonuts, foodSomeFood};
	
	private int initialFoodSupply = 60;
	private int score = 0;
	private int fatnessLevel = 0;
	
	private Image backgroundImg;
	private String backgroundImage = "../images/tableBackground.png";
	
	private boolean playing = true;
	public boolean isFat = false;
	
	private Thread animation;
	
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
		foods = new ArrayList<Food>();
		player = new Player();
		
		generateFood(initialFoodSupply);
		
		if (animation == null || !playing) {
			animation = new Thread(this);
			animation.start();
		}
	}

	public void generateFood(int foodSupply) {
		Random rnd = new Random();
		String foodType;
		ImageIcon icon;
		int foodChoice;
		int foodTypeChoice;
		int spawnPosition;
		
		for (int i = 0; i <= foodSupply; i++) {
			foodChoice = rnd.nextInt(4);
			foodTypeChoice = rnd.nextInt(200);
			spawnPosition = 5 + rnd.nextInt(windowWidth - 50);
			
			if (foodTypeChoice <= 12) {
				foodType = "fruit";
				icon = new ImageIcon(getClass().getResource(fruitsCollection[foodChoice]));
				
			} else {
				foodType = "fatFood";
				icon = new ImageIcon(getClass().getResource(normalFoodCollection[foodChoice]));
			}
			
			Food food = new Food(spawnPosition, 0, foodType);
			food.setImage(icon.getImage());
			foods.add(food);
		}
	}
	
	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
	}
	
	public void drawFood(Graphics g) {
		if (foods.size() < initialFoodSupply) {
			generateFood(initialFoodSupply - foods.size());
		}

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
			displayResult();
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
				food.setEaten(true);
				
				if (food.getType().equals("fatFood")) {
					fatnessLevel++;
					score += 5;
				} else {
					fatnessLevel -= 10;
					score += 20;
				}
				
				if (fatnessLevel < 0) {
					fatnessLevel = 0;
				}
				
				if (fatnessLevel > 50 && !isFat) {
					isFat = true;
					ImageIcon icon = new ImageIcon(getClass().getResource("../images/bigMan.png"));
					player.setImage(icon.getImage());
					player.setY(player.getY() - 40);
					player.setWidth(100);
					player.setHeight(105);
					player.setBounds(player.getX(), player.getY(), 0, 0);
					
				} else if (fatnessLevel <= 50 && isFat) {
					isFat = false;
					ImageIcon icon = new ImageIcon(getClass().getResource("../images/littleMan.png"));
					player.setImage(icon.getImage());
					player.setY(player.getY() + 40);
					player.setWidth(60);
					player.setHeight(60);
					player.setBounds(player.getX(), player.getY(), 0, 0);
				}
				
				if (fatnessLevel >= 200) {
					playing = false;
				}
			}
		}
	}
	
	public void displayResult() {
		Graphics g = this.getGraphics();
		
		Font someFont = new Font("Helvetica", Font.BOLD, 16);
		FontMetrics metrics = this.getFontMetrics(someFont);
		
		g.setColor(Color.WHITE);
		g.setFont(someFont);
		g.drawString("Fattness Level: " + String.valueOf(fatnessLevel), 100, 15);
		g.drawString("Score: " + String.valueOf(score), 10, 15);
	}
	
	public void gameOver() {
		Graphics g = this.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(windowWidth / 2 - 160, windowHeight / 2 - 120, 300, 150);
		
		Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metrics = this.getFontMetrics(small);
		
        g.setFont(small);
		g.setColor(Color.WHITE);
		g.drawString("TOO... MUCH... FOOD!", windowWidth / 2 - 115, windowHeight / 2 - 60);
		
		g.setColor(Color.RED);
		g.drawString("GAME OVER!!!", windowWidth / 2 - 75, windowHeight / 2 - 30);
		
	}
	
	public void runAnimation() {
		player.move();
		for (int i = 0; i < foods.size(); i++) {
			Food food = foods.get(i);
			
			if(!food.isEaten()) {
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
		
		gameOver();
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
