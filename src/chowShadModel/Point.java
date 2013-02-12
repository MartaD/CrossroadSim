package chowShadModel;

import java.util.LinkedList;

public class Point {
	private final int MAX_SPEED = 3;
	private Integer currSpeed;
	private Integer nextSpeed;
	private Integer dist;
	private boolean horizontal = true;
	
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
	
	public void brake(int x, int y, Point[][] points, LinkedList<TrafficLights> lights){
		Integer lightDist = new Integer(0);
		TrafficLights nearestLights = null;
		
		if(horizontal){
			//end of board
			if(x+currSpeed>=points.length){
				this.remove();
				
				return;
			}
			
			//calculating distance to nearest vehicle
			for(int i=(x+1);i<points.length;i++){
				if(points[i][y].getSpeed()!=null){
					dist=((i-x)-1)>=0?(i-x)-1:null;
					break;
				}
			}
			if(dist==null)
				dist = new Integer(points.length-1);
			
			//calculating distance to nearest traffic lights
			for(TrafficLights l:lights){
				if(l.getLocation()>=x && l.getY() == y && l.isHorizontal()){
					nearestLights=l;
					break;
				}
			}
			
			if(nearestLights!=null)
				lightDist = nearestLights.getLocation()-x>=0?nearestLights.getLocation()-x:null;
			else
				lightDist = null;	
			
		}
		else {
			//end of board
			if(y+currSpeed>=points[x].length){
				this.remove();
				return;
			}
			
			//calculating distance to nearest vehicle
			for(int i=(y+1);i<points.length;i++){
				if(points[x][i].getSpeed()!=null){
					dist=((i-y)-1)>=0?(i-y)-1:null;
					break;
				}
			}
			if(dist==null)
				dist = new Integer(points[x].length-1);
			
			//calculating distance to nearest traffic lights
			for(TrafficLights l:lights){
				if(l.getY()>=y && l.getLocation()==x && !l.isHorizontal()){
					nearestLights=l;
					break;
				}
			}
			if(nearestLights!=null)
				lightDist = nearestLights.getY()-y>=0?nearestLights.getY()-y:null;
			else
				lightDist = null;	
		}
		
		//speed modification
		if(lightDist==null || nearestLights.isGreen()){
			if(dist!=null)
				currSpeed=Math.min(dist, currSpeed);
		}
		else {
			if(dist!=null && currSpeed.compareTo(Math.min(dist, lightDist))>0)
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
	
	public boolean isHorizontal(){
		return horizontal;
	}
	
	public void setHorizontal(boolean horizontal){
		this.horizontal = horizontal;
	}
	
	public Integer getNextSpeed(){
		return nextSpeed;
	}
}
