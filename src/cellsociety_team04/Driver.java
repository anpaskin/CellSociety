package cellsociety_team04;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
	private SimulationWindow segregation;
	private SimulationWindow wator;
	private SimulationWindow fire;
	private SimulationWindow gameoflife;
	
	protected double FRAMES_PER_SECOND = 60.0;
	protected double MILLISECOND_DELAY = 10000.0 / FRAMES_PER_SECOND;

	public CellManager simulation;
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
		
		menuLoop(simulation);
		
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
			simulation = ((MenuWindow)menu).getSimChoice();
			setupSimulation();
			runSimulation();
			simulationStage.show();
		}
	}

	public void setupSimulation() {
		//TODO use title from xml file... 
		simulationStage.setTitle(SIMULATIONTITLE);

		if (simulation instanceof Segregation) {
			segregation = new SegregationWindow(simulationStage, simulation);
			segregation.setWindowOpen(true);
			segregation.userInteraction();
			segregation.setRowSize(simulation);
			((Segregation) simulation).initializeCurrentCells();
			segregation.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(segregation.getScene());
			System.out.println("segregation");
			//System.out.println(segregation.getCellColors());
		}
		else if (simulation instanceof Fire) {
			fire = new FireWindow(simulationStage, simulation);
			fire.setWindowOpen(true);
			fire.userInteraction();
			fire.setRowSize(simulation);
			// TODO:
			((Fire) simulation).initializeCurrentCells();
			fire.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(fire.getScene());
			System.out.println("fire");
		}
		else if (simulation instanceof GameOfLife) {
			gameoflife = new GameOfLifeWindow(simulationStage, simulation);
			gameoflife.setWindowOpen(true);
			gameoflife.userInteraction();
			gameoflife.setRowSize(simulation);
			((GameOfLife) simulation).initializeCurrentCells();
			gameoflife.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(gameoflife.getScene());
			System.out.println("gameoflife");
		}
		else if (simulation instanceof WaTor) {
			wator = new WatorWindow(simulationStage, simulation);
			wator.setWindowOpen(true);
			wator.userInteraction();
			wator.setRowSize(simulation);
			((WaTor) simulation).initializeCurrentCells();
			wator.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(wator.getScene());
			System.out.println("wator");
		}
	}

	public void runSimulation() {
		if (simulation instanceof Segregation && segregation.getWindowOpen()) {
			segregation.gameLoop(simulation);
		}
		else if (simulation instanceof Fire && fire.getWindowOpen()) {
			fire.gameLoop(simulation);
		}
		else if (simulation instanceof WaTor && wator.getWindowOpen()) {
			wator.gameLoop(simulation);
		}
		else if (simulation instanceof GameOfLife && gameoflife.getWindowOpen()) {
			gameoflife.gameLoop(simulation);
		}
	}

	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
