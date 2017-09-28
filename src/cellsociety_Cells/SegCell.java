package cellsociety_Cells;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class SegCell extends Cell{

	public static final String RED = "Red";
	public static final String BLUE = "Blue";
	
	public SegCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		//this.setStatus(s);
		status = s;
		if(s.equals(RED)) {
			color = Color.RED;
		}
		else if(s.equals(BLUE)) {
			color = Color.BLUE;
		}
		else if(s.equals(EMPTY)) {
			color = Color.WHITE;
		}
	}
	
}
