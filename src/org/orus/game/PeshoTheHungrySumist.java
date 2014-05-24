package org.orus.game;

import javax.swing.JFrame;

import org.orus.game.field.Table;
import org.orus.game.commons.Variables;;

public class PeshoTheHungrySumist extends JFrame implements Variables {
	
	public PeshoTheHungrySumist() {
		add(new Table());
		
		setVisible(true);
		setTitle("Pesho, The Hungry Sumist");
		setSize(windowWidth, windowHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}	
	
	public static void main(String[] args) {
		new PeshoTheHungrySumist();
	}

}
