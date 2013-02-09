package nagSchreckModel;

public class Point {
	private final int MAX_SPEED = 3;
	private Integer currSpeed;
	private Integer nextSpeed;
	private Integer dist;
	private boolean horizontal=true;
	
	
	public Point() {
		currSpeed = null;
		nextSpeed = null;
		dist = null;
	}

	public Integer getSpeed() {
		return currSpeed;
	}

	public void setSpeed(Integer speed) {
		if(this.currSpeed==null)
			currSpeed = new Integer(speed);
		else
			this.currSpeed = speed;
	}
	
	public void setNextSpeed(Integer speed) {
		if(this.nextSpeed==null){
			this.nextSpeed = new Integer(0);
			nextSpeed = speed;
		}
		else {
			this.nextSpeed = speed;
		}
	}
	
	public int getDist(){
		return dist;
	}
	
	public void setDist(Integer dist){
		if(this.dist!=null)
			this.dist = dist;
		else{
			this.dist = new Integer(0);
			this.dist = dist;
		}
	}
	
	public void accelerate(){
		
		currSpeed=Math.min(MAX_SPEED, currSpeed+1);
	}
	
	public void brake(int x, int y, Point[][] points, TrafficLights lights){
		//end of board
		if(x+currSpeed>=points.length){
			this.remove();
			
			return;
		}
		for(int i=++x;i<points.length;i++){
			//closest vehicle
			if(points[i][y].getSpeed()!=null){
				dist=(i-x);
				break;
			}
		}
		if(dist==null)
			dist = new Integer(points.length-1);
		
		Integer lightDist = new Integer(0);
		lightDist = lights.getLocation()-x>=0?lights.getLocation()-x:null;
		
		if(lights.isGreen() || lightDist==null){
			if(dist!=null && currSpeed.compareTo(dist)>0)
				currSpeed=dist;
		}
		else {
			if(dist!=null && currSpeed.compareTo(Math.min(dist==null?100:dist, lightDist))>0)
				currSpeed=Math.min(dist, lightDist);
		}
	}
	
	public void move(){
		currSpeed = nextSpeed;
	}
	
	public void remove(){
		dist = null;
		currSpeed = null;
		nextSpeed = null;
	}
	
	public void setHorizontal(boolean hor){
		this.horizontal = hor;
	}

	public boolean isHorizontal() {
		return horizontal;
	}
	
}
