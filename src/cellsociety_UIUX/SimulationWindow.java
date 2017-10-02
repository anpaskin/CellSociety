package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Cells.Cell;
import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Segregation;
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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;


/**
 * 
 * @author Kelly Zhang
 *
 */
public abstract class SimulationWindow extends Window {

	private static final int MIN_SLIDER_WIDTH = 180;
	protected static final String PLAY_PNG = "play.png";
	private static final String PAUSE_PNG = "pause.png";
	protected static final String RESTART_PNG = "restart.png";
	private static final String STEP_PNG = "step.png";
	private static String shape;
	
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
	protected double padding = 75;

	protected Slider speed = new Slider();;
	protected double simSpeed;

	protected GridPane grid;
	private GridDisplay gridDisplay;

	protected CellManager simType;
	private Text errorText = new Text();

	public SimulationWindow(Stage s, CellManager sim) {
		super(s);
		setupScene();
		simType = sim;
		grid = new GridPane();
		setRowSize(simType);
		System.out.println("size of grid: " + numCells);
		cellSize = (int) (HEIGHT - offset)/numCells;
		gridDisplay = new GridDisplay(numCells, cellSize, shape);
	}
	
	
	//FOR SETUP ****************************************
	@Override
	public void setupScene() {
		setupSceneDimensions();
		addButtons();
		addSpeedSlider();
		addText();
		throwErrors();
	}

	public void setupSceneDimensions() {
		Rectangle2D dimensions = Screen.getPrimary().getVisualBounds();
		WIDTH = dimensions.getMaxX()*twothirds;
		HEIGHT = dimensions.getMaxY()*twothirds;
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
	
	public static void setCellShape(String cellShape) {
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
	
	private void addText() {
		//do nothing
//		TODO what does this even do rn???
		errorText.setText("no error");
		errorText.setLayoutX(WIDTH - offset - errorText.getBoundsInLocal().getWidth());
		errorText.setLayoutY(HEIGHT - offset);
		myRoot.getChildren().add(errorText);
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
	
	protected Slider addExtraSlider(Slider mySlider, double min, double max, double setValue, double ticks, double blocks) {
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
		return mySlider;
	}
	
	
	// FOR INTERACTIONS ****************************************
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

	private void sliderDrag() {
		List<Slider> extraSliders = getExtraSliders();
		for (int i = 0; i < extraSliders.size(); i++) {
			Slider extraSlider = extraSliders.get(i);
			extraSlider.setOnMouseReleased(e -> {
				updateExtra(extraSlider);
			});
		}
	}
	
	protected void updateExtra(Slider mySlider) {
		//do nothing
	}
	
	protected List<Slider> getExtraSliders() {
		//do nothing;
		return null;
	}
	
	
	// FOR UPDATING GAME LOOP (STEP) **********************
	/**
	 * Updates the cells for each SimulationWindow
	 * @param simType 
	 */
	@Override
	public void step() {
		buttonClick();
		sliderDrag();
		if (running) {
			simType.updateCurrentCells();
			displayGrid(simType.getCurrentCells());
		}
		if (stepping) {
			running = false;
			simType.updateCurrentCells();
			displayGrid(simType.getCurrentCells());
			stepping = false;
		}
		throwErrors();
	}

	protected void resetGameLoop(double newSpeed) {
		animation.stop();
		gameLoop(simType, newSpeed);
	}
 	
	public void displayGrid(List<Cell> currentCellStatuses) {
		gridDisplay.updateGridDisplay(currentCellStatuses, grid);
		if (!myRoot.getChildren().contains(grid)) {
			myRoot.getChildren().add(grid);
		}
	}
	
	public void stopRunning() {
		running = false;
	}

	public void throwErrors() {
		//TODO do more than just print error in console... need to handle
		if (!errorText.getText().equals("no error")) {
			running = false;
			playButton.setGraphic(getImageView(PLAY_PNG));
			System.out.println("there is an error");
		}
	}
	
	public void setErrorText(String error) {
		errorText.setText(error);
	}
}