package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.RPSCell;

public class RPS extends CellManager {
	
	private double rockRatio;
	private double paperRatio;
	private double scissorsRatio;
	
	public RPS(double rockPercent, double paperPercent, double scissorsPercent, double n, String shape, boolean toroidal, List<String> initialStatuses) {
		super(n, shape, toroidal);
		rockRatio = rockPercent;
		paperRatio = paperPercent;
		scissorsRatio = scissorsPercent;
		initializeCurrentCells(initialStatuses);
	}
	
	public RPS(double rockPercent, double paperPercent, double scissorsPercent, double n, String shape) {
		this(rockPercent, paperPercent, scissorsPercent, n, shape, true, new ArrayList<String>());
	}
	
	@Override
	protected List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = getPSize();
		for(int k = 0; k < pSize; k++) {
			if(k < pSize * rockRatio) {
				paramCells.add(new RPSCell(RPSCell.ROCK));
			}
			else if(k < (pSize * rockRatio) + (pSize * paperRatio)) {
				paramCells.add(new RPSCell(RPSCell.PAPER));
			}
			else if(k < (pSize * rockRatio) + (pSize * paperRatio) + (pSize * scissorsRatio)) {
				paramCells.add(new RPSCell(RPSCell.SCISSORS));
			}
			else {
				paramCells.add(new RPSCell(RPSCell.EMPTY));
			}
		}
		return paramCells;
	}
	
	@Override
	protected List<Cell> setParamCells(List<String> statuses) {
		List<Cell> ret = new ArrayList<Cell>();
		for(String s : statuses) {
			ret.add(new RPSCell(s));
		}
		return ret;
	}
	
	protected void setNextCellStatuses() {
		for(Cell c : currentCells) {
			if(!c.getStatus().equals(Cell.EMPTY) && !c.getStatus().equals(Cell.NULL) && c.getStatus().equals(nextCellStatuses.get(currentCells.indexOf(c)))) {
				List<Cell> neighbors = removeEmpties(getNeighbors(c));
				if(neighbors.size() > 0) {
					int nChoice = (int)Math.random()*neighbors.size();
					if(c.getStatus().equals(RPSCell.ROCK)) {
						rockMatchup(c, neighbors, nChoice);
					}
					else if(c.getStatus().equals(RPSCell.PAPER)) {
						paperMatchup(c, neighbors, nChoice);
					}
					else if(c.getStatus().equals(RPSCell.SCISSORS)) {
						scissorsMatchup(c, neighbors, nChoice);
					}
				}	
			}
		}
	}

	private void scissorsMatchup(Cell c, List<Cell> neighbors, int nChoice) {
		if(neighbors.get(nChoice).getStatus().equals(RPSCell.ROCK)) {
			nextCellStatuses.set(currentCells.indexOf(c), RPSCell.ROCK);
		}
		else if(neighbors.get(nChoice).getStatus().equals(RPSCell.PAPER)) {
			nextCellStatuses.set(currentCells.indexOf(neighbors.get(nChoice)), RPSCell.SCISSORS);
		}
	}

	private void paperMatchup(Cell c, List<Cell> neighbors, int nChoice) {
		if(neighbors.get(nChoice).getStatus().equals(RPSCell.SCISSORS)) {
			nextCellStatuses.set(currentCells.indexOf(c), RPSCell.SCISSORS);
		}
		else if(neighbors.get(nChoice).getStatus().equals(RPSCell.ROCK)) {
			nextCellStatuses.set(currentCells.indexOf(neighbors.get(nChoice)), RPSCell.PAPER);
		}
	}

	private void rockMatchup(Cell c, List<Cell> neighbors, int nChoice) {
		if(neighbors.get(nChoice).getStatus().equals(RPSCell.PAPER)) {
			nextCellStatuses.set(currentCells.indexOf(c), RPSCell.PAPER);
		}
		else if(neighbors.get(nChoice).getStatus().equals(RPSCell.SCISSORS)) {
			nextCellStatuses.set(currentCells.indexOf(neighbors.get(nChoice)), RPSCell.ROCK);
		}
	}
	
	private List<Cell> removeEmpties(List<Cell> neighbors) {
		List<Cell> ret = new ArrayList<Cell>();
		for(Cell c : neighbors) {
			if(!c.getStatus().equals(Cell.EMPTY)) {
				ret.add(c);
			}
		}
		return ret;
	}

	public double getRockPercent() {
		return rockRatio;
	}
	
	public double getPaperPercent() {
		return paperRatio;
	}
	
	public double getScissorsPercent() {
		return scissorsRatio;
	}
	
}
