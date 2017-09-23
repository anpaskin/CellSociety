package cellsociety_team04;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	protected Scene myScene;
	private static final double WIDTH = 500;
	private static final double HEIGHT = 500;
	
	public Window() {
		myStage = new Stage();
		setSceneDimensions();
		myStage.setScene(myScene);
	}
	
	public void setupScene() {
		//do nothing
	}
	
	public void setSceneDimensions() {
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}
	
	public Scene getScene() {
		return myScene;
	}	
	
}
