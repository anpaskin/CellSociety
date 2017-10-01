package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import cellsociety_team04.GridDisplay;
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
import javafx.util.StringConverter;

public abstract class SimulationWindow extends Window {

	private static final int MIN_SLIDER_WIDTH = 180;
	private static final String PLAY_PNG = "play.png";
	private static final String PAUSE_PNG = "pause.png";
	protected static final String RESET_PNG = "reset.png";
	private static final String STEP_PNG = "step.png";
	private String shape = "triangle";
	
	private static final double twothirds = 0.66;
	protected static double WIDTH;
	protected double HEIGHT;

	protected boolean running = false;
	protected boolean stepping = false;
	protected Button playButton;
	protected Button stepButton;

	protected int numCells;
	protected int cellSize;

	protected List<Node> controls;
	protected static double offset = 50;
	protected double padding = 100;

	protected Slider speed = new Slider();;
	private double simSpeed;

	protected GridPane grid;
	private GridDisplay gridDisplay;

	protected CellManager simType;

	public SimulationWindow(Stage s, CellManager sim) {
		super(s);
		setupScene();
		simType = sim;
		grid = new GridPane();
		setRowSize(simType);
		System.out.println(numCells);
		cellSize = (int) (HEIGHT - offset)/numCells;
		gridDisplay = new GridDisplay(numCells, cellSize, shape);
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
				playButton.setGraphic(getImageView(PLAY_PNG));
			}
		});

		speed.setOnMouseReleased(e -> {
			updateSimSpeed();
		});

	}

	private void updateSimSpeed() {
		simSpeed = (double) (1 / speed.getValue()) * 300;
		resetGameLoop(simSpeed);
	}

	protected void sliderDrag() {
		//do nothing
	}
	
	protected void updateExtras(Slider mySlider) {
		// do nothing
	}
	
	/**
	 * Updates the cells for each SimulationWindow
	 * @param simType 
	 */
	@Override
	public void step() {
		buttonClick();
		sliderDrag();
		if (running) {
			//simType.setNextCellStatuses();
			simType.updateCurrentCells();
			displayGrid(simType.getCurrentCells());
		}
		if (stepping) {
			running = false;
			//simType.setNextCellStatuses();
			simType.updateCurrentCells();
			displayGrid(simType.getCurrentCells());
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
		addSpeedSlider();
		addTitle();
		//throwErrors();
	}

	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX()*twothirds;
		HEIGHT = dimensions.getMaxY()*twothirds;
		//myStage.setX(dimensions.getMinX());
		//myStage.setY(dimensions.getMinY());
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}
	
	public static double getWidth() {
		return WIDTH;
	}
	
	public static double getOffset() {
		return offset;
	}

	public void setRowSize(CellManager c) {
		numCells = (int) Math.sqrt(c.getSize());
	}
	
	public void setCellShape(String cellShape) {
		shape = cellShape;
	}

	private void addButtons() {
		playButton = new Button();
		playButton.setGraphic(getImageView(PLAY_PNG));
		setControlLayout(playButton, offset, offset);

		stepButton = new Button();
		stepButton.setGraphic(getImageView(STEP_PNG));
		setControlLayout(stepButton, offset, offset + padding);

		controls = new ArrayList<Node>(Arrays.asList(playButton, stepButton));
		myRoot.getChildren().addAll(controls);
	}

	protected ImageView getImageView(String imageName) {
		Image buttonImage = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		return new ImageView(buttonImage);
	}

	private void setControlLayout(Node control, Double x, Double y) {
		control.setLayoutX(x);
		control.setLayoutY(y);
	}


	private void addTitle() {
		//do nothing
	}

	private void addSpeedSlider() {//http://docs.oracle.com/javafx/2/ui_controls/slider.htm
		speed.setMin(1);
		speed.setMax(3);
		speed.setValue(1);
		labelSpeedSlider(speed);
		speed.setMinWidth(MIN_SLIDER_WIDTH);
		speed.setShowTickLabels(true);
		speed.setShowTickMarks(true);
		speed.setMajorTickUnit(1);
		speed.setBlockIncrement(1);
		speed.setLayoutX(offset);
		speed.setLayoutY(offset + controls.size()*padding);
		myRoot.getChildren().add(speed);
	}
	
	protected void labelSpeedSlider(Slider mySlider) {
		mySlider.setLabelFormatter(new StringConverter<Double>() {
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
	
	protected void addExtraSliders(Slider mySlider, double min, double max, double setValue, double ticks, double blocks) {
		mySlider = new Slider();
		mySlider.setMin(min);
		mySlider.setMax(max);
		mySlider.setValue(setValue);
		mySlider.setMinWidth(MIN_SLIDER_WIDTH);
		mySlider.setShowTickLabels(true);
		mySlider.setShowTickMarks(true);
		mySlider.setMajorTickUnit(ticks);
		mySlider.setBlockIncrement(blocks);
		mySlider.setLayoutX(offset);
		mySlider.setLayoutY(offset + controls.size()*padding);
		myRoot.getChildren().add(mySlider);
	}
 	
	public void displayGrid(List<Cell> currentCellStatuses) {
		gridDisplay.updateGridDisplay(currentCellStatuses, grid);
		if (!myRoot.getChildren().contains(grid)) {
			myRoot.getChildren().add(grid);
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
}