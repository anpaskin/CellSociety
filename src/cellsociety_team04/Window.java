package cellsociety_team04;

import javafx.scene.Group;
import javafx.stage.Stage;

public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	
	public void setDimensions(double width, double height) {
		myStage.setWidth(width);
		myStage.setHeight(height);
	}
	
}
