package twoLaneCross;

public class TrafficLights {
	private final int x, y;
	private int changeTime;
	private int type;
	private boolean green=false;
	
	public TrafficLights(int x, int y, int changeTime, int type, boolean green){
		this.x = x;
		this.y = y;
		this.changeTime = changeTime;
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
