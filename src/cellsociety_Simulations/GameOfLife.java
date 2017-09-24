package cellsociety_Simulations;

import java.util.ArrayList;

import cellsociety_Cells.Cell;
import cellsociety_Cells.LifeCell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class GameOfLife extends CellManager{

	private double aliveRatio;
	
	public GameOfLife(double a, double n) {
		super();
		size = Math.pow(Math.sqrt(n) + 2, 2);
		aliveRatio = a;
	}
	
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new LifeCell("Null"));
			}
			else {
				int k = (int)(Math.random()*paramCells.size());
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
			nextCellStatuses.add(currentCells.get(i).getStatus());
		}
	}

	public ArrayList<Cell> setParamCells() {
		ArrayList<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * aliveRatio) {
				paramCells.add(new LifeCell("Alive"));
			}
			else {
				paramCells.add(new LifeCell("Dead"));
			}
		}
		return paramCells;
	}
	
	public void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(!c.getStatus().equals("Null") && checkNeighbors(c, getNeighbors(c))) {
				if(c.getStatus().equals("Alive")) {
					nextCellStatuses.set(currentCells.indexOf(c), "Dead");
				}
				else if(c.getStatus().equals("Dead")) {
					nextCellStatuses.set(currentCells.indexOf(c), "Alive");
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @param c
	 * @param neighbors
	 * @return returns true if cell needs to be moved (does not have enough similar neighbors)
	 */
	private boolean checkNeighbors(Cell c, ArrayList<Cell> neighbors) {
		int liveNeighbors = 0;
		for(Cell n : neighbors) {
			if(n.getStatus().equals("Alive")) {
				liveNeighbors++;
			}
		}
		if(c.getStatus().equals("Alive")) {
			if(liveNeighbors < 2 || liveNeighbors > 3) {
				return true;
			}
		}
		else {
			if(liveNeighbors == 3) {
				return true;
			}
		}
		return false;
	}
	
}