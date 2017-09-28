package cellsociety_Cells;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class GameofLifeCell extends Cell{

	public static final String ALIVE = "Alive";
	public static final String DEAD = "Dead";
	
	public GameofLifeCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals(ALIVE)) {
			color = Color.BLACK;
		}
		else if(s.equals(DEAD)) {
			color = Color.WHITE;
		}
	}
	
}