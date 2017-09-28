package cellsociety_Cells;

import javafx.scene.paint.Color;

public class FireCell extends Cell {

	public static final String FIRE = "Fire";
	public static final String TREE = "Tree";
	
	public FireCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals(FIRE)) {
			color = Color.ORANGE;
		}
		else if(s.equals(TREE)) {
			color = Color.GREEN;
		}
		else if(s.equals(EMPTY)) {
			color = Color.GRAY;
		}
	}
	

}
