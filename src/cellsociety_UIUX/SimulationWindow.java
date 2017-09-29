package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.glass.events.MouseEvent;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

public abstract class SimulationWindow extends Window {

	private static final String PLAY_PNG = "play.png";
	private static final String PAUSE_PNG = "pause.png";
	private static final String STEP_PNG = "step.png";
	
	protected double WIDTH;
	protected double HEIGHT;

	protected boolean running = false;
	protected boolean stepping = false;
	protected Button playButton;
	protected Button stepButton;
	
	protected int numCells;
	protected int cellSize = 50;

	protected List<Button> buttons;
	protected double offset = 50;
	protected double padding = 100;

	Slider speed;
	private double simSpeed;
	
	protected GridPane grid = new GridPane();
	protected ArrayList<Color> cellColors = new ArrayList<>();

	private CellManager simType;
	private List<Integer> numSimTypes;

	public SimulationWindow(Stage s, CellManager sim) {
		super(s);
		setupScene();
		simType = sim;
	}

	public void buttonClick() {
		// TODO Auto-generated method stub
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				running = !running;
				if (running) {
					playButton.setGraphic(getImageView(PAUSE_PNG));
				}
				else {
					playButton.setGraphic(getImageView(PLAY_PNG));
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
	
	private void updateSimSpeed() {
		simSpeed = (double) (1 / speed.getValue()) * 60;
		resetGameLoop(simSpeed);
	}
	
	/**
	 * Updates the cells for each SimulationWindow
	 * @param simType 
	 */
	@Override
	public void step() {
		buttonClick();
		if (running) {
			//simType.setNextCellStatuses();
			simType.updateCurrentCells();
			displayGridPane(simType.getCurrentCells());
		}
		if (stepping) {
			running = false;
			//simType.setNextCellStatuses();
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
		addButtons();
		addSlider();
		addTitle();
		//throwErrors();
	}

	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX();
		HEIGHT = dimensions.getMaxY();
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}
	
	public void setRowSize(CellManager c) {
		numCells = (int) Math.sqrt(c.getSize());
	}

	private void addButtons() {
		//TODO
		playButton = new Button();
		playButton.setGraphic(getImageView(PLAY_PNG));
		setControlButtonLayout(playButton, offset, offset);
		
		stepButton = new Button();
		stepButton.setGraphic(getImageView(STEP_PNG));
		setControlButtonLayout(stepButton, offset, offset + padding);

		buttons = new ArrayList<Button>(Arrays.asList(playButton, stepButton));
		myRoot.getChildren().addAll(buttons);
	}

	private ImageView getImageView(String imageName) {
		Image buttonImage = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		return new ImageView(buttonImage);
	}
	
	private void setControlButtonLayout(Button button, Double x, Double y) {
		button.setLayoutX(x);
		button.setLayoutY(y);
	}
	
	
	private void addTitle() {
		//do nothing
	}

	private void addSlider() {//http://docs.oracle.com/javafx/2/ui_controls/slider.htm
		speed = new Slider();
		speed.setMin(1);
		speed.setMax(3);
		speed.setValue(1);
		labelSpeedSlider(speed);
		speed.setMinWidth(180);
		speed.setShowTickLabels(true);
		speed.setShowTickMarks(true);
		speed.setMajorTickUnit(1);
		speed.setBlockIncrement(1);
		speed.setLayoutX(offset);
		speed.setLayoutY(offset + buttons.size()*padding);
		myRoot.getChildren().add(speed);
	}
	
	private void labelSpeedSlider(Slider speed) {
		speed.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 1.5) return "Slow";
                if (n <= 2.5) return "Medium";
                return "Fast";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Slow":
                        return 0d;
                    case "Medium":
                        return 1d;
                    case "Fast":
                        return 2d;
                    default:
                        return 2d;
                }
            }
        });
	}
 	
	public void displayGridPane(List<Cell> currentCells) { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		getCellColors(currentCells);
		grid.getChildren().clear();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Polygon polygon = new Polygon();
				polygon.getPoints().addAll(new Double[] {
						0.0, 15.0,
						10.0, 0.0,
						20.0, 0.0,
						30.0, 15.0,
						20.0, 30.0,
						10.0, 30.0
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
	private void getCellColors(List<Cell> cellStatuses) {
		cellColors.clear();
		for (int i = 0; i < cellStatuses.size(); i++) {
			cellColors.add(cellStatuses.get(i).getColor());
		}
	}
	

	/*public void throwErrors() {
		//TODO do more than just print error in console... need to handle
		double gridSize = numCells*cellSize;
		//if (gridSize > WIDTH || gridSize > HEIGHT) {
		if (grid.getBoundsInParent().getMinX() < offset + buttons.get(0).getBoundsInLocal().getWidth() || grid.getBoundsInParent().getMinY() + gridSize > HEIGHT) {
			System.out.println("ERROR: grid created is too big, make number of cells in grid smaller or decrease the cell size");			
		}
	}*/

	public ArrayList<Color> getCellColors() {
		return cellColors;
	}
}
