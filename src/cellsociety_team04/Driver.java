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
	private SimulationWindow segregation;
	private SimulationWindow wator;
	private SimulationWindow fire;
	private SimulationWindow gameoflife;

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
		/*XMLParser parser = new XMLParser();
		parser.chooseFile(stage);
		simulation = parser.getSimulation();
		*/
		
		//((MenuWindow) menu).newSimulation(menuStage, parser);
		if (((MenuWindow) menu).getNewSim()) {
			System.out.println("getting new sim");
			XMLParser parser = new XMLParser();
			parser.chooseFile(menuStage);
			simulation = parser.getSimulation();
			setupSimulation();
			runSimulation();
			simulationStage.show();
			((MenuWindow) menu).setNewSim(false);
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
