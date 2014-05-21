package org.orus.game;

import javax.swing.JFrame;

import org.orus.game.field.Table;
import org.orus.game.commons.Variables;;

public class HungryPesho extends JFrame implements Variables {
	
	public HungryPesho() {
		add(new Table());
		
		setVisible(true);
		setTitle("Hungry Pesho - The Game");
		setSize(windowWidth, windowHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}	
	
	public static void main(String[] args) {
		new HungryPesho();
	}

}
