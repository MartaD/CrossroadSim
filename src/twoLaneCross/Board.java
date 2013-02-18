package twoLaneCross;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Board with Points that may be expanded
 */

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	private LinkedList<TrafficLights> lights;
	private int size = 14;
	private int iteration = 0;
	private int spawn = 4;
	private static final int PINK_COL = 13;
	private static final int BLUE_COL = 14;
	private static final int LIGHT_BLUE_ROW = 14;
	private static final int GRAY_ROW = 15;
	private static int totalSpeed;
	private static int totalGeneratedCars;
	private Random rand = new Random();

	// single iteration
	public void iteration(int blueCars, int lightBlueCars, int grayCars,
			int pinkCars, int maxSpeed, int conditions, int changeLight) {
		iteration++;

		// lights change
		for (TrafficLights l : lights) {
			if (iteration % changeLight == 0) {
				l.changeLight();
			}
		}

		// car spawn
		if (iteration % spawn == 0) {

			int blueRow = 0;
			for (int i = 0; i < blueCars; i++) {
				points[blueRow][BLUE_COL].setSpeed(0);
				points[blueRow][BLUE_COL].setType(1);
				blueRow = blueRow + 2;
				totalGeneratedCars++;
			}

			int lightBlueCol = 0;
			for (int i = 0; i < lightBlueCars; i++) {
				points[LIGHT_BLUE_ROW][lightBlueCol].setSpeed(0);
				points[LIGHT_BLUE_ROW][lightBlueCol].setType(2);
				lightBlueCol = lightBlueCol + 2;
				totalGeneratedCars++;
			}

			int pinkCol = 28;
			for (int i = 0; i < pinkCars; i++) {
				points[pinkCol][PINK_COL].setSpeed(0);
				points[pinkCol][PINK_COL].setType(3);
				pinkCol = pinkCol - 2;
				totalGeneratedCars++;
			}

			int grayCol = 28;
			for (int i = 0; i < grayCars; i++) {
				points[GRAY_ROW][grayCol].setSpeed(0);
				points[GRAY_ROW][grayCol].setType(4);
				grayCol = grayCol - 2;
				totalGeneratedCars++;
			}

		}

		// acceleration
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null) {
					points[x][y].accelerate(maxSpeed);
				}
			}

		// breaking
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null)
					points[x][y].brake(x, y, points.clone(), lights);
			}

		// random event
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null) {
					if (conditions == 1 && rand.nextInt(101) % 2 == 0) {
						points[x][y].randomBrake();
					} else if (conditions == 2 && rand.nextInt(101) % 4 == 0) {
						points[x][y].randomBrake();
					} else if (conditions == 3 && rand.nextInt(101) % 10 == 0) {
						points[x][y].randomBrake();
					}

				}
			}

		// movement
		totalSpeed = 0;
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null) {
					int speed = points[x][y].getSpeed();

					points[x][y].setNextSpeed(null);
					points[x][y].setDist(null);

					if (points[x][y].getType() == 1) {
						if (points[x + speed][y].getNextSpeed() == null) {
							points[x + speed][y].setNextSpeed(speed);
							points[x + speed][y].setType(1);
						} else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					} else if (points[x][y].getType() == 2) {
						if (points[x][y + speed].getNextSpeed() == null) {
							points[x][y + speed].setNextSpeed(speed);
							points[x][y + speed].setType(2);
						} else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					} else if (points[x][y].getType() == 3) {
						if (points[x - speed][y].getNextSpeed() == null) {
							points[x - speed][y].setNextSpeed(speed);
							points[x - speed][y].setType(3);
						} else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					} else if (points[x][y].getType() == 4) {
						if (points[x][y - speed].getNextSpeed() == null) {
							points[x][y - speed].setNextSpeed(speed);
							points[x][y - speed].setType(4);
						} else {
							points[x][y].setNextSpeed(0);
							speed = 0;
						}
					}
					totalSpeed+=points[x][y].getSpeed();
				}
			}
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].move();
			}
		this.paintComponent(this.getGraphics());
		// this.repaint();
	}

	// clearing board
	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y] != null)
					points[x][y].remove();
			}
		Point.setTotalNumberOfCars(0);
		Board.setTotalSpeed(0);
		Board.setTotalGeneratedCars(0);

		this.paintComponent(this.getGraphics());
		// this.repaint();
	}

	public void initialize(int length, int height) {
		points = new Point[length][height];
		lights = new LinkedList<TrafficLights>();
		// for blue cars
		lights.add(new TrafficLights(13, 14, 6, 1, true));
		// for lightblue cars
		lights.add(new TrafficLights(14, 12, 6, 2, false));
		// for pink cars
		lights.add(new TrafficLights(16, 13, 6, 3, true));
		// for gray cars
		lights.add(new TrafficLights(15, 15, 6, 4, false));
		// lights.add(new TrafficLights(15, 9, 4, 4, true));

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y] = new Point();
			}

	}

	// paint background and separators between cells
	protected void paintComponent(Graphics g) {

		g.setColor(getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.GRAY);
		drawNetting(g, size);
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

		// Cars
		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				if (points[x][y].getSpeed() != null) {

					if (points[x][y].getType() == 1) {
						g.setColor(new Color(0x0000ff));

					} else if (points[x][y].getType() == 2) {
						g.setColor(Color.cyan);

					} else if (points[x][y].getType() == 3) {
						g.setColor(Color.magenta);

					} else if (points[x][y].getType() == 4) {
						g.setColor(Color.GRAY);
					}

					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1),
							(size - 1));
					g.setColor(Color.white);
					g.drawString(points[x][y].getSpeed().toString(),
							(x * size) + 1, (y * size) + size);
				} else if (x < 14 && y < 13 || x > 15 && y < 13 || x < 14
						&& y > 14 || x > 15 && y > 14) {
					g.setColor(Color.black);
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1),
							(size - 1));
				} else {
					g.setColor(Color.white);
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1),
							(size - 1));

				}
			}
		}

		// Traffic lights
		for (TrafficLights l : lights) {
			g.setColor(l.isGreen() ? Color.GREEN : Color.RED);
			if (l.getType() == 1)
				g.fillRect((l.getLocation() * size) + 1,
						((l.getY() + 1) * size) + 1, (size - 1), (size - 1));
			else if (l.getType() == 2)
				g.fillRect(((l.getLocation() - 1) * size) + 1,
						(l.getY() * size) + 1, (size - 1), (size - 1));
			else if (l.getType() == 3)
				g.fillRect((l.getLocation() * size) + 1,
						((l.getY() - 1) * size) + 1, (size - 1), (size - 1));
			else if (l.getType() == 4)
				g.fillRect(((l.getLocation() + 1) * size) + 1,
						(l.getY() * size) + 1, (size - 1), (size - 1));
		}
	}

	public static int getTotalSpeed() {
		return totalSpeed;
	}

	public static void setTotalSpeed(int totalSpeed) {
		Board.totalSpeed = totalSpeed;
	}

	public static int getTotalGeneratedCars() {
		return totalGeneratedCars;
	}

	public static void setTotalGeneratedCars(int totalGeneratedCars) {
		Board.totalGeneratedCars = totalGeneratedCars;
	}
	
	public static BigDecimal getAvgSpeed(){
		BigDecimal avg = new BigDecimal(0);
		if(totalGeneratedCars!=0)
			avg = BigDecimal.valueOf(totalSpeed).divide(BigDecimal.valueOf(totalGeneratedCars-Point.getTotalNumberOfCars()), MathContext.DECIMAL32);
		avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
		return avg;
	}
	
	public int getIteration(){
		return iteration;
	}
}
