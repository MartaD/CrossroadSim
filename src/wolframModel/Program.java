package wolframModel;
import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Program extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	
	public Program(){
		setLayout(new BorderLayout());
		
		board = new Board();
		board.initialize(200, 200);
		
		this.setTitle("Crossroad simulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(200, 200);
		this.add(board);
		this.setVisible(true);
		run();
	}
	
	public void run(){
		while(true){
			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			board.iteration();
			
		}
	}
	

	public static void main(String[] args) {
		new Program();
	}
	
}