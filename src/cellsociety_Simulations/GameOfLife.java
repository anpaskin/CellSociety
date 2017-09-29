package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.List;

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
		super(n);
		aliveRatio = a;
	}
	
	public void initializeCurrentCells() {
		List<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new LifeCell(Cell.NULL));
			}
			else {
				int k = (int)(Math.random()*paramCells.size());
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
			nextCellStatuses.add(currentCells.get(i).getStatus());
		}
	}

	public List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * aliveRatio) {
				paramCells.add(new LifeCell(LifeCell.ALIVE));
			}
			else {
				paramCells.add(new LifeCell(LifeCell.DEAD));
			}
		}
		return paramCells;
	}
	
	protected void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(!c.getStatus().equals(Cell.NULL) && checkNeighbors(c, getNeighbors(c))) {
				if(c.getStatus().equals(LifeCell.ALIVE)) {
					nextCellStatuses.set(currentCells.indexOf(c), LifeCell.DEAD);
				}
				else if(c.getStatus().equals(LifeCell.DEAD)) {
					nextCellStatuses.set(currentCells.indexOf(c), LifeCell.ALIVE);
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
	private boolean checkNeighbors(Cell c, List<Cell> neighbors) {
		int liveNeighbors = 0;
		for(Cell n : neighbors) {
			if(n.getStatus().equals(LifeCell.ALIVE)) {
				liveNeighbors++;
			}
		}
		if(c.getStatus().equals(LifeCell.ALIVE)) {
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