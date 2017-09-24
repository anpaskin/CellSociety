package cellsociety_team04;

import java.util.ArrayList;
import java.util.Collections;

import cellsociety_Cells.Cell;
import cellsociety_Cells.FireCell;
import cellsociety_Simulations.CellManager;

public class Fire extends CellManager {

	private double pCatch;
	
	public Fire(double probCatch, double n) {
		super();
		pCatch = probCatch;
		size = Math.pow(Math.sqrt(n) + 2, 2);
	}
	
	public void initializeCurrentCells(int fireStartLoc) {
		for(int n = 0; n < size; n++) {
			if((n % Math.sqrt(size) == 0) || (n % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(n % Math.sqrt(size) == n) || (size - n < Math.sqrt(size))) {
				currentCells.add(new FireCell("Null"));
			}
			else {
				currentCells.add(new FireCell("Tree"));
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
		startFire(fireStartLoc);
	}
	
	private void startFire(int fireStartLoc) {
		currentCells.get(fireStartLoc).setStatus("Fire");
	}
	
	public void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(c.getStatus().equals("Fire")) {
				nextCellStatuses.set(currentCells.indexOf(c), "Empty");
			}
			else if(c.getStatus().equals("Tree")) {
				ArrayList<Cell> neighbors = getNeighbors(c);
				if(checkNeighbors(neighbors)) {
					double spread = Math.random();
					if(spread < pCatch) {
						nextCellStatuses.set(currentCells.indexOf(c), "Fire");
					}
				}
			}
		}
	}
	
	/**
	 * @override
	 * @return
	 */
	public ArrayList<Integer> getNeighborLocationNums(Cell c) {
		ArrayList<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		locNums.add(cNum - (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum + (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		Collections.sort(locNums);
		return locNums;
	}
	
	private boolean checkNeighbors(ArrayList<Cell> neighbors) {
		for(Cell c : neighbors) {
			if(c.getStatus().equals("Fire")) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
