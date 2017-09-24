package cellsociety_Cells;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class GameofLifeCell extends Cell{

	public GameofLifeCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		//this.setStatus(s);
		status = s;
		if(s.equals("Alive")) {
			color = Color.BLACK; //no null cells, so we can reuse black as a color
		}
		else if(s.equals("Dead")) {
			color = Color.WHITE; //no empty cells
		}
	}
	
}