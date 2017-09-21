package cellsociety_team04;

import javafx.scene.paint.Color;

public abstract class Cell {

	private String status;
	private Color color;
	
	public Cell(String s) {
		status =  s;
		color = Color.WHITE;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
}
