package cellsociety_team04;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
	private Window menu = new MenuWindow(menuStage);
	private SimulationWindow segregation = new SegregationWindow(simulationStage);
	private SimulationWindow wator = new WatorWindow(simulationStage);
	private SimulationWindow fire = new FireWindow(simulationStage);
	private SimulationWindow gameoflife = new GameOfLifeWindow(simulationStage);

	private CellManager simulation;


	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(MENUTITLE);

		menu.setupSceneDimensions();
		menuStage.setScene(menu.getScene());
		menuStage.show();

		//NOTE: xml info is parsed and read here to create new simulation
		// add button to menu for uploading a new file... if user clicks then prompt file choice
		XMLParser parser = new XMLParser();
		parser.chooseFile(stage);
		simulation = parser.getSimulation();

		setupSimulation();
		runSimulation();
		simulationStage.show();
	}

	private void setupSimulation() {
		//TODO use title from xml file... 
		simulationStage.setTitle(SIMULATIONTITLE);

		if (simulation instanceof Segregation) {
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
			gameoflife.setWindowOpen(true);
			gameoflife.userInteraction();
			gameoflife.setRowSize(simulation);
			((GameOfLife) simulation).initializeCurrentCells();
			gameoflife.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(gameoflife.getScene());
			System.out.println("gameoflife");
		}
		else if (simulation instanceof WaTor) {
			wator.setWindowOpen(true);
			wator.userInteraction();
			wator.setRowSize(simulation);
			((WaTor) simulation).initializeCurrentCells();
			wator.displayGridPane(simulation.getCurrentCells());
			simulationStage.setScene(wator.getScene());
			System.out.println("wator");
		}
	}

	private void runSimulation() {
		if (segregation.getWindowOpen()) {
			segregation.gameLoop(simulation);
		}
		else if (fire.getWindowOpen()) {
			fire.gameLoop(simulation);
		}
		else if (wator.getWindowOpen()) {
			wator.gameLoop(simulation);
		}
		else if (gameoflife.getWindowOpen()) {
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
