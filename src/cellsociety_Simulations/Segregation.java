package cellsociety_Simulations;

import java.util.ArrayList;

import cellsociety_Cells.Cell;
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
	
	public Segregation(double t, double r, double empty, double n) {
		super(n);
		minSimilar = t;
		redRatio = r;
		blueRatio = 1 - r;
		emptyRatio = empty;
	}
	
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new SegCell(SegCell.NULL));
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
	
	public void setNextCellStatuses() {
		ArrayList<Integer> empties = getValidMoveLocs();
		for(Cell c : currentCells) {
			if(!c.getStatus().equals(Cell.EMPTY) && !c.getStatus().equals(SegCell.NULL)) {
				ArrayList<Cell> neighbors = getNeighbors(c);
				if(checkNeighbors(c, neighbors)) {
					nextCellStatuses.set(empties.get(0), c.getStatus());
					empties.remove(0);
					empties.add(currentCells.indexOf(c));
					nextCellStatuses.set(currentCells.indexOf(c), Cell.EMPTY);
				}
			}
		}
	}
	
	private ArrayList<Integer> getValidMoveLocs() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
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
	private boolean checkNeighbors(Cell c, ArrayList<Cell> neighbors) {
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
