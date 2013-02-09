package nagSchreckModel;

public class TrafficLights {
	private final int x = 15, y = 6;
	private boolean green=false;
	
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

}
