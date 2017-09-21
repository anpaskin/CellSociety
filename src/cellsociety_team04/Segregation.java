package cellsociety_team04;

import java.util.ArrayList;

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
	private double size;
	
	public Segregation(double t, double r, double empty, double n) {
		super();
		minSimilar = t;
		redRatio = r;
		blueRatio = 1 - r;
		emptyRatio = empty;
		size = Math.pow(Math.sqrt(n) + 1, 2);
	}
	
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i / Math.sqrt(size) == 0) || (i / Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1)) {
				currentCells.add(new SegCell("Null"));
			}
			else {
				int k = (int)(Math.random()*size);
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
		}
	}

	private ArrayList<Cell> setParamCells() {
		ArrayList<Cell> paramCells = new ArrayList<Cell>();
		for(int k = 0; k < size; k++) {
			if(k < size * emptyRatio) {
				paramCells.add(new SegCell("Empty"));
			}
			else if(k < (size * emptyRatio) + ((size - size * emptyRatio) * redRatio)) {
				paramCells.add(new SegCell("Red"));
			}
			else {
				paramCells.add(new SegCell("Blue"));
			}
		}
		return paramCells;
	}
	
	public void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(((SegCell)c).getFillRed()) {
				nextCellStatuses.set(currentCells.indexOf(c), "Red");
			}
			else if(((SegCell)c).getFillBlue()) {
				nextCellStatuses.set(currentCells.indexOf(c), "Blue");
			}
			else if(!c.getStatus().equals("Empty")) {
				ArrayList<Cell> neighbors = getNeighbors(c);
				removeEmptyNeighbors(neighbors);
				if(checkNeighbors(c, neighbors)) {
					nextCellStatuses.set(currentCells.indexOf(c), "Empty");
					for(Cell e : currentCells) {
						if(e.getStatus().equals("Empty")) {
							((SegCell)e).markFill(c.getStatus());
							nextCellStatuses.set(currentCells.indexOf(e), c.getStatus());
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param c
	 * @param neighbors
	 * @return returns true if cell needs to be moved (does not have enough similar neighbors
	 */
	private boolean checkNeighbors(Cell c, ArrayList<Cell> neighbors) {
		double simCount = 0;
		for(Cell n : neighbors) {
			if(n.getStatus().equals(c.getStatus())) {
				simCount++;
			}
		}
		return simCount / neighbors.size() < minSimilar;
	}
	
	private void removeEmptyNeighbors(ArrayList<Cell> neighbors) {
		for(Cell n : neighbors) {
			if(n.getStatus().equals("Empty")) {
				neighbors.remove(n);
			}
		}
	}
	
}
