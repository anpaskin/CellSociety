package cellsociety_UIUX;

import cellsociety_Simulations.CellManager;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	private Slider aliveRatio;
	
	public GameOfLifeWindow(Stage s, CellManager sim) {
		super(s, sim);
//		addSlider(aliveRatio);
	}
}
