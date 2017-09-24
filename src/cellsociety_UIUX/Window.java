package cellsociety_UIUX;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	protected Scene myScene;
	protected static final double WIDTH = 1000;
	protected static final double HEIGHT = 500;
	
	public Window() {
		myStage = new Stage();
		myRoot = new Group();
		setupSceneDimensions();
		myStage.setScene(myScene);
	}
	
	public void setupScene() {
		//do nothing
	}
	
	public void setupSceneDimensions() {
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}
	
	public Scene getScene() {
		return myScene;
	}	
	
}
