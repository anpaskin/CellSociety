package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.NullCell;
import cellsociety_Cells.WaTorCell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public abstract class CellManager {

	protected List<Cell> currentCells;
	protected List<String> nextCellStatuses;
	protected double size;
	protected String cellShape;
	protected boolean isToroidal;
	
	public static final String SQUARE = "square";
	public static final String TRI = "triangle";
	public static final String HEX = "hexagon";
	
	public CellManager(double n, String shape) {
		currentCells = new ArrayList<Cell>();
		nextCellStatuses = new ArrayList<String>();
		cellShape = shape;
		if(cellShape.equals(SQUARE)) size = Math.pow(Math.sqrt(n) + 2, 2);
		else if(cellShape.equals(TRI)) size = Math.pow(Math.sqrt(n) + 4, 2);
		isToroidal = false;
	}
	
	public CellManager(double n, String shape, boolean toroidal) {
		this(n, shape);
		isToroidal = toroidal;
		if(toroidal) size = n;
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
		if(cellShape.equals(SQUARE)) {
			locNums = getSquareNeighborLocationNums(c);	
		}
		else if(cellShape.equals(TRI)) {
			locNums = getTriNeighborLocationNums(c);
		}
		return locNums;
	}

	private List<Integer> getSquareNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		if(!isToroidal) {
			for(int i = -1; i < 2; i++) {
				locNums.add(cNum - (int)Math.sqrt(currentCells.size()) + i);
				locNums.add(cNum + i);
				locNums.add(cNum + (int)Math.sqrt(currentCells.size()) + i);
			}
		}
		else {
			int rowSize = (int)Math.sqrt(size);
			if(isLeftCol(cNum) && isTopRow(cNum)) {
				locNums.add((int)size - 1);
				locNums.add(rowSize*(rowSize - 1));
				locNums.add(rowSize*(rowSize - 1) + 1);
				locNums.add(rowSize - 1);
				locNums.add(1);
				locNums.add(rowSize - 1);
				locNums.add(rowSize);
				locNums.add(rowSize + 1);
			}
			else if(isLeftCol(cNum) && isBottomRow(cNum)) {
				locNums.add(cNum - 1);
				locNums.add(cNum - rowSize);
				locNums.add(cNum - rowSize + 1);
			}
			
		}
		Collections.sort(locNums);
		locNums.remove(locNums.indexOf(cNum));
		return locNums;
	}
	
	private List<Integer> getTriNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		for(int i = -2; i < 3; i++) {
			if(cNum % 2 == 0) {		//pointing up
				if(i > -2 && i < 2) locNums.add(cNum - (int)Math.sqrt(currentCells.size()) + i);
				locNums.add(cNum + i);
				locNums.add(cNum + (int)Math.sqrt(currentCells.size()) + i);
			}
			else {					//pointing down
				locNums.add(cNum - (int)Math.sqrt(currentCells.size()) + i);
				locNums.add(cNum + i);
				if(i > -2 && i < 2) locNums.add(cNum + (int)Math.sqrt(currentCells.size()) + i);
			}
		}
		Collections.sort(locNums);
		locNums.remove(locNums.indexOf(cNum));
		return locNums;
	}

	public void initializeCurrentCells() {
		List<Cell> paramCells = setParamCells();
		for(int n = 0; n < size; n++) {
			if(!isToroidal) {
				if(isEdge(n)) {
					currentCells.add(new NullCell());
				}
				else if(cellShape.equals(TRI) && (n < 2*Math.sqrt(size) || n > size - 2*Math.sqrt(size) || n % Math.sqrt(size) == 1 || n % Math.sqrt(size) == Math.sqrt(size) - 2)) {
					currentCells.add(new NullCell());
				}
				else {
					int k = (int)(Math.random()*paramCells.size());
					currentCells.add(paramCells.get(k));
					paramCells.remove(k);
				}
			}
			else {
				int k = (int)(Math.random()*paramCells.size());
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
	}

	protected boolean isEdge(int n) {
		return (n % Math.sqrt(size) == 0) || (n % Math.sqrt(size) == Math.sqrt(size) - 1) || 
				(n % Math.sqrt(size) == n) || (size - n < Math.sqrt(size));
	}
	
	protected boolean isLeftCol(int n) {
		return n % Math.sqrt(size) == 0;
	}
	
	protected boolean isRightCol(int n) {
		return n % Math.sqrt(size) == Math.sqrt(size) - 1;
	}
	
	protected boolean isTopRow(int n) {
		return n % Math.sqrt(size) == n;
	}
	
	protected boolean isBottomRow(int n) {
		return size - n < Math.sqrt(size);
	}
	
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
				else {
					int k = (int)(Math.random()*paramCells.size());
					currentCells.add(paramCells.get(k));
					paramCells.remove(k);
				}
			}
			else {
				currentCells.add(paramCells.get(n));
			}
			nextCellStatuses.add(currentCells.get(n).getStatus());
		}
	}
	
	protected List<Cell> setParamCells() {
		return new ArrayList<Cell>((int)size);
	}
	
	protected List<Cell> setParamCells(List<String> statuses) {
		return new ArrayList<Cell>();
	}

	protected int getPSize() {
		int pSize;
		if(isToroidal) pSize = (int)size;
		else if(cellShape.equals(TRI)) pSize = (int)(Math.pow((Math.sqrt(size) - 4), 2));
		else pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		return pSize;
	}

}
