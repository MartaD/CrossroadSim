package chowShadModel;

public class TrafficLights {
	private final int x, y;
	private int changeTime;
	private boolean horizontal;
	private boolean green=false;
	
	public TrafficLights(int x, int y, int changeTime, boolean horizontal, boolean green){
		this.x = x;
		this.y = y;
		this.changeTime = changeTime;
		this.horizontal = horizontal;
		this.green = green;
	}
	
	public int getLocation(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isGreen(){
		return green;
	}
	
	public void changeLight(){
		if(green)
			green = false;
		else
			green = true;
	}

	public int getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

}
