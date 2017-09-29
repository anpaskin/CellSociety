package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class SimulationWindow extends Window {

	private static final String PLAY_PNG = "play.png";
	private static final String PAUSE_PNG = "pause.png";
	private static final String STEP_PNG = "step.png";
	
	private static final double half = 0.5;
	private static final double third = 0.33;
	private static final double twothirds = 0.66;
	private static final double one = 1.0;
	private static final double zero = 0.0;

	protected double WIDTH;
	protected double HEIGHT;

	protected boolean running = false;
	protected boolean stepping = false;
	protected Button playButton;
	protected Button stepButton;

	protected int numCells;
	protected int cellSize = 50;

	protected List<Node> buttons;
	protected double offset = 50;
	protected double padding = 100;

	Slider speed = new Slider();
	private double simSpeed = 10000;

	protected GridPane grid = new GridPane();
	protected ArrayList<Color> cellColors = new ArrayList<>();

	private CellManager simType;

	public SimulationWindow(Stage s, CellManager sim) {
		super(s);
		setupScene();
		simType = sim;
	}

	public void buttonClick() {
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
		simSpeed = (double) Math.pow(speed.getValue(), -2) * 100;
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
		WIDTH = dimensions.getMaxX()*twothirds;
		HEIGHT = dimensions.getMaxY()*twothirds;
		myStage.setX(dimensions.getMinX());
		myStage.setY(dimensions.getMinY());
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}

	public void setRowSize(CellManager c) {
		numCells = (int) Math.sqrt(c.getSize());
	}

	private void addButtons() {
		playButton = new Button();
		playButton.setGraphic(getImageView(PLAY_PNG));
		setControlButtonLayout(playButton, offset, offset);

		stepButton = new Button();
		stepButton.setGraphic(getImageView(STEP_PNG));
		setControlButtonLayout(stepButton, offset, offset + padding);

		buttons = new ArrayList<Node>(Arrays.asList(playButton, stepButton));
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

	public void displayGridPane(List<Cell> currentCells) { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
		getCellColors(currentCells);
		grid.getChildren().clear();
		for (int row = 0; row < numCells; row++) {
			for (int col = 0; col < numCells; col++) {
				Polygon polygon = new Polygon();
				
				//if hexagon
//				polygon.getPoints().addAll(new Double[] {
//						// if hexagon
//						zero, half*cellSize,
//						third*cellSize, zero,
//						twothirds*cellSize, zero,
//						one*cellSize, half*cellSize,
//						twothirds*cellSize, one*cellSize,
//						third*cellSize, one*cellSize
//				});
				
				// if triangle
//				if ((row + col)% 2 == 0) {
//					polygon.getPoints().addAll(new Double[]{
//							half*cellSize, zero,
//							zero, one*cellSize,
//							one*cellSize, one*cellSize
//					});
//				}
//				else {
//					polygon.getPoints().addAll(new Double[]{
//							zero, zero,
//							half*cellSize, one*cellSize,
//							one*cellSize, zero
//					});
//				}
				
				// if rectangle
				polygon.getPoints().addAll(new Double[]{
						zero, zero,
						one*cellSize, zero,
						one*cellSize, one*cellSize,
						zero, one*cellSize
				});
				
				int cellNum = row*numCells + col;
				polygon.setFill(cellColors.get(cellNum));
				polygon.setStroke(Color.WHITE);
				GridPane.setRowIndex(polygon, row);
				GridPane.setColumnIndex(polygon, col);
				grid.getChildren().addAll(polygon);
			}
		}
		grid.setLayoutX(WIDTH - numCells*cellSize - offset* half);
		grid.setLayoutY(offset*half);
		if (!myRoot.getChildren().contains(grid)) {
			myRoot.getChildren().add(grid);
		}
	}

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
