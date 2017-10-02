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
		//System.out.println("Cell #" + currentCells.indexOf(c) + " neighbors: " + neighborLocNums);
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
		if(!isToroidal || !isEdge(cNum)) {
			addMiddleNeighborNums(locNums, cNum);
		}
		else {
			getToroidalNeighborNums(locNums, cNum);
		}
		Collections.sort(locNums);
		if(locNums.contains(cNum)) locNums.remove(locNums.indexOf(cNum));
		return locNums;
	}

	protected void getToroidalNeighborNums(List<Integer> locNums, int cNum) {
		int rowSize = (int)Math.sqrt(size);
		if(isLeftCol(cNum) && isTopRow(cNum)) {
			addNWneighbors(locNums, rowSize);
		}
		else if(isLeftCol(cNum) && isBottomRow(cNum)) {
			addSWneighbors(locNums, rowSize);
		}
		else if(isRightCol(cNum) && isTopRow(cNum)) {
			addNEneighbors(locNums, rowSize);
		}
		else if(isRightCol(cNum) && isBottomRow(cNum)) {
			addSEneighbors(locNums, rowSize);
		}
		else if(isLeftCol(cNum)) {
			addWneighbors(locNums, cNum, rowSize);
		}
		else if(isRightCol(cNum)) {
			addEneighbors(locNums, cNum, rowSize);
		}
		else if(isTopRow(cNum)) {
			addNneighbors(locNums, cNum, rowSize);
		}
		else if(isBottomRow(cNum)) {
			addSneighbors(locNums, cNum, rowSize);
		}
		else addMiddleNeighborNums(locNums, cNum);
	}

	protected void addMiddleNeighborNums(List<Integer> locNums, int cNum) {
		if(cellShape.equals(TRI)) {
			locNums = getTriNeighborLocationNums(currentCells.get(cNum));
		}
		else {
			for(int i = -1; i < 2; i++) {
				locNums.add(cNum - (int)Math.sqrt(currentCells.size()) + i);
				locNums.add(cNum + i);
				locNums.add(cNum + (int)Math.sqrt(currentCells.size()) + i);
			}
		}
	}

	protected void addSneighbors(List<Integer> locNums, int cNum, int rowSize) {
		for(int i = -1; i < 2; i++) {
			locNums.add(cNum - rowSize + i);
			locNums.add(cNum + i);
			locNums.add(cNum - (rowSize * (rowSize - 1)) + i);
		}
		if(cellShape.equals(TRI)) {
			if(cNum == (rowSize*(rowSize - 1)) + 1) {
				if((rowSize % 2) == 1) {
					locNums.add(cNum - 2);
					locNums.add(cNum - rowSize + 2);
					
				}
				else if((rowSize % 2) == 0) {
					locNums.add(rowSize - 1);
					locNums.add(3);
				}
				locNums.add((int)size - 1);
				locNums.add(cNum + 2);
			}
			else if(cNum == (int)size - 2) {
				locNums.add(cNum - rowSize - 2);
				locNums.add(cNum - 2*rowSize + 2);
				locNums.add(cNum - 2);
				locNums.add(rowSize*(rowSize - 1));
			}
			else {
				if(((cNum / rowSize) % 2 == 1 && cNum % 2 == 1) ||
						((cNum / rowSize) % 2 == 0 && cNum % 2 == 0)) {			//pointing down
					locNums.add(cNum - rowSize - 2);
					locNums.add(cNum - rowSize + 2);
				}
				else if(((cNum / rowSize) % 2 == 0 && cNum % 2 == 1) ||
						((cNum / rowSize) % 2 == 1 && cNum % 2 == 0)) {			//pointing up
					locNums.add(cNum - (rowSize*(rowSize - 1)) + 2);
					locNums.add(cNum - (rowSize*(rowSize - 1)) - 2);
				}
				locNums.add(cNum - 2);
				locNums.add(cNum + 2);
			}
		}
	}

	protected void addNneighbors(List<Integer> locNums, int cNum, int rowSize) {
		for(int i = -1; i < 2; i++) {
			locNums.add(cNum + (rowSize * (rowSize - 1)) + i);
			locNums.add(cNum + i);
			locNums.add(cNum + rowSize + i);
		}
		if(cellShape.equals(TRI)) {
			if(cNum == 1) {
				locNums.add((int)size - 1);
				locNums.add(cNum + (rowSize*(rowSize - 1)) + 2);
				locNums.add(rowSize - 1);
				locNums.add(cNum + 2);
			}
			else if(cNum == rowSize - 2) {
				locNums.add(cNum + (rowSize*(rowSize - 1)) - 2);
				locNums.add(rowSize*(rowSize - 1));
				locNums.add(cNum - 2);
				locNums.add(0);
			}
			else {
				locNums.add(cNum + (rowSize*(rowSize - 1)) - 2);
				locNums.add(cNum + (rowSize*(rowSize - 1)) + 2);
				locNums.add(cNum - 2);
				locNums.add(cNum + 2);
			}
		}
	}

	protected boolean pointingUp(int cNum, int rowSize) {
		if(rowSize % 2 == 1) {
			if(cNum % 2 == 1) return false;
			else return true;
		}
		else {
			if((cNum / rowSize) % 2 == 0) {
				if(cNum % 2 == 0) return true;
				else return false;
			}
			else {
				if(cNum % 2 == 1) return true;
				else return false;
			}
		}
	}
	
	protected void addEneighbors(List<Integer> locNums, int cNum, int rowSize) {
		locNums.add(cNum - rowSize - 1);
		locNums.add(cNum - rowSize);
		locNums.add(cNum - 2*rowSize + 1);
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize + 1);
		locNums.add(cNum + rowSize - 1);
		locNums.add(cNum + rowSize);
		locNums.add(cNum + 1);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum - 2);
			locNums.add(cNum - rowSize + 1);
			if(!pointingUp(cNum, rowSize)) {			//pointing down
				locNums.add(cNum - rowSize - 2);
				locNums.add(cNum - 2*rowSize + 2);
			}
			else if(pointingUp(cNum, rowSize)) {			//pointing up
				locNums.add(cNum + rowSize - 2);
				locNums.add(cNum + 2);
			}
		}
	}

	protected void addWneighbors(List<Integer> locNums, int cNum, int rowSize) {
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize);
		locNums.add(cNum - rowSize + 1);
		locNums.add(cNum + rowSize - 1);
		locNums.add(cNum + 1);
		locNums.add(cNum + 2*rowSize - 1);
		locNums.add(cNum + rowSize);
		locNums.add(cNum + rowSize + 1);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum + rowSize - 2);
			locNums.add(cNum + 2);
			if(!pointingUp(cNum, rowSize)) {		//pointing down
				locNums.add(cNum - 2);
				locNums.add(cNum - rowSize + 2);
			}
			else if(pointingUp(cNum, rowSize)) {	//pointing up
				locNums.add(cNum + 2*rowSize - 2);
				locNums.add(cNum + rowSize + 2);
			}
		}
	}
	
	protected void addSEneighbors(List<Integer> locNums, int rowSize) {
		int cNum = (int)size - 1;
		locNums.add(cNum - rowSize - 1);
		locNums.add(cNum - rowSize);
		locNums.add(cNum - 2*rowSize + 1);
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize + 1);
		locNums.add(rowSize - 2);
		locNums.add(rowSize - 1);
		locNums.add(0);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum - 2);
			locNums.add(cNum - rowSize + 2);
			locNums.add(rowSize - 3);
			locNums.add(1);
		}
	}

	protected void addNEneighbors(List<Integer> locNums, int rowSize) {
		int cNum = rowSize - 1;
		locNums.add((int)size - 2);
		locNums.add((int)size - 1);
		locNums.add(rowSize*(rowSize - 1));
		locNums.add(cNum - 1);
		locNums.add(0);
		locNums.add(cNum * 2);
		locNums.add(cNum + rowSize);
		locNums.add(cNum + 1);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum - 2);
			locNums.add(1);
			if(pointingUp(cNum, rowSize)) {
				locNums.add(cNum + rowSize - 2);
				locNums.add(cNum + 2);
			}
			else if(!pointingUp(cNum, rowSize)) {
				locNums.add((int)size - 3);
				locNums.add(rowSize*(rowSize - 1) + 1);
			}
		}
	}

	protected void addSWneighbors(List<Integer> locNums, int rowSize) {
		int cNum = rowSize * (rowSize - 1);
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize);
		locNums.add(cNum - rowSize + 1);
		locNums.add((int)size - 1);
		locNums.add(cNum + 1);
		locNums.add(rowSize - 1);
		locNums.add(0);
		locNums.add(1);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum + 2);
			locNums.add(rowSize - 2);
			if(pointingUp(cNum, rowSize)) {
				locNums.add(cNum + rowSize - 2);
				locNums.add(2);
			}
			else if(!pointingUp(cNum, rowSize)) {
				locNums.add(cNum - 2);
				locNums.add(cNum - rowSize + 2);
			}
		}
	}

	protected void addNWneighbors(List<Integer> locNums, int rowSize) {
		int cNum = 0;
		locNums.add((int)size - 1);
		locNums.add(rowSize*(rowSize - 1));
		locNums.add(rowSize*(rowSize - 1) + 1);
		locNums.add(rowSize - 1);
		locNums.add(cNum + 1);
		locNums.add(rowSize - 1);
		locNums.add(rowSize);
		locNums.add(rowSize + 1);
		if(cellShape.equals(TRI)) {
			locNums.add(cNum + rowSize - 2);
			locNums.add(cNum + 2);
			locNums.add(cNum + 2*rowSize - 2);
			locNums.add(cNum + rowSize + 2);
		}
	}
	
	private List<Integer> getTriNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		int rowSize = (int)Math.sqrt(size);
		for(int i = -2; i < 3; i++) {
			if(pointingUp(cNum, rowSize)) {		//pointing up
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
				(n % Math.sqrt(size) == n) || (size - n <= Math.sqrt(size));
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
		return size - n <= Math.sqrt(size);
	}
	
	public void initializeCurrentCells(List<String> statuses) {
		if(statuses.size() == 0) {
			initializeCurrentCells();
			return;
		}
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
	
	public String getShape() {
		return cellShape;
	}
	
	public boolean getToroidal() {
		return isToroidal;
	}

}
