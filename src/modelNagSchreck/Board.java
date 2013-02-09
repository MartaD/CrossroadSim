package modelNagSchreck;
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
	private TrafficLights lights;
	private int size = 14;
	private int iteration=0;
	private int spawnHor = 3, spawnVer = 3, lightsChange = 4; 

	// single iteration
	public void iteration() {
		iteration++;

		//lights change
		if(iteration%lightsChange==0) {
			lights.changeLight();
		}
		
		//car spawn
		if(iteration%spawnHor==0) {
//			points[3][0].setSpeed(0);
//			points[2][6].setSpeed(0);
			points[0][6].setSpeed(0);
			points[0][6].setHorizontal(true);
		}
		
		if(iteration==1 || iteration%spawnVer==0) {
//			points[3][0].setSpeed(0);
//			points[2][6].setSpeed(0);
			points[6][0].setSpeed(0);
			points[6][0].setHorizontal(false);
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
						points[x+speed][y].setNextSpeed(speed);
						points[x+speed][y].setHorizontal(points[x][y].isHorizontal());
					}
//					else{ 
//						points[x][y+speed].setNextSpeed(speed);
//						points[x][y+speed].setHorizontal(points[x][y].isHorizontal());
//					}
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
		lights = new TrafficLights();

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
					g.setColor(new Color(0x0000ff));
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
					
					
				}
			}
		}
		
		//Traffic lights
		g.setColor(lights.isGreen()?Color.GREEN:Color.RED);
		g.fillRect((lights.getLocation() * size) + 1, ((lights.getY()+1) * size) + 1, (size - 1), (size - 1));

	}
}
