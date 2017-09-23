package cellsociety_team04;

import java.util.ArrayList;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class GameofLife extends CellManager{

	private double size;
	
	public GameofLife(double n) {
		super();
		size = Math.pow(Math.sqrt(n) + 2, 2);
	}
	
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new SegCell("Null"));
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
		//TODO
		return paramCells;
	}
	
	public void setNextCellStatuses() {
		ArrayList<Integer> empties = getValidMoveLocs();
		for(Cell c : currentCells) {
			if(!c.getStatus().equals("Empty") && !c.getStatus().equals("Null")) {
				ArrayList<Cell> neighbors = getNeighbors(c);
				if(checkNeighbors(c, neighbors)) {
					nextCellStatuses.set(empties.get(0), c.getStatus());
					empties.remove(0);
					empties.add(currentCells.indexOf(c));
					nextCellStatuses.set(currentCells.indexOf(c), "Empty");
				}
			}
		}
	}
	
	private ArrayList<Integer> getValidMoveLocs() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(Cell c : currentCells) {
			if(c.getStatus().equals("Empty")) ret.add(currentCells.indexOf(c));
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
		//TODO
/*		for(Cell n : neighbors) {
			if(n.getStatus().equals(c.getStatus())) {
				simCount++;
			}
			if(!n.getStatus().equals("Empty")) {
				occupiedNeighbors++;
			}
		}
		return simCount / occupiedNeighbors < minSimilar;*/
		return true;
	}
	
}
