package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.FireCell;
import cellsociety_Cells.NullCell;
import cellsociety_Cells.WaTorCell;

public class Fire extends CardinalSim {

	private double pCatch;	//the probability that a neighbor of a Cell with status "Fire" takes on status "Fire"
	
	public Fire(double probCatch, double n, String shape, boolean toroidal) {
		super(n, shape, toroidal);
		pCatch = probCatch;
	}
	
	public Fire(double probCatch, double n, String shape) {
		this(probCatch, n, shape, true);
	}
	
	/**
	 * Sets all current Cell statuses equal to "Tree" so that the simulation is ready to start.
	 * Sets center Cell status to "Fire" by calling startFire.
	 */
	@Override
	public void initializeCurrentCells() {
		for(int n = 0; n < size; n++) {
			if(!isToroidal) {
				if(isEdge(n)) {
					currentCells.add(new NullCell());
				}
				else if(cellShape.equals(TRI) && (n < 2*Math.sqrt(size) || n > size - 2*Math.sqrt(size) || n % Math.sqrt(size) == 1 || n % Math.sqrt(size) == Math.sqrt(size) - 2)) {
					currentCells.add(new NullCell());
				}
				else {
					currentCells.add(new FireCell(FireCell.TREE));
				}
			}
			else {
				currentCells.add(new FireCell(FireCell.TREE));
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
		startFire();
	}
	
	@Override
	public void initializeCurrentCells(List<String> statuses) {
		List<Cell> paramCells = setParamCells(statuses);
		for(int n = 0; n < size; n++) {
			if(!isToroidal) {
				if(isEdge(n)) {
					currentCells.add(new NullCell());
				}
				else if(cellShape.equals(TRI) && (n < 2*Math.sqrt(size) || n > size - 2*Math.sqrt(size) || n % Math.sqrt(size) == 1 || n % Math.sqrt(size) == Math.sqrt(size) - 2)) {
					currentCells.add(new NullCell());
				}
			}
			else {
				currentCells.add(paramCells.get(n));
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
	}
	
	@Override
	protected List<Cell> setParamCells(List<String> statuses) {
		List<Cell> ret = new ArrayList<Cell>();
		for(String s : statuses) {
			ret.add(new FireCell(s));
		}
		return ret;
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
