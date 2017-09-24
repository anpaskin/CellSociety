package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class SimulationWindow extends Window {
	
	protected double WIDTH;
	protected double HEIGHT;
	
	protected boolean start;
	protected Button run;
	protected Button step;
	
	protected double speed;
	protected int numCells = 10;
	protected int cellSize = 10;
	
	protected List<Button> buttons;
	protected int buttonOffset = 50;
	protected int buttonPadding = 100;
		
	protected boolean windowOpen = false;
	protected boolean simulationRunning = false;

	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


	public SimulationWindow(Stage s) {
		super(s);
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
		setupSceneDimensions();
		buttons = new ArrayList<Button>();
		addButtons();
		placeButtons();
		addTitle();
		addGridPane();
	}
	
	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX();
		HEIGHT = dimensions.getMaxY();
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		myStage.setWidth(WIDTH);
		myStage.setHeight(HEIGHT);
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}

	private void addButtons() {
		//TODO
		Image startImage = new Image(getClass().getClassLoader().getResourceAsStream("start.png"));
		Button startButton = new Button();
		startButton.setGraphic(new ImageView(startImage));
		
		Image stepImage = new Image(getClass().getClassLoader().getResourceAsStream("step.png"));
		Button stepButton = new Button();
		stepButton.setGraphic(new ImageView(stepImage));
		
		buttons = new ArrayList<Button>(Arrays.asList(startButton, stepButton));
	}
	
	private void placeButtons() {
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			button.setLayoutX(buttonOffset);
			button.setLayoutY(buttonOffset + i*buttonPadding);
			myRoot.getChildren().add(button);
		}
	}
	
	private void addTitle() {
		//TODO
	}

	private void addSlider() {
		//TODO
	}

	public void addGridPane() { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Rectangle rect = new Rectangle();
				rect.setWidth(cellSize);
				rect.setHeight(cellSize);
				rect.setFill(Color.WHITE);
				//grid.add(rect, col, row);
				grid.getChildren().add(rect);
			}
		}
		grid.setLayoutX(WIDTH - grid.getBoundsInLocal().getWidth());
		grid.setLayoutY(HEIGHT - grid.getBoundsInLocal().getWidth());
		myRoot.getChildren().add(grid);
	}
	
	public void closeSimulation() {
		myStage.close();
		windowOpen = false;
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
