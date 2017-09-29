package cellsociety_UIUX;

import cellsociety_Simulations.CellManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	protected Scene myScene;
	protected Timeline animation;
	protected double FRAMES_PER_SECOND = 60.0;
	protected double MILLISECOND_DELAY = 10000.0 / FRAMES_PER_SECOND;
	
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
}
