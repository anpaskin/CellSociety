package cellsociety_team04;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Aaron Paskin
 *
 */
public abstract class CellManager {

	protected ArrayList<Cell> currentCells;
	protected ArrayList<String> nextCellStatuses;
	protected double size;
	
	public CellManager() {
		currentCells = new ArrayList<Cell>();
		nextCellStatuses = new ArrayList<String>();
	}
	
	public void setNextCellStatuses() {
		//content depends on simulation type
	}
	
	public void updateCurrentCells() {
		for(int n = 0; n < currentCells.size(); n++) {
			currentCells.get(n).setStatus(nextCellStatuses.get(n));
		}
	}
	
	public final ArrayList<Cell> getNeighbors(Cell c) {
		//System.out.println("Enter Get Neighbors with Cell #" + currentCells.indexOf(c) + ", " + c.getStatus());
		ArrayList<Integer> neighborLocNums = getNeighborLocationNums(c);
		//System.out.println("Neighbor Loc Nums Size: " + neighborLocNums.size());
		//System.out.println("Neighbors: " + neighborLocNums);
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(int i = 0; i < neighborLocNums.size(); i++){
			Cell curCell = currentCells.get(neighborLocNums.get(i));
			if(!curCell.getStatus().equals("Null")) {
				neighbors.add(curCell);
			}
		}
		return neighbors;
	}
	
	private ArrayList<Integer> getNeighborLocationNums(Cell c) {
		ArrayList<Integer> locNums = new ArrayList<Integer>();
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
	
	/**
	 * for testing only
	 * @return
	 */
	public ArrayList<Cell> getCurrentCells() {
		return currentCells;
	}
	
	/*public static void main(String[] args) {
		currentCells = new ArrayList<Cell>();
		for(int x = 0; x < 36; x++) {
			currentCells.add(new Cell("Null"));
		}
		System.out.println(getNeighborLocationNums(currentCells.get(18)));
	}*/
	
	
}
