package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.FireCell;
import cellsociety_Cells.LifeCell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class GameOfLife extends CellManager{

	private double aliveRatio;
	
	public GameOfLife(double a, double n, String shape, boolean toroidal, List<String> initialStatuses) {
		super(n, shape, toroidal);
		aliveRatio = a;
		initializeCurrentCells(initialStatuses);
	}
	
	public GameOfLife(double a, double n, String shape) {
		this(a, n, shape, true, new ArrayList<String>());
	}

	@Override
	protected List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = getPSize();
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

	@Override
	protected List<Cell> setParamCells(List<String> statuses) {
		List<Cell> ret = new ArrayList<Cell>();
		for(String s : statuses) {
			ret.add(new LifeCell(s));
		}
		return ret;
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
	
	public double getAliveRatio() {
		return aliveRatio;
	}
	
}