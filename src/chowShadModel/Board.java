package chowShadModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.LinkedList;

import javax.swing.JComponent;

/**
 * Board with Points that may be expanded 
 */

public class Board extends JComponent{
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	private LinkedList<TrafficLights> lights;
	private int size = 14;
	private int iteration=0;
	private int spawn = 4; 

	// single iteration
	public void iteration() {
		iteration++;

		//lights change
		for(TrafficLights l: lights){
			if(iteration%l.getChangeTime()==0) {
				l.changeLight();
			}
		}
		
		//car spawn
		if(iteration%spawn==0) {
//			points[3][0].setSpeed(0);
//			points[2][6].setSpeed(0);
			points[0][10].setSpeed(0);
			points[0][10].setHorizontal(true);
			points[1][10].setSpeed(0);
			points[1][10].setHorizontal(true);
			points[2][10].setSpeed(0);
			points[2][10].setHorizontal(true);
			
			points[12][0].setSpeed(0);
			points[12][0].setHorizontal(false);
			points[12][2].setSpeed(0);
			points[12][2].setHorizontal(false);
		}

		//acceleration
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if(points[x][y].getSpeed()!=null){
					points[x][y].accelerate();
				}
			}
		
		//breaking
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if(points[x][y].getSpeed()!=null)
					points[x][y].brake(x, y, points.clone(), lights);
			}
		
		//random event
//		for (int x = 0; x < points.length; ++x)
//			for (int y = 0; y < points[x].length; ++y) {
//				if(points[x][y].getSpeed()!=null){
//					
//				}
//			}
		
		//movement
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if(points[x][y].getSpeed()!=null){
					int speed = points[x][y].getSpeed();
					
					if(points[x][y].isHorizontal()){
						if(points[x+speed][y].getNextSpeed()==null){
							points[x+speed][y].setNextSpeed(speed);
							points[x+speed][y].setHorizontal(true);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					else {
						if(points[x][y+speed].getNextSpeed()==null){
							points[x][y+speed].setNextSpeed(speed);
							points[x][y+speed].setHorizontal(false);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					
					if( speed!=0){
						points[x][y].setNextSpeed(null);
						points[x][y].setDist(null);
					}
				}
			}
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].move();
			}
		
		this.repaint();
	}

	// clearing board
	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].setSpeed(null);
			}
		this.repaint();
	}

	public void initialize(int length, int height) {
		points = new Point[25][25];
		lights = new LinkedList<TrafficLights>();
		lights.add(new TrafficLights(11, 10, 6, true, true));
		lights.add(new TrafficLights(12, 8, 6, false, false));

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y){
				points[x][y] = new Point();
			}
//		points[3][0].setSpeed(0);
//		points[2][0].setSpeed(0);
//		points[0][0].setSpeed(0);
		
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
		
		//Cars
		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null) {
					// TODO: set the proper color of the cell
					if(points[x][y].isHorizontal())
						g.setColor(new Color(0x0000ff));
					else
						g.setColor(Color.cyan);
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
					
					
				}
			}
		}
		
		//Traffic lights
		for(TrafficLights l:lights){
			g.setColor(l.isGreen()?Color.GREEN:Color.RED);
			if(l.isHorizontal())
				g.fillRect((l.getLocation() * size) + 1, ((l.getY()+1) * size) + 1, (size - 1), (size - 1));
			else
				g.fillRect(((l.getLocation()-1) * size) + 1, (l.getY() * size) + 1, (size - 1), (size - 1));
		}
	}
}
