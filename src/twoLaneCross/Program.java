package twoLaneCross;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Program extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu menu;

	public Program() {

		this.setTitle("Crossroad simulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new Menu(this);
		menu.initialize(this.getContentPane());

		this.setSize(900, 600);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new Program();
	}

}