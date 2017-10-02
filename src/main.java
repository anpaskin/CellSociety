import java.util.ArrayList;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_UIUX.MenuWindow;
import cellsociety_UIUX.SimulationWindow;
import cellsociety_UIUX.Window;
import cellsociety_team04.*;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * 
 * MAIN METHOD and sets up first menu window stage, then goes everywhere else to run
 * 
 * @author kelly
 *
 */

public class main extends Application {

	private static final String MENUTITLE = "Cell Society";

	private Stage menuStage;
	private Window menuWindow;

	protected double FRAMES_PER_SECOND = 60.0;
	protected double MILLISECOND_DELAY = 10000.0 / FRAMES_PER_SECOND;

	public CellManager simCellManager;

	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		//TODO move this stuff into MenuWindow?
		menuStage = stage;
		menuStage.setTitle(MENUTITLE);
		menuWindow = new MenuWindow(menuStage);
		menuStage.setScene(menuWindow.getScene());
		menuStage.show();

		//menuLoop(simCellManager);
		menuWindow.gameLoop(simCellManager, MILLISECOND_DELAY);
	}

	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
