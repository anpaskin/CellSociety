package cellsociety_Cells;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public abstract class Cell {

	protected String status;
	protected Color color;
	
	public Cell(String s) {
		setStatus(s);
	}
	
	public String getStatus() {
		return status;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setStatus(String s) {
		status = s;
		if(s.equals("Empty")) {
			color = Color.WHITE;
		}
		else if(s.equals("Null")) {
			color = Color.BLACK;
		}
	}
	
}
