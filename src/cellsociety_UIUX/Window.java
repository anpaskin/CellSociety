package cellsociety_UIUX;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	protected Scene myScene;
	
	public Window(Stage s) {
		myStage = s;
		myRoot = new Group();
		
	}
	
	protected void setupScene() {
		//do nothing
	}
	
	protected void setupSceneDimensions() {
		//do nothing
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	public void userInteraction() {
		//do nothing
	}
	
}
