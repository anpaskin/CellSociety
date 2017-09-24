package cellsociety_Cells;

import javafx.scene.paint.Color;

public class FireCell extends Cell {

	public FireCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals("Fire")) {
			color = Color.ORANGE;
		}
		else if(s.equals("Tree")) {
			color = Color.GREEN;
		}
		else if(s.equals("Empty")) {
			color = Color.GRAY;
		}
	}
	

}
