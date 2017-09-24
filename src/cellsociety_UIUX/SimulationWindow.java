package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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

	protected boolean running;
	protected Button startButton = new Button();
	protected Button stepButton = new Button();

	protected int numCells = 50;
	protected int cellSize = 10;

	protected List<Button> buttons;
	protected int offset = 50;
	protected int padding = 100;

	Slider speed = new Slider();
	
	protected GridPane grid = new GridPane();
	protected ArrayList<Color> cellColors;

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
		//TODO multiply seconddelay by amount sound on speed slider
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
		addSlider();
		addTitle();
		addGridPane();
		throwErrors();
	}

	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX();
		HEIGHT = dimensions.getMaxY();
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
		myStage.setMaximized(true);
	}

	private void addButtons() {
		//TODO
		Image startImage = new Image(getClass().getClassLoader().getResourceAsStream("start.png"));
		startButton.setGraphic(new ImageView(startImage));

		Image stepImage = new Image(getClass().getClassLoader().getResourceAsStream("step.png"));
		stepButton.setGraphic(new ImageView(stepImage));

		buttons = new ArrayList<Button>(Arrays.asList(startButton, stepButton));
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			button.setLayoutX(offset);
			button.setLayoutY(offset + i*padding);
			myRoot.getChildren().add(button);
		}
	}

	private void addTitle() {
		//do nothing
	}

	private void addSlider() {//http://docs.oracle.com/javafx/2/ui_controls/slider.htm
		speed.setMin(0);
		speed.setMax(5);
		speed.setValue(1);
		speed.setShowTickLabels(true);
		speed.setShowTickMarks(true);
		speed.setMajorTickUnit(1);
		speed.setBlockIncrement(1);
		speed.setLayoutX(offset);
		speed.setLayoutY(offset + buttons.size()*padding);
		myRoot.getChildren().add(speed);
	}

	public void addGridPane() { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		/*grid.getStyleClass().add("game-grid");
		grid.setGridLinesVisible(true);*/
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Rectangle rect = new Rectangle();
				rect.setWidth(cellSize);
				rect.setHeight(cellSize);
				if ((row+col) % 2 == 1) {
					rect.setFill(Color.WHITE);
				}
				else {
					rect.setFill(Color.BLUE);
				}
				GridPane.setRowIndex(rect, row);
				GridPane.setColumnIndex(rect, col);
				//grid.add(rect, col, row);
				grid.getChildren().addAll(rect);
			}
		}
		grid.setLayoutX(WIDTH - numCells*cellSize - offset);
		grid.setLayoutY(offset);
		myRoot.getChildren().add(grid);
	}
	
	// updates grid with cellColors array list data
	public GridPane updateGridPane(GridPane grid) {
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Rectangle rect = new Rectangle();
				rect.setWidth(cellSize);
				rect.setHeight(cellSize);
				int cellNum = row + col;
				rect.setFill(cellColors.get(cellNum));
				GridPane.setRowIndex(rect, row);
				GridPane.setColumnIndex(rect, col);
				//grid.add(rect, col, row);
				grid.getChildren().addAll(rect);
			}
		}
		return grid;
		
	}
	
	// pass in currentCells array list and get array list of colors to fill grid
	private ArrayList<Color> getCellColors(ArrayList<Cell> cellStatuses) {
		for (int i = 0; i < cellStatuses.size(); i++) {
			cellColors.add(cellStatuses.get(i).getColor());
		}
		return cellColors;
	}
	

	public void throwErrors() {
		double gridSize = numCells*cellSize;
		//if (gridSize > WIDTH || gridSize > HEIGHT) {
		if (grid.getBoundsInParent().getMinX() < offset + buttons.get(0).getBoundsInLocal().getWidth() || grid.getBoundsInParent().getMinY() + gridSize > HEIGHT) {
			System.out.println("ERROR: grid created is too big, make number of cells in grid smaller or decrease the cell size");			
		}
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