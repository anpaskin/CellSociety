package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.FireCell;
import cellsociety_Cells.SegCell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class Segregation extends CellManager{

	private double minSimilar;
	private double redRatio;
	private double blueRatio;
	private double emptyRatio;
	
	public Segregation(double t, double r, double empty, double n, String shape) {
		super(n, shape);
		minSimilar = t;
		redRatio = r;
		blueRatio = 1 - r;
		emptyRatio = empty;
	}

	@Override
	protected List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * emptyRatio) {
				paramCells.add(new SegCell(Cell.EMPTY));
			}
			else if(k < (pSize * emptyRatio) + ((pSize - pSize * emptyRatio) * redRatio)) {
				paramCells.add(new SegCell(SegCell.RED));
			}
			else {
				paramCells.add(new SegCell(SegCell.BLUE));
			}
		}
		return paramCells;
	}
	
	@Override
	protected List<Cell> setParamCells(List<String> statuses) {
		List<Cell> ret = new ArrayList<Cell>();
		for(String s : statuses) {
			ret.add(new SegCell(s));
		}
		return ret;
	}
	
	protected void setNextCellStatuses() {
		List<Integer> empties = getValidMoveLocs();
		for(Cell c : currentCells) {
			if(!c.getStatus().equals(Cell.EMPTY) && !c.getStatus().equals(SegCell.NULL)) {
				List<Cell> neighbors = getNeighbors(c);
				List<Integer> neighborNums = new ArrayList<Integer>();
				for(Cell x : neighbors) {
					neighborNums.add(currentCells.indexOf(x));
				}
				System.out.println("Cell #" + currentCells.indexOf(c) + " neighbors: " + neighborNums);
				if(checkNeighbors(c, neighbors)) {
					nextCellStatuses.set(empties.get(0), c.getStatus());
					empties.remove(0);
					empties.add(currentCells.indexOf(c));
					nextCellStatuses.set(currentCells.indexOf(c), Cell.EMPTY);
				}
			}
		}
	}
	
	private List<Integer> getValidMoveLocs() {
		List<Integer> ret = new ArrayList<Integer>();
		for(Cell c : currentCells) {
			if(c.getStatus().equals(Cell.EMPTY)) ret.add(currentCells.indexOf(c));
		}
		return ret;
	}

	/**
	 * 
	 * @param c
	 * @param neighbors
	 * @return returns true if cell needs to be moved (does not have enough similar neighbors)
	 */
	private boolean checkNeighbors(Cell c, List<Cell> neighbors) {
		double simCount = 0;
		double occupiedNeighbors = 0;
		for(Cell n : neighbors) {
			if(n.getStatus().equals(c.getStatus())) {
				simCount++;
			}
			if(!n.getStatus().equals(Cell.EMPTY)) {
				occupiedNeighbors++;
			}
		}
		return simCount / occupiedNeighbors < minSimilar;
	}
	
}
