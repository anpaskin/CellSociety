package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;

public abstract class CardinalSim extends CellManager {

	public CardinalSim(double n, String shape, boolean toroidal, List<String> initialStatuses) {
		super(n, shape, toroidal, initialStatuses);
	}
	
	public CardinalSim(double n, String shape, boolean toroidal) {
		super(n, shape, toroidal);
	}

	/**
	 * Redefines neighbors to be adjacent only (no diagonals).
	 */
	@Override
	public List<Integer> getNeighborLocationNums(Cell c) {
		List<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		if(!isToroidal || !isEdge(cNum)) {
			locNums.add(cNum - (int)Math.sqrt(currentCells.size()));
			locNums.add(cNum + (int)Math.sqrt(currentCells.size()));
			locNums.add(cNum - 1);
			locNums.add(cNum + 1);
		}
		else {
			getToroidalNeighborNums(locNums, cNum);
		}
		Collections.sort(locNums);
		if(locNums.contains(cNum)) locNums.remove(locNums.indexOf(cNum));
		return locNums;
	}
	
	@Override
	protected void addMiddleNeighborNums(List<Integer> locNums, int cNum) {
		int rowSize =(int)Math.sqrt(currentCells.size());
		if(!pointingUp(cNum, rowSize)) locNums.add(cNum - rowSize);
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		if(pointingUp(cNum, rowSize)) locNums.add(cNum + rowSize);
	}
	
	@Override
	protected void addSneighbors(List<Integer> locNums, int cNum, int rowSize) {
		if(cellShape.equals(SQUARE) || !pointingUp(cNum, rowSize)) {
			locNums.add(cNum - rowSize);
		}
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		if((cellShape.equals(SQUARE) || pointingUp(cNum, rowSize))) {
			locNums.add(cNum - (rowSize * (rowSize - 1)));
		}
	}
	
	@Override
	protected void addNneighbors(List<Integer> locNums, int cNum, int rowSize) {
		if(cellShape.equals(SQUARE) || !pointingUp(cNum, rowSize)) locNums.add(cNum + (rowSize * (rowSize - 1)));
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		if(cellShape.equals(SQUARE) || pointingUp(cNum, rowSize)) locNums.add(cNum + rowSize);
	}
	
	@Override
	protected void addEneighbors(List<Integer> locNums, int cNum, int rowSize) {
		if(cellShape.equals(SQUARE) || !pointingUp(cNum, rowSize)) {
			locNums.add(cNum - rowSize);
		}
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize + 1);
		if(cellShape.equals(SQUARE) || pointingUp(cNum, rowSize)) {
			locNums.add(cNum + rowSize);
		}
	}
	
	@Override
	protected void addWneighbors(List<Integer> locNums, int cNum, int rowSize) {
		if(cellShape.equals(SQUARE) || !pointingUp(cNum, rowSize)) locNums.add(cNum - rowSize);
		locNums.add(cNum + rowSize - 1);
		locNums.add(cNum + 1);
		if(cellShape.equals(SQUARE) || pointingUp(cNum, rowSize)) locNums.add(cNum + rowSize);
	}
	
	@Override
	protected void addSEneighbors(List<Integer> locNums, int rowSize) {
		int cNum = (int)size - 1;
		if(cellShape.equals(SQUARE)) locNums.add(cNum - rowSize);
		locNums.add(cNum - 1);
		locNums.add(cNum - rowSize + 1);
		locNums.add(rowSize - 1);
	}
	
	@Override
	protected void addNEneighbors(List<Integer> locNums, int rowSize) {
		int cNum = rowSize - 1;
		if(cellShape.equals(SQUARE) || rowSize % 2 == 0) locNums.add((int)size - 1);
		locNums.add(cNum - 1);
		locNums.add(0);
		if(cellShape.equals(SQUARE) || rowSize % 2 == 1) locNums.add(cNum + rowSize);
	}
	
	@Override
	protected void addSWneighbors(List<Integer> locNums, int rowSize) {
		int cNum = rowSize * (rowSize - 1);
		if(cellShape.equals(SQUARE) || rowSize % 2 == 0) locNums.add(cNum - rowSize);
		locNums.add((int)size - 1);
		locNums.add(cNum + 1);
		if(cellShape.equals(SQUARE) || rowSize % 2 == 1) locNums.add(0);
	}
	
	@Override
	protected void addNWneighbors(List<Integer> locNums, int rowSize) {
		int cNum = 0;
		if(cellShape.equals(SQUARE)) locNums.add(rowSize*(rowSize - 1));
		locNums.add(rowSize - 1);
		locNums.add(cNum + 1);
		locNums.add(rowSize);
	}
	
}
