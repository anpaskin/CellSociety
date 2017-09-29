package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public abstract class CellManager {

	protected List<Cell> currentCells;
	protected List<String> nextCellStatuses;
	protected double size;
	
	public CellManager(double n) {
		currentCells = new ArrayList<Cell>();
		nextCellStatuses = new ArrayList<String>();
		size = Math.pow(Math.sqrt(n) + 2, 2);
	}
	
	public List<Cell> getCurrentCells() {
		return currentCells;
	}
	
	public double getSize() {
		return size;
	}
	
	protected void setNextCellStatuses() {
		//content depends on simulation type
	}
	
	/**
	 * Updates the current cell statuses according to nextCellStatuses.
	 * Override this method if more than just the status needs to be updated in
	 * a given simulation step.
	 */
	public void updateCurrentCells() {
		setNextCellStatuses();
		for(int n = 0; n < currentCells.size(); n++) {
			currentCells.get(n).setStatus(nextCellStatuses.get(n));
		}
	}
	
	public void initializeCurrentCells() {
		//do nothing
	}
	
	/**
	 * Gets neighbors of given Cell. Neighbors is defined by the getNeighborLocationNums method.
	 * @param c			Cell of which to get the neighbors
	 * @return			An ArrayList of Cells, each a different neighbor of c
	 */
	protected final List<Cell> getNeighbors(Cell c) {
		List<Integer> neighborLocNums = getNeighborLocationNums(c);
		List<Cell> neighbors = new ArrayList<Cell>();
		for(int i = 0; i < neighborLocNums.size(); i++){
			Cell curCell = currentCells.get(neighborLocNums.get(i));
			if(!curCell.getStatus().equals(Cell.NULL)) {
				neighbors.add(curCell);
			}
		}
		return neighbors;
	}
	
	/**
	 * Gets neighbor location numbers of given Cell. A neighbor is defined here as an adjacent 
	 * or diagonal location and can be redefined by overriding this method in a child class.
	 * @param c			Cell of which to get the neighbor location numbers
	 * @return			An ArrayList of Integers, each representing a different neighbor 
	 * 					location of c
	 */
	protected List<Integer> getNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		for(int i = -1; i < 2; i++) {
			locNums.add(cNum - (int)Math.sqrt(currentCells.size()) + i);
			locNums.add(cNum + i);
			locNums.add(cNum + (int)Math.sqrt(currentCells.size()) + i);
		}
		Collections.sort(locNums);
		locNums.remove(locNums.indexOf(cNum));
		return locNums;	
	}

}
