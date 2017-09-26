package cellsociety_Cells;

import javafx.scene.paint.Color;

public class WaTorCell extends Cell {

	private int energy;
	private int lifeCount;
	private boolean eaten;
	
	public WaTorCell(String s) {
		super(s);
		energy = 5;
		lifeCount = 0;
	}
	
	public WaTorCell(String s, int sEnergy, int lCount) {
		super(s);
		energy = sEnergy;
		lifeCount = lCount;
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals("Fish")) {
			color = Color.TURQUOISE;
		}
		else if(s.equals("Shark")) {
			color = Color.GRAY;
		}
		else if(s.equals("Empty")) {
			color = Color.BLUE;
		}
	}
	
	public int getLifeCount() {
		return lifeCount;
	}
	
	public void setLifeCount(int l) {
		lifeCount = l;
	}
	
	public void incrementLifeCount() {
		lifeCount++;
	}
	
	public void resetLifeCount() {
		lifeCount = 0;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void setEnergy(int e) {
		energy = e;
	}
	
	public void increaseEnergy(int a) {
		energy += a;
	}
	
	public void decrementEnergy() {
		energy--;
	}
	
	public boolean getEaten() {
		return eaten;
	}
	
	public void setEaten() {
		eaten = true;
	}
	
	public void resetEaten() {
		eaten = false;
	}

}
