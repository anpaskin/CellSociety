package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.FireCell;

public class Fire extends CellManager {

	private double pCatch;	//the probability that a neighbor of a Cell with status "Fire" takes on status "Fire"
	
	public Fire(double probCatch, double n) {
		super(n);
		pCatch = probCatch;
	}
	
	/**
	 * Sets all current Cell statuses equal to "Tree" so that the simulation is ready to start.
	 * Sets center Cell status to "Fire" by calling startFire.
	 */
	public void initializeCurrentCells() {
		for(int n = 0; n < size; n++) {
			if((n % Math.sqrt(size) == 0) || (n % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(n % Math.sqrt(size) == n) || (size - n < Math.sqrt(size))) {
				currentCells.add(new FireCell(Cell.NULL));
			}
			else {
				currentCells.add(new FireCell(FireCell.TREE));
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
		startFire();
	}
	
	/**
	 * Sets center Cell status to "Fire".
	 */
	private void startFire() {
		int i;
		if(size % 2 == 0) {
			i = (int)(size/2 + Math.sqrt(size)/2);
		}
		else i = (int)(size/2);
		currentCells.get(i).setStatus(FireCell.FIRE);
	}
	
	/**
	 * If Cell with status "Tree" is adjacent to Cell with status "Fire", it has a probCatch chance of its next status being set to "Fire".
	 */
	protected void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(c.getStatus().equals("Fire")) {
				nextCellStatuses.set(currentCells.indexOf(c), Cell.EMPTY);
			}
			else if(c.getStatus().equals("Tree")) {
				List<Cell> neighbors = getNeighbors(c);
				if(checkNeighbors(neighbors)) {
					double spread = Math.random();
					if(spread < pCatch) {
						nextCellStatuses.set(currentCells.indexOf(c), FireCell.FIRE);
					}
				}
			}
		}
	}
	
	/**
	 * Redefines neighbors to be adjacent only (no diagonals).
	 */
	@Override
	public List<Integer> getNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		locNums.add(cNum - (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum + (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		Collections.sort(locNums);
		return locNums;
	}
	
	/**
	 * Checks if any neighbors of a Cell have status "Fire".
	 * @param neighbors		ArrayList of neighboring Cells
	 * @return				true if at least one neighbor has status "Fire"
	 * 						false otherwise
	 */
	private boolean checkNeighbors(List<Cell> neighbors) {
		for(Cell c : neighbors) {
			if(c.getStatus().equals(FireCell.FIRE)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
