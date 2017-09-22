package cellsociety_team04;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuWindow extends Window {
	
	public MenuWindow(Stage stage, Group root) {
		this.myStage = stage;
		this.myRoot = root;
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		
	}	
	
	
}
