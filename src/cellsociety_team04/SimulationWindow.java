package cellsociety_team04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class SimulationWindow extends Window {
	protected boolean start;
	protected Button run;
	protected Button step;
	protected double speed;
	protected double numCells;
	protected double cellSize;
	
	protected List<Button> buttons;
		
	protected boolean windowOpen = false;
	protected boolean simulationRunning = false;

	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


	public SimulationWindow() {
		super();
		setupScene();
		userInteraction();

		// attach "game loop" to timeline to play it
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void userInteraction() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Updates the cells for each SimulationWindow
	 */
	public void step(double elapsedTime) {
		// do nothing
	}

	@Override
	public void setupScene() {
		buttons = new ArrayList<Button>();
		addButtons();
		addTitle();
	}

	private void addButtons() {
		//TODO
	}
	
	private void addTitle() {
		//TODO
	}

	private void addSlider() {
		//TODO
	}

	public GridPane addGridPane() { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		GridPane grid = new GridPane();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Rectangle rect = new Rectangle();
				rect.setWidth(cellSize);
				rect.setHeight(cellSize);
				GridPane.setRowIndex(rect, row);
				GridPane.setColumnIndex(rect, col);
				grid.getChildren().add(rect);
			}
		}
		myRoot.getChildren().add(grid);
		return grid;
	}

	public boolean getWindowOpen() {
		return windowOpen;
	}
	public void setWindowOpen(boolean b) {
		windowOpen = b;
	}
	
	public boolean getSimulationRunning() {
		return simulationRunning;
	}	
	public void setSimulationRunning(boolean b) {
		simulationRunning = b;
	}
}
