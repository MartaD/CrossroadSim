package modelWolframa;
import java.util.ArrayList;

public class Point {
	private int currentState;
	private int nextState;
	private int nextCellState;
	private int prevCellState;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		nextCellState = 0;
		prevCellState = 0;
	}

	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	/*
	 * Elementarny automat komórkowy Wolframa - w każdym ruchu cząstka może tylko zostać na swoim miejscu
	 * lub przesunąć się o jedną pozycję
	 */
	public void calculateNewState() {
		nextState = prevCellState*(1-currentState) + currentState*nextCellState;
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public int getNextCellState() {
		return nextCellState;
	}

	public void setNextCellState(int nextCellState) {
		this.nextCellState = nextCellState;
	}

	public int getPrevCellState() {
		return prevCellState;
	}

	public void setPrevCellState(int prevCellState) {
		this.prevCellState = prevCellState;
	}

	
}
