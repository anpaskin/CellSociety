package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import cellsociety_Cells.Cell;
import cellsociety_Cells.WaTorCell;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class WaTor extends CellManager {
	
	private int energyStart;						//energy with which every Cell with status "Shark" starts
	private int sharkBreedCount;					//lifeCount at which Cell with status "Shark" can reproduce
	private int fishBreedCount;						//lifeCount at which Cell with status "Fish" can reproduce
	private double sharkRatio;						//percent of all non-"Null" Cells that have status "Shark"
	private double fishRatio;						//percent of all non-"Null" Cells that have status "Fish"
	private int fishEnergyContent;					//amount by which energy increases when Cell with status "Shark" eats Cell with status "Fish"
	private ArrayList<Integer> nextLifeCounts;
	private ArrayList<Integer> nextEnergies;
	
	public WaTor(double sharksPercent, double fishPercent, double n, int initialEnergy, int sharkBreed, int fishBreed, int fishEnergy) {
		super(n);
		sharkRatio = sharksPercent;
		energyStart = initialEnergy;
		fishEnergyContent = fishEnergy;
		sharkBreedCount = sharkBreed;
		fishBreedCount = fishBreed;
		fishRatio = fishPercent;
		nextLifeCounts = new ArrayList<Integer>();
		nextEnergies = new ArrayList<Integer>();
	}
	
	public WaTor(double sharksPercent, double fishPercent, double n) {
		super(n);
		sharkRatio = sharksPercent;
		energyStart = 5;
		fishEnergyContent = 2;
		sharkBreedCount = 5;
		fishBreedCount = 5;
		fishRatio = fishPercent;
		nextLifeCounts = new ArrayList<Integer>();
		nextEnergies = new ArrayList<Integer>();
	}
	
	/**
	 * Randomly sets currentCells statuses from setParamCells list. Wraps Cells in a loop of "Null" Cells.
	 */
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new WaTorCell(Cell.NULL));
			}
			else {
				int k = (int)(Math.random()*paramCells.size());
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
			nextCellStatuses.add(currentCells.get(i).getStatus());
			nextLifeCounts.add(((WaTorCell)(currentCells.get(i))).getLifeCount());
			nextEnergies.add(((WaTorCell)(currentCells.get(i))).getEnergy());
		}
	}
	
	/**
	 * Creates a sorted list of Cells, the statuses of which are determined by the parameters of the simulation.
	 * @return			Sorted list of Cells with correct amount of "Shark", "Fish", and "Empty" statuses
	 */
	public ArrayList<Cell> setParamCells() {
		ArrayList<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * fishRatio) {
				paramCells.add(new WaTorCell(WaTorCell.FISH, 0, 0));
			}
			else if(k < (pSize * fishRatio) + (pSize * sharkRatio)) {
				paramCells.add(new WaTorCell(WaTorCell.SHARK, energyStart, 0));
			}
			else {
				paramCells.add(new WaTorCell(Cell.EMPTY, 0, 0));
			}
		}
		return paramCells;
	}
	
	@Override
	public void updateCurrentCells() {
		for(int n = 0; n < currentCells.size(); n++) {
			currentCells.get(n).setStatus(nextCellStatuses.get(n));
			((WaTorCell)currentCells.get(n)).setEnergy(nextEnergies.get(n));
			((WaTorCell)currentCells.get(n)).setLifeCount(nextLifeCounts.get(n));
			((WaTorCell)currentCells.get(n)).resetEaten();
		}
	}
	
	@Override
	protected ArrayList<Integer> getNeighborLocationNums(Cell c) {
		ArrayList<Integer> locNums = new ArrayList<Integer>();
		int cNum = currentCells.indexOf(c);
		locNums.add(cNum - (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum + (int)Math.sqrt(currentCells.size()));
		locNums.add(cNum - 1);
		locNums.add(cNum + 1);
		Collections.sort(locNums);
		return locNums;
	}
	
	/**
	 * Switch order of for loops to give fish priority
	 */
	public void setNextCellStatuses() {
		
		for(Cell c : currentCells) {
			if(c.getStatus().equals(WaTorCell.SHARK)) {
				setShark(c);
			}
		}
		
		for(Cell c : currentCells) {
			if(c.getStatus().equals(WaTorCell.FISH)) {
				if(!((WaTorCell)c).getEaten()) {
					setFish(c);
				}
			}
		}
		
	}
	
	private void setShark(Cell c) {
		ArrayList<Cell> neighbors = getNeighbors(c);
		if(((WaTorCell)c).getEnergy() == 0) {
			leaveEmpty(c);
		}
		else {
			ArrayList<Integer> fishLocs = fishNeighbors(neighbors);
			ArrayList<Integer> sharkLocs = sharkNeighbors(neighbors);
			ArrayList<Integer> emptyLocs = emptyNeighbors(neighbors);
			if(fishLocs.size() > 0) {
				int moveLoc = fishLocs.get((int)(Math.random()*fishLocs.size()));
				moveCell(c, moveLoc);
				((WaTorCell)currentCells.get(moveLoc)).setEaten();
				tryReproduce(c);
			}
			else if(emptyLocs.size() > 0){
				int moveLoc = emptyLocs.get((int)(Math.random()*emptyLocs.size()));
				moveCell(c, moveLoc);
				tryReproduce(c);
			}
			else {
				nextLifeCounts.set(currentCells.indexOf(c), ((WaTorCell)c).getLifeCount() + 1);
				nextEnergies.set(currentCells.indexOf(c), ((WaTorCell)c).getEnergy() - 1);
			}
		}
	}
	
	private void setFish(Cell c) {
		ArrayList<Cell> neighbors = getNeighbors(c);
		ArrayList<Integer> emptyLocs = emptyNeighbors(neighbors);
		if(!((WaTorCell)c).getEaten() && emptyLocs.size() > 0) {
			int moveLoc = emptyLocs.get((int)(Math.random()*emptyLocs.size()));
			moveCell(c, moveLoc);
			tryReproduce(c);
		}
	}
	
	private void leaveEmpty(Cell c) {
		nextCellStatuses.set(currentCells.indexOf(c), Cell.EMPTY);
		nextLifeCounts.set(currentCells.indexOf(c), 0);
		nextEnergies.set(currentCells.indexOf(c), 0);
	}
	
	private void moveCell(Cell c, int moveLoc) {
		nextCellStatuses.set(moveLoc, c.getStatus());
		nextLifeCounts.set(moveLoc, ((WaTorCell)c).getLifeCount() + 1);
		if(currentCells.get(moveLoc).getStatus().equals(WaTorCell.FISH)) {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy() + fishEnergyContent);
		}
		else if(currentCells.get(moveLoc).getStatus().equals(Cell.EMPTY) || moveLoc == currentCells.indexOf(c)) {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy() - 1);
		}
		else {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy());
		}
	}
	
	private void tryReproduce(Cell c) {
		if(c.getStatus().equals(WaTorCell.SHARK)) {
			if(((WaTorCell)c).getLifeCount() == sharkBreedCount) {
				nextCellStatuses.set(currentCells.indexOf(c), c.getStatus());
				nextLifeCounts.set(currentCells.indexOf(c), 0);
				nextEnergies.set(currentCells.indexOf(c), energyStart);
			}
			else {
				leaveEmpty(c);
			}
		}
		else if(c.getStatus().equals(WaTorCell.FISH)) {
			if(((WaTorCell)c).getLifeCount() == fishBreedCount) {
				nextCellStatuses.set(currentCells.indexOf(c), c.getStatus());
				nextLifeCounts.set(currentCells.indexOf(c), 0);
				nextEnergies.set(currentCells.indexOf(c), energyStart);
			}
			else {
				leaveEmpty(c);
			}
		}
	}

	private ArrayList<Integer> fishNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> fishNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if((n.getStatus().equals(WaTorCell.FISH) && nextCellStatuses.get(currentCells.indexOf(n)).equals(WaTorCell.FISH) && !((WaTorCell)n).getEaten())) {
				fishNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return fishNeighborLocs;
	}

	private ArrayList<Integer> sharkNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> sharkNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals(WaTorCell.SHARK) && nextCellStatuses.get(currentCells.indexOf(n)).equals(WaTorCell.SHARK)) {
				sharkNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return sharkNeighborLocs;
	}
	
	private ArrayList<Integer> emptyNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> emptyNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals(Cell.EMPTY) && nextCellStatuses.get(currentCells.indexOf(n)).equals(Cell.EMPTY)) {
				emptyNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return emptyNeighborLocs;
	}
	
}
