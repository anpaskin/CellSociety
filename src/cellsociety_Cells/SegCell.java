package cellsociety_Cells;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class SegCell extends Cell{

	public SegCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		//this.setStatus(s);
		status = s;
		if(s.equals("Red")) {
			color = Color.RED;
		}
		else if(s.equals("Blue")) {
			color = Color.BLUE;
		}
	}
	
}
