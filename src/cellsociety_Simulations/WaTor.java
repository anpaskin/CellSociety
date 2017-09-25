package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;

import cellsociety_Cells.Cell;
import cellsociety_Cells.WaTorCell;

public class WaTor extends CellManager {

	private static final int DEFAULT_ENERGY = 5;
	private double size;
	private int energyStart;
	private int maxLifeCount;
	private double sharkRatio;
	private double fishRatio;
	private int fishEnergyContent;
	private ArrayList<Integer> nextLifeCounts;
	private ArrayList<Integer> nextEnergies;
	
	public WaTor(double sharks, double fish, double n) {
		super();
		sharkRatio = sharks;
		energyStart = DEFAULT_ENERGY;
		fishEnergyContent = 2;
		maxLifeCount = 3;
		fishRatio = fish;
		size = Math.pow(Math.sqrt(n) + 2, 2);
		nextLifeCounts = new ArrayList<Integer>();
		nextEnergies = new ArrayList<Integer>();
	}
	
	public void initializeCurrentCells() {
		ArrayList<Cell> paramCells = setParamCells();
		for(int i = 0; i < size; i++) {
			if((i % Math.sqrt(size) == 0) || (i % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(i % Math.sqrt(size) == i) || (size - i < Math.sqrt(size))) {
				currentCells.add(new WaTorCell("Null"));
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
		
	public ArrayList<Cell> setParamCells() {
		ArrayList<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * fishRatio) {
				paramCells.add(new WaTorCell("Fish", 0, 0));
			}
			else if(k < (pSize * fishRatio) + (pSize * sharkRatio)) {
				paramCells.add(new WaTorCell("Shark", energyStart, 0));
			}
			else {
				paramCells.add(new WaTorCell("Empty", 0, 0));
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
			if(c.getStatus().equals("Shark")) {
				setShark(c);
			}
		}
		for(Cell c : currentCells) {
			if(c.getStatus().equals("Fish")) {
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
		nextCellStatuses.set(currentCells.indexOf(c), "Empty");
		nextLifeCounts.set(currentCells.indexOf(c), 0);
		nextEnergies.set(currentCells.indexOf(c), 0);
	}
	
	private void moveCell(Cell c, int moveLoc) {
		nextCellStatuses.set(moveLoc, c.getStatus());
		nextLifeCounts.set(moveLoc, ((WaTorCell)c).getLifeCount() + 1);
		if(currentCells.get(moveLoc).getStatus().equals("Fish")) {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy() + fishEnergyContent);
		}
		else if(currentCells.get(moveLoc).getStatus().equals("Empty") || moveLoc == currentCells.indexOf(c)) {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy() - 1);
		}
		else {
			nextEnergies.set(moveLoc, ((WaTorCell)c).getEnergy());
		}
	}
	
	private void tryReproduce(Cell c) {
		if(((WaTorCell)c).getLifeCount() == maxLifeCount) {
			nextCellStatuses.set(currentCells.indexOf(c), c.getStatus());
			nextLifeCounts.set(currentCells.indexOf(c), 0);
			nextEnergies.set(currentCells.indexOf(c), DEFAULT_ENERGY);
		}
		else {
			leaveEmpty(c);
		}
	}

	private ArrayList<Integer> fishNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> fishNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if((n.getStatus().equals("Fish") && nextCellStatuses.get(currentCells.indexOf(n)).equals("Fish") && !((WaTorCell)n).getEaten())) {
				fishNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return fishNeighborLocs;
	}

	private ArrayList<Integer> sharkNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> sharkNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals("Shark") && nextCellStatuses.get(currentCells.indexOf(n)).equals("Shark")) {
				sharkNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return sharkNeighborLocs;
	}
	
	private ArrayList<Integer> emptyNeighbors(ArrayList<Cell> neighbors) {
		ArrayList<Integer> emptyNeighborLocs = new ArrayList<Integer>();
		for(Cell n : neighbors) {
			if(n.getStatus().equals("Empty") && nextCellStatuses.get(currentCells.indexOf(n)).equals("Empty")) {
				emptyNeighborLocs.add(currentCells.indexOf(n));
			}
		}
		return emptyNeighborLocs;
	}
	
}
