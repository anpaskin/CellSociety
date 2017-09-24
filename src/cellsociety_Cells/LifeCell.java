package cellsociety_Cells;

import javafx.scene.paint.Color;

public class LifeCell extends Cell {

	public LifeCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals("Alive")) {
			color = Color.YELLOW;
		}
		else if(s.equals("Dead")) {
			color = Color.GRAY;
		}
	}

}
