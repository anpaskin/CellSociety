package cellsociety_team04;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cellsociety_Simulations.*;
import cellsociety_UIUX.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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

	public CellManager simCellManager;

	public Driver(Stage stage) {
		setup(stage);
	}

	/**
	 * Displays the menu in the window
	 */
	public void setup(Stage stage) {
		simWindows = new ArrayList<>();
		simStages = new ArrayList<>();
	}
	
	public void determineSim(CellManager simChoice) {
		simStages.add(new Stage());
		int simNum = simStages.size()-1;
		simCellManager = simChoice;
		if (simCellManager instanceof Segregation) {
			simWindows.add(new SegregationWindow(simStages.get(simNum), simCellManager));
			System.out.println("segregation");
		}
		else if (simCellManager instanceof Fire) {
			simWindows.add(new FireWindow(simStages.get(simNum), simCellManager));
			System.out.println("fire");
		}
		else if (simCellManager instanceof GameOfLife) {
			simWindows.add(new GameOfLifeWindow(simStages.get(simNum), simCellManager));
			System.out.println("gameoflife");
		}
		else if (simCellManager instanceof WaTor) {
			simWindows.add(new WatorWindow(simStages.get(simNum), simCellManager));
			System.out.println("wator");
		}
		else {
			//TODO throw exception
			return;
		}
		setupSim(simNum);
		System.out.println(simWindows);
	}

	private void setupSim(int simNum) {
		simWindows.get(simNum).buttonClick();
		simWindows.get(simNum).setRowSize(simCellManager);
		simCellManager.initializeCurrentCells();
		simWindows.get(simNum).displayGrid(simCellManager.getCurrentCells());
		simStages.get(simNum).setScene(simWindows.get(simNum).getScene());
		simStages.get(simNum).setTitle(simCellManager.getClass().toString());
		simStages.get(simNum).setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out.println("Stage is closing");
			}
		});  
		simStages.get(simNum).show();
	}
	public void runSimulation() {
		int simNum = simStages.size()-1;
		simWindows.get(simNum).gameLoop(simCellManager, MILLISECOND_DELAY);
		System.out.println("running sim");
	}
}