package cellsociety_Cells;

import javafx.scene.paint.Color;

public class LifeCell extends Cell {

	public static final String ALIVE = "Alive";
	public static final String DEAD = "Dead";
	
	public LifeCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals(ALIVE)) {
			color = Color.YELLOW;
		}
		else if(s.equals(DEAD)) {
			color = Color.GRAY;
		}
	}

}
