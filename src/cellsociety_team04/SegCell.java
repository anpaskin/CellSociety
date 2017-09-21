package cellsociety_team04;

import javafx.scene.paint.Color;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class SegCell extends Cell{

	private boolean fillRed;		//marks an empty SegCell to which a Red SegCell will move
	private boolean fillBlue;		//marks an empty SegCell to which a Blue SegCell will move
	
	public SegCell(String s) {
		super(s);
	}
	
	public void setStatus(String s) {
		this.setStatus(s);
		if(s.equals("Red")) {
			color = Color.RED;
		}
		else if(s.equals("Blue")) {
			color = Color.BLUE;
		}
	}
	
	public boolean getFillRed() {
		return fillRed;
	}
	
	public boolean getFillBlue() {
		return fillBlue;
	}
	
	public void markFill(String fill) {
		if(status.equals("Empty")) {
			if(fill.equals("Red")) {
				fillRed = true;
			}
			else if(fill.equals("Blue")) {
				fillBlue = true;
			}
		}
	}
	
}
