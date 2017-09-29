package cellsociety_team04;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * Driver for running the code
 * 
 * @author kelly
 * @author Dara
 *
 */

public class Driver extends Application {

	private static final String MENUTITLE = "Cell Society";
	private static final String SIMULATIONTITLE = "SIMULATION";

	private Stage menuStage;
	private Stage simulationStage = new Stage();
	private Window menu;
	private SimulationWindow simWindow;
	
	protected double FRAMES_PER_SECOND = 60.0;
	protected double MILLISECOND_DELAY = 10000.0 / FRAMES_PER_SECOND;

	public CellManager simCellManager;
	private Timeline animation;


	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(MENUTITLE);
		menu = new MenuWindow(menuStage);
		menuStage.setScene(menu.getScene());
		menuStage.show();
		
		menuLoop(simCellManager);
		
	}
	
	public void menuLoop(CellManager simType) {
		// attach "game loop" to timeline to play it
				KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
						e -> menuStep());
				//TODO multiply seconddelay by amount sound on speed slider
				animation = new Timeline();
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.getKeyFrames().add(frame);
				animation.play();
	}
	
	protected void menuStep() {
		//System.out.println("Enter menu step");
		((MenuWindow)menu).chooseSim();
//				XMLParser parser = new XMLParser();
//				parser.chooseFile(menuStage);
//				simulation = parser.getSimulation();	
		if(((MenuWindow)menu).getSimChoice() != null) {
			animation.stop();
			simCellManager = ((MenuWindow)menu).getSimChoice();
			determineSim();
			runSimulation();
			simulationStage.show();
		}
	}

	public void determineSim() {
		//TODO use title from xml file... 
		simulationStage.setTitle(SIMULATIONTITLE);

		if (simCellManager instanceof Segregation) {
			simWindow = new SegregationWindow(simulationStage, simCellManager);
			setupSim();
			System.out.println("segregation");
		}
		else if (simCellManager instanceof Fire) {
			simWindow = new FireWindow(simulationStage, simCellManager);
			setupSim();
			System.out.println("fire");
		}
		else if (simCellManager instanceof GameOfLife) {
			simWindow = new GameOfLifeWindow(simulationStage, simCellManager);
			setupSim();
			System.out.println("gameoflife");
		}
		else if (simCellManager instanceof WaTor) {
			simWindow = new WatorWindow(simulationStage, simCellManager);
			setupSim();
			System.out.println("wator");
		}
	}

	private void setupSim() {
		simWindow.setWindowOpen(true);
		simWindow.userInteraction();
		simWindow.setRowSize(simCellManager);
		simCellManager.initializeCurrentCells();
		simWindow.displayGridPane(simCellManager.getCurrentCells());
		simulationStage.setScene(simWindow.getScene());
	}

	public void runSimulation() {
		simWindow.gameLoop(simCellManager, MILLISECOND_DELAY);
	}

	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
