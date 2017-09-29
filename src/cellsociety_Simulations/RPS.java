package cellsociety_Simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Cells.RPSCell;

public class RPS extends CellManager {
	
	private double rockRatio;
	private double paperRatio;
	private double scissorsRatio;
	
	public RPS(double rockPercent, double paperPercent, double scissorsPercent, double n) {
		super(n);
		rockRatio = rockPercent;
		paperRatio = paperPercent;
		scissorsRatio = scissorsPercent;
	}
	
	public void initializeCurrentCells() {
		List<Cell> paramCells = setParamCells();
		for(int n = 0; n < size; n++) {
			if((n % Math.sqrt(size) == 0) || (n % Math.sqrt(size) == Math.sqrt(size) - 1) || 
					(n % Math.sqrt(size) == n) || (size - n < Math.sqrt(size))) {
				currentCells.add(new RPSCell(Cell.NULL));
			}
			else {
				int k = (int)(Math.random()*paramCells.size());
				currentCells.add(paramCells.get(k));
				paramCells.remove(k);
			}
		}
	}
	
	private List<Cell> setParamCells() {
		List<Cell> paramCells = new ArrayList<Cell>();
		int pSize = (int)(Math.pow((Math.sqrt(size) - 2), 2));
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
				paramCells.add(new RPSCell(Cell.EMPTY));
			}
		}
		return paramCells;
	}
	
	protected void setNextCellStatuses() {
		//
	}

}
