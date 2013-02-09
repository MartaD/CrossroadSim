package wolframModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;

/**
 * Board with Points that may be expanded (with automatic change of cell number)
 * with mouse event listener
 */

public class Board extends JComponent{
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	private int size = 14;
	private int iteration=0;
	private boolean light=false;

	// single iteration
	public void iteration() {
		iteration++;
		if(iteration%3==0)
			this.changeLight();
		//ewentualnie opóźnienie
//		try {
//			Thread.currentThread();
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//		}

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				Point cell = points[x][y];
					if(x>0)
						cell.setPrevCellState(points[x-1][y].getState());
					if(x<points.length-1)
						cell.setNextCellState(points[x+1][y].getState());
					else
						cell.setNextCellState(1);
				
				cell.calculateNewState();
			}
		
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].changeState();
			}
		
		this.repaint();
	}

	// clearing board
	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].setState(0);
			}
		this.repaint();
	}

	public void initialize(int length, int height) {
		points = new Point[12][12];
		

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y){
				points[x][y] = new Point();
			}
		points[2][6].setState(1);
		points[1][6].setState(1);
		points[0][6].setState(1);
		
	}

	// paint background and separators between cells
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.GRAY);
		drawNetting(g, size );
	}

	// draws the background netting
	private void drawNetting(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = this.getWidth() - insets.right;
		int lastY = this.getHeight() - insets.bottom;

		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}
		
		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				if (points[x][y].getState() != 0) {
					// TODO: set the proper color of the cell
					//tutaj to zrobić? zmienia się w : point.calculateNewState()
					
					g.setColor(new Color(0x0000ff));
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
				}
			}
		}

	}
	
	public void changeLight(){
		if(this.light)
			light=false;
		else
			light=true;
	}

}
