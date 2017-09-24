package cellsociety_team04;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;

import javafx.application.Application;
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
	private Stage simulationStage;
	private Window menu = new MenuWindow();
	private SimulationWindow segregation = new SegregationWindow();
	private SimulationWindow wator = new WatorWindow();
	private SimulationWindow fire = new FireWindow();
	private SimulationWindow gameoflife = new GameOfLifeWindow();

	private CellManager simulation;


	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(MENUTITLE);

		menuStage.setScene(menu.getScene());
		menuStage.show();

		setupSimulation();
		//NOTE: xml info is parsed and read here to create new simulation
		XMLParser parser = new XMLParser();
		parser.chooseFile(stage);
		simulation = parser.getSimulation();

		runSimulation();
		simulationStage.show();
	}


	private void setupSimulation() {
		simulationStage = new Stage();
		//TODO use title from xml file... 
		simulationStage.setTitle(SIMULATIONTITLE);

		if (simulation instanceof Segregation) {
			segregation.setWindowOpen(true);
		}
/*		else if (simulation instanceof Wator) {
			wator.setWindowOpen(true);
		}*/
		else if (simulation instanceof Fire) {
			fire.setWindowOpen(true);
		}
		else if (simulation instanceof GameOfLife) {
			gameoflife.setWindowOpen(true);
		}

	}

	private void runSimulation() {
		if (segregation.getWindowOpen()) {
			simulationStage.setScene(segregation.getScene());
		}
		else if (wator.getWindowOpen()) {
			simulationStage.setScene(wator.getScene());
		}
		else if (fire.getWindowOpen()) {
			simulationStage.setScene(fire.getScene());
		}
		else if (gameoflife.getWindowOpen()) {
			simulationStage.setScene(gameoflife.getScene());
		}
	}
	 
	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
