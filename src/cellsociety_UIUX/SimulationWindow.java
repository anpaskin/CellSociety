package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.glass.events.MouseEvent;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class SimulationWindow extends Window {

	protected double WIDTH;
	protected double HEIGHT;

	//protected boolean started = false;
	protected boolean running = false;
	protected boolean stepping = false;
	protected Button playButton = new Button();
	protected Button stepButton = new Button();
	protected ImageView playImageView, pauseImageView;
	
	protected int numCells;
	protected int cellSize = 50;

	protected List<Button> buttons;
	protected int offset = 50;
	protected int padding = 100;

	Slider speed = new Slider();
	private double simSpeed = 10000;
	//private boolean speedChange = false;
	
	protected GridPane grid = new GridPane();
	protected ArrayList<Color> cellColors = new ArrayList<>();

	protected boolean windowOpen = false;
	protected boolean simulationRunning = false;

	private CellManager simType;

	public SimulationWindow(Stage s, CellManager sim) {
		super(s);
		setupScene();
		simType = sim;
		//setRowSize();
	}

	public void userInteraction() {
		// TODO Auto-generated method stub
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				//simSpeed = speed.valueProperty().doubleValue()*5;
				running = !running;
				if (running) {
					playButton.setGraphic(pauseImageView);
				}
				else {
					playButton.setGraphic(playImageView);
				}
			}
		});
		
		stepButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stepping = true;
			}
		});
		
		speed.setOnMouseReleased(e -> {
			updateSimSpeed();
		});

	}
	
	private double getSimSpeed() {
		return simSpeed;
	}
	
	private void updateSimSpeed() {
		simSpeed = (double) Math.pow(speed.getValue(), -2) * 100;
		resetGameLoop(simSpeed);
	}
	
	/**
	 * Updates the cells for each SimulationWindow
	 * @param simType 
	 */
	@Override
	protected void step() {
		userInteraction();
//		if (speedChange) {
//			resetGameLoop(getSimSpeed());
//			speedChange = false;
//		}
		if (running) {
			simType.setNextCellStatuses();
			simType.updateCurrentCells();
			displayGridPane(simType.getCurrentCells());
		}
		if (stepping) {
			running = false;
			simType.setNextCellStatuses();
			simType.updateCurrentCells();
			displayGridPane(simType.getCurrentCells());
			stepping = false;
		}
	}

	private void resetGameLoop(double newSpeed) {
		animation.stop();
		gameLoop(simType, newSpeed);
	}

	
	@Override
	public void setupScene() {
		setupSceneDimensions();
		buttons = new ArrayList<Button>();
		addButtons();
		addSlider();
		addTitle();
		//displayGridPane();
		throwErrors();
	}

	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX();
		HEIGHT = dimensions.getMaxY();
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
		//myStage.setMaximized(true);
	}
	
	public void setRowSize(CellManager c) {
		// do nothing
		numCells = (int) Math.sqrt(c.getSize());
	}

	private void addButtons() {
		//TODO
		Image playImage = new Image(getClass().getClassLoader().getResourceAsStream("play.png"));
		playImageView = new ImageView(playImage);
		playButton.setGraphic(playImageView);

		Image stepImage = new Image(getClass().getClassLoader().getResourceAsStream("step.png"));
		stepButton.setGraphic(new ImageView(stepImage));
		
		Image pauseImage = new Image(getClass().getClassLoader().getResourceAsStream("pause.png"));
		pauseImageView = new ImageView(pauseImage);
		
		buttons = new ArrayList<Button>(Arrays.asList(playButton, stepButton));
		
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
		speed.setMin(1);
		speed.setMax(3);
		speed.setValue(1);
		speed.setShowTickLabels(true);
		speed.setShowTickMarks(true);
		speed.setMajorTickUnit(1);
		speed.setBlockIncrement(1);
		speed.setLayoutX(offset);
		speed.setLayoutY(offset + buttons.size()*padding);
		myRoot.getChildren().add(speed);
	}

	public void displayGridPane(ArrayList<Cell> currentCells) { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		getCellColors(currentCells);
		grid.getChildren().clear();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Polygon polygon = new Polygon();
				polygon.getPoints().addAll(new Double[] {
						0.0, 7.5,
						5.0, 0.0,
						10.0, 0.0,
						15.0, 7.5,
						10.0, 15.0,
						5.0, 15.0
				});
//				Polygon polygon = new Polygon();
//				if (row % 2 == 1) {
//					if (col % 2 == 0) {
//						polygon.getPoints().addAll(new Double[]{
//							    0.0, 0.0,
//							    10.0, 20.0,
//							    20.0, 0.0 });
//					} else {
//						polygon.getPoints().addAll(new Double[]{
//							    0.0, 20.0,
//							    10.0, 0.0,
//							    20.0, 20.0 });
//					}
//				} else {
//					if (col % 2 == 1) {
//						polygon.getPoints().addAll(new Double[]{
//							    0.0, 0.0,
//							    10.0, 20.0,
//							    20.0, 0.0 });
//					} else {
//						polygon.getPoints().addAll(new Double[]{
//							    0.0, 20.0,
//							    10.0, 0.0,
//							    20.0, 20.0 });
//					}
//				}
				int cellNum = row*numCells + col;
				polygon.setFill(cellColors.get(cellNum));
				polygon.setStroke(Color.WHITE);
				GridPane.setRowIndex(polygon, row);
				GridPane.setColumnIndex(polygon, col);
				grid.getChildren().addAll(polygon);
				/*
				 * RECTANGLE
				 */
				//Rectangle rect = new Rectangle();
				//rect.setWidth(cellSize);
				//rect.setHeight(cellSize);
				//rect.setFill(cellColors.get(cellNum));
				//GridPane.setRowIndex(rect, row);
				//GridPane.setColumnIndex(rect, col);
				//grid.getChildren().addAll(rect);
			}
		}
		grid.setLayoutX(WIDTH - numCells*cellSize - offset);
		grid.setLayoutY(offset);
		if (!myRoot.getChildren().contains(grid)) {
			myRoot.getChildren().add(grid);
		}
	}
	
	// updates grid with cellColors array list data
//	public GridPane updateGridPane(GridPane grid) {
//		for (int row = 0; row < numCells; row++) {
//			for (int col = 0; col < numCells; col++) {
//				Rectangle rect = new Rectangle();
//				rect.setWidth(cellSize);
//				rect.setHeight(cellSize);
//				int cellNum = row*numCells + col;
//				rect.setFill(cellColors.get(cellNum));
//				GridPane.setRowIndex(rect, row);
//				GridPane.setColumnIndex(rect, col);
//				grid.getChildren().addAll(rect);
//			}
//		}
//		return grid;
//		
//	}
	
	// pass in currentCells array list and get array list of colors to fill grid
	private void getCellColors(ArrayList<Cell> cellStatuses) {
		cellColors.clear();
		for (int i = 0; i < cellStatuses.size(); i++) {
			cellColors.add(cellStatuses.get(i).getColor());
		}
	}
	

	public void throwErrors() {
		//TODO do more than just print error in console... need to handle
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

	public ArrayList<Color> getCellColors() {
		return cellColors;
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
