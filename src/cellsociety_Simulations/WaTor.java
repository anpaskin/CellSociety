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
public class WaTor extends CellManager {
	
	private int energyStart;						//energy with which every Cell with status "Shark" starts
	private int sharkBreedCount;					//lifeCount at which Cell with status "Shark" can reproduce
	private int fishBreedCount;						//lifeCount at which Cell with status "Fish" can reproduce
	private double sharkRatio;						//percent of all non-"Null" Cells that have status "Shark"
	private double fishRatio;						//percent of all non-"Null" Cells that have status "Fish"
	private int fishEnergyContent;					//amount by which energy increases when Cell with status "Shark" eats Cell with status "Fish"
	private ArrayList<Integer> nextLifeCounts;
	private ArrayList<Integer> nextEnergies;
	
	public WaTor(double sharksPercent, double fishPercent, double n, int initialEnergy, int sharkBreed, int fishBreed, int fishEnergy, String shape, boolean toroidal) {
		super(n, shape, toroidal);
		sharkRatio = sharksPercent;
		energyStart = initialEnergy;
		fishEnergyContent = fishEnergy;
		sharkBreedCount = sharkBreed;
		fishBreedCount = fishBreed;
		fishRatio = fishPercent;
		nextLifeCounts = new ArrayList<Integer>();
		nextEnergies = new ArrayList<Integer>();
	}
	
	public WaTor(double sharksPercent, double fishPercent, double n, String shape, boolean toroidal) {
		super(n, shape, toroidal);
		sharkRatio = sharksPercent;
		energyStart = 3;
		fishEnergyContent = 1;
		sharkBreedCount = 9;
		fishBreedCount = 2;
		fishRatio = fishPercent;
		nextLifeCounts = new ArrayList<Integer>();
		nextEnergies = new ArrayList<Integer>();
	}
	
	/**
	 * Randomly sets currentCells statuses from setParamCells list. Wraps Cells in a loop of "Null" Cells.
	 */
	@Override
	public void initializeCurrentCells() {
		List<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if(!isToroidal) {
				if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
						(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
					currentCells.add(new WaTorCell(Cell.NULL));
				}
				else if(cellShape.equals(TRI) && (i < 2*Math.sqrt(size) || i > size - 2*Math.sqrt(size) || i % Math.sqrt(size) == 1 || i % Math.sqrt(size) == Math.sqrt(size) - 2)) {
					currentCells.add(new WaTorCell(Cell.NULL));
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
			nextCellStatuses.add(currentCells.get(i).getStatus());
			nextLifeCounts.add(((WaTorCell)(currentCells.get(i))).getLifeCount());
			nextEnergies.add(((WaTorCell)(currentCells.get(i))).getEnergy());
		}
	}
	
	@Override
	public void initializeCurrentCells(List<String> statuses) {
		List<Cell> paramCells = setParamCells(statuses);
		for(int i = 0; i < size; i++) {
			if(!isToroidal) {
				if(isEdge(i)) {
					currentCells.add(new WaTorCell(Cell.NULL));
				}
				else if(cellShape.equals(TRI) && (i < 2*Math.sqrt(size) || i > size - 2*Math.sqrt(size) || i % Math.sqrt(size) == 1 || i % Math.sqrt(size) == Math.sqrt(size) - 2)) {
					currentCells.add(new WaTorCell(Cell.NULL));
				}
				else {
					currentCells.add(paramCells.get(i));
				}
			}
			else {
				currentCells.add(paramCells.get(i));
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
	@Override
	protected List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = getPSize();
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * fishRatio) {
				paramCells.add(new WaTorCell(WaTorCell.FISH, 0));
			}
			else if(k < (pSize * fishRatio) + (pSize * sharkRatio)) {
				paramCells.add(new WaTorCell(WaTorCell.SHARK, energyStart));
			}
			else {
				paramCells.add(new WaTorCell(Cell.EMPTY, 0));
			}
		}
		return paramCells;
	}
	
	@Override
	protected List<Cell> setParamCells(List<String> statuses) {
		List<Cell> ret = new ArrayList<Cell>();
		for(String s : statuses) {
			ret.add(new WaTorCell(s, energyStart));
		}
		return ret;
	}
	
	@Override
	public void updateCurrentCells() {
		setNextCellStatuses();
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
	protected void setNextCellStatuses() {
		
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
		List<Cell> neighbors = getNeighbors(c);
		if(((WaTorCell)c).getEnergy() == 0) {
			leaveEmpty(c);
		}
		else {
			List<Integer> fishLocs = fishNeighbors(neighbors);
			List<Integer> sharkLocs = sharkNeighbors(neighbors);
			List<Integer> emptyLocs = emptyNeighbors(neighbors);
			if(fishLocs.size() > 0) {
				int moveLoc = fishLocs.get((int)(Math.random()*fishLocs.size()));
				moveCell(c, moveLoc);
				((WaTorCell)currentCells.get(moveLoc)).setEaten();
				tryReproduce(c);
			}
			else if(emptyLocs.size() > 0){
				int moveLoc = emptyLocs.get((int)(Math.random()*emptyLocs.size()));
				moveCell(c, moveLoc);
			}
			else {
				nextEnergies.set(currentCells.indexOf(c), ((WaTorCell)c).getEnergy() - 1);
			}
			nextLifeCounts.set(currentCells.indexOf(c), ((WaTorCell)c).getLifeCount() + 1);
		}
	}
	
	private void setFish(Cell c) {
		List<Cell> neighbors = getNeighbors(c);
		List<Integer> emptyLocs = emptyNeighbors(neighbors);
		if(!((WaTorCell)c).getEaten() && emptyLocs.size() > 0) {
			int moveLoc = emptyLocs.get((int)(Math.random()*emptyLocs.size()));
			moveCell(c, moveLoc);
		}
		nextLifeCounts.set(currentCells.indexOf(c), ((WaTorCell)c).getLifeCount() + 1);
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
		tryReproduce(c);
	}
	
	private void tryReproduce(Cell c) {
		if(c.getStatus().equals(WaTorCell.SHARK)) {
			if(((WaTorCell)c).getLifeCount() >= sharkBreedCount) {
				nextCellStatuses.set(currentCells.indexOf(c), c.getStatus());
				nextLifeCounts.set(currentCells.indexOf(c), 0);
				nextEnergies.set(currentCells.indexOf(c), energyStart);
			}
			else {
				leaveEmpty(c);
			}
		}
		else if(c.getStatus().equals(WaTorCell.FISH)) {
			if(((WaTorCell)c).getLifeCount() >= fishBreedCount) {
				nextCellStatuses.set(currentCells.indexOf(c), c.getStatus());
				nextLifeCounts.set(currentCells.indexOf(c), 0);
				nextEnergies.set(currentCells.indexOf(c), energyStart);
			}
			else {
				leaveEmpty(c);
			}
		}
	}

	private List<Integer> fishNeighbors(List<Cell> neighbors) {
		List<Integer> fishNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if((n.getStatus().equals(WaTorCell.FISH) && nextCellStatuses.get(currentCells.indexOf(n)).equals(WaTorCell.FISH) && !((WaTorCell)n).getEaten())) {
				fishNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return fishNeighborLocs;
	}

	private List<Integer> sharkNeighbors(List<Cell> neighbors) {
		List<Integer> sharkNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals(WaTorCell.SHARK) && nextCellStatuses.get(currentCells.indexOf(n)).equals(WaTorCell.SHARK)) {
				sharkNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return sharkNeighborLocs;
	}
	
	private List<Integer> emptyNeighbors(List<Cell> neighbors) {
		List<Integer> emptyNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals(Cell.EMPTY) && nextCellStatuses.get(currentCells.indexOf(n)).equals(Cell.EMPTY)) {
				emptyNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return emptyNeighborLocs;
	}
	
}
