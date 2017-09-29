package cellsociety_Cells;

import javafx.scene.paint.Color;

public class RPSCell extends Cell {

	
	
	public static final String ROCK = "Rock";
	public static final String PAPER = "Paper";
	public static final String SCISSORS = "Scissors";
	
	public RPSCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals(EMPTY)) {
			color = Color.WHITE;
		}
		else if(s.equals(ROCK)) {
			color = Color.GRAY;
		}
		else if(s.equals(PAPER)) {
			color = Color.BROWN;
		}
		else if(s.equals(SCISSORS)) {
			color = Color.BLUE;
		}
	}
	
}
