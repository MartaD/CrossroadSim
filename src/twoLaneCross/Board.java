package twoLaneCross;
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
			points[3][0].setSpeed(0);
			points[2][6].setSpeed(0);
			points[0][10].setSpeed(0);
			points[0][10].setType(1);
			points[1][10].setSpeed(0);
			points[1][10].setType(1);
			
			points[22][9].setSpeed(0);
			points[22][9].setType(3);
			
			points[12][0].setSpeed(0);
			points[12][0].setType(2);
			points[12][2].setSpeed(0);
			points[12][2].setType(2);
			
			points[13][15].setSpeed(0);
			points[13][15].setType(4);
			points[13][16].setSpeed(0);
			points[13][16].setType(4);
			
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
					
					points[x][y].setNextSpeed(null);
					points[x][y].setDist(null);
					
					if(points[x][y].getType()==1){
						if(points[x+speed][y].getNextSpeed()==null){
							points[x+speed][y].setNextSpeed(speed);
							points[x+speed][y].setType(1);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					else if(points[x][y].getType()==2){
						if(points[x][y+speed].getNextSpeed()==null){
							points[x][y+speed].setNextSpeed(speed);
							points[x][y+speed].setType(2);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					else if(points[x][y].getType()==3){
						if(points[x-speed][y].getNextSpeed()==null){
							points[x-speed][y].setNextSpeed(speed);
							points[x-speed][y].setType(3);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					else if(points[x][y].getType()==4){
						if(points[x][y-speed].getNextSpeed()==null){
							points[x][y-speed].setNextSpeed(speed);
							points[x][y-speed].setType(4);
						}
						else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
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
		points = new Point[50][50];
		lights = new LinkedList<TrafficLights>();
		lights.add(new TrafficLights(10, 10, 6, 1, true));
		
		lights.add(new TrafficLights(12, 7, 6, 2, false));
		
		lights.add(new TrafficLights(15, 9, 6, 3, true));
		
		lights.add(new TrafficLights(13, 12, 6, 4, false));
		lights.add(new TrafficLights(13, 5, 4, 4, true));

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y){
				points[x][y] = new Point();
			}
		
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
					
					if(points[x][y].getType()==1)
						g.setColor(new Color(0x0000ff));
					else if(points[x][y].getType()==2)
						g.setColor(Color.cyan);
					else if(points[x][y].getType()==3)
						g.setColor(Color.magenta);
					else if(points[x][y].getType()==4)
						g.setColor(Color.yellow);
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
					g.setColor(Color.white);
					g.drawString(points[x][y].getSpeed().toString(), (x * size) + 1, (y * size) + size);
				}
			}
		}
		
		//Traffic lights
		for(TrafficLights l:lights){
			g.setColor(l.isGreen()?Color.GREEN:Color.RED);
			if(l.getType()==1)
				g.fillRect((l.getLocation() * size) + 1, ((l.getY()+1) * size) + 1, (size - 1), (size - 1));
			else if(l.getType()==2)
				g.fillRect(((l.getLocation()-1) * size) + 1, (l.getY() * size) + 1, (size - 1), (size - 1));
			else if(l.getType()==3)
				g.fillRect((l.getLocation() * size) + 1, ((l.getY()-1) * size) + 1, (size - 1), (size - 1));
			else if(l.getType()==4)
				g.fillRect(((l.getLocation()+1) * size) + 1, (l.getY() * size) + 1, (size - 1), (size - 1));
		}
	}
}
