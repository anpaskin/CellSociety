package cellsociety_UIUX;

import cellsociety_Simulations.CellManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * This is the abstract Window superclass which sets up things that every window will need!
 * 
 * @author Kelly Zhang
 *
 */
public abstract class Window {
	protected Stage myStage;
	protected Group myRoot;
	protected Scene myScene;
	protected Timeline animation;
	
	public Window(Stage s) {
		myStage = s;
		myRoot = new Group();
		
	}
	
	public void gameLoop(CellManager simType, double simSpeed) {
		// attach "game loop" to timeline to play it
		KeyFrame frame = new KeyFrame(Duration.millis(simSpeed),
				e -> step());
		//TODO multiply seconddelay by amount sound on speed slider
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	protected void step() {
		//do nothing
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
