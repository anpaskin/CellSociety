package cellsociety_UIUX;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	
	private Button run, step;
	
	public GameOfLifeWindow() {
		super();
		
	}
	
	/**
	 * Updates the cells for each SimulationWindow
	 */
	public void step(double elapsedTime) {
		// do nothing
		if (windowOpen) {
			if (simulationRunning) {
				
			}
			else {
				
			}
		}
	}


}
