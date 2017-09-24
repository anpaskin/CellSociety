package cellsociety_team04;

import cellsociety_Simulations.CellManager;
import cellsociety_UIUX.FireWindow;
import cellsociety_UIUX.GameOfLifeWindow;
import cellsociety_UIUX.MenuWindow;
import cellsociety_UIUX.SegregationWindow;
import cellsociety_UIUX.WatorWindow;
import cellsociety_UIUX.Window;

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

	private static final String TITLE = "Cell Society";

	private Stage menuStage;
	private Stage simulationStage;
	private Window menu = new MenuWindow();
	private Window segregation = new SegregationWindow();
	private Window wator = new WatorWindow();
	private Window fire = new FireWindow();
	private Window gameoflife = new GameOfLifeWindow();
	
	private CellManager simulation;


	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(TITLE);

		menuStage.setScene(menu.getScene());
		menuStage.show();
		
		//NOTE: xml info is parsed and read here to create new simulation
		XMLParser parser = new XMLParser();
		parser.chooseFile(stage);
		simulation = parser.getSimulation();

		setupSimulation();
		//runSimulation();
	}


	private void setupSimulation() {
		simulationStage = new Stage();
		//TODO use title from xml file... 
		simulationStage.setTitle("Simulation");
	}

	/*	private void runSimulation() {
		// TODO Auto-generated method stub
		if (segregation.getWindowOpen()) {

		}
	}
	 */
	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
