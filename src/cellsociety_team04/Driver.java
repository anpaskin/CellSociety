package cellsociety_team04;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * 
 * Driver for running the code
 * 
 * @author kelly
 * @author Dara
 *
 */

public class Driver {

	private static final String MENUTITLE = "Cell Society";
	private Stage menuStage;
	private List<Stage> simStages;
	private Window menuWindow;
	private List<SimulationWindow> simWindows;

	protected double FRAMES_PER_SECOND = 60.0;
	protected double MILLISECOND_DELAY = 10000.0 / FRAMES_PER_SECOND;

	private CellManager simCellManager;
	private Timeline animation;


	public Driver(Stage stage) {
		setupDriver(stage);
	}

	/**
	 * Displays the menu in the window
	 */
	public void setupDriver(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(MENUTITLE);
		menuWindow = new MenuWindow(menuStage);
		menuStage.setScene(menuWindow.getScene());
		menuStage.show();

		simWindows = new ArrayList<>();
		simStages = new ArrayList<>();

		//menuWindow.gameLoop(simCellManager, default_SPEED);
		menuStep();
	}

	protected void menuStep() {
		//animation.play();
		((MenuWindow)menuWindow).chooseSim();
		if(((MenuWindow)menuWindow).getSimChoice() != null) {
			animation.stop();
			simCellManager = ((MenuWindow)menuWindow).getSimChoice();
			simStages.add(new Stage());
			System.out.println(simStages);
			determineSim(simStages.size()-1);
			runSimulation(simStages.size()-1);
			simStages.get(simStages.size()-1).show();
			((MenuWindow) menuWindow).resetMenu();
			animation.playFromStart();
			System.out.println("playing from start");
		}
	}

	public void determineSim(int simNum) {
		//TODO use title from xml file... 
		if (simCellManager instanceof Segregation) {
			simWindows.add(new SegregationWindow(simStages.get(simNum), simCellManager));
			setupSim(simNum);
			System.out.println("segregation");
		}
		else if (simCellManager instanceof Fire) {
			simWindows.add(new FireWindow(simStages.get(simNum), simCellManager));
			setupSim(simNum);
			System.out.println("fire");
		}
		else if (simCellManager instanceof GameOfLife) {
			simWindows.add(new GameOfLifeWindow(simStages.get(simNum), simCellManager));
			setupSim(simNum);
			System.out.println("gameoflife");
		}
		else if (simCellManager instanceof WaTor) {
			simWindows.add(new WatorWindow(simStages.get(simNum), simCellManager));
			setupSim(simNum);
			System.out.println("wator");
		}
		System.out.println(simWindows);
	}

	private void setupSim(int simNum) {
		simWindows.get(simNum).buttonClick();
		simWindows.get(simNum).setRowSize(simCellManager);
		simCellManager.initializeCurrentCells();
		simWindows.get(simNum).displayGridPane(simCellManager.getCurrentCells());
		simStages.get(simNum).setScene(simWindows.get(simNum).getScene());
		simStages.get(simNum).setTitle(simCellManager.getClass().toString());
		simStages.get(simNum).setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out.println("Stage is closing");
			}
		});        

	}

	public void runSimulation(int simNum) {
		for (int i = 0; i < simWindows.size(); i++) {
			((SimulationWindow) simWindows.get(simNum)).gameLoop(simCellManager, MILLISECOND_DELAY);
			System.out.println(i+1);
		}
	}

	/**
	 * Start of the program
	 */
//	public static void main(String[] args) {
//		launch(args);
//	}

}
