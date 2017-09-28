package cellsociety_UIUX;

import cellsociety_Simulations.CellManager;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	private Slider aliveRatio;
	
	public GameOfLifeWindow(Stage s, CellManager sim) {
		super(s, sim);
	}
	
	public void addSlider() {
		aliveRatio.setMin(0);
		aliveRatio.setMax(1);
		aliveRatio.setValue(0.5);
		aliveRatio.setShowTickLabels(true);
		aliveRatio.setShowTickMarks(true);
		aliveRatio.setMajorTickUnit(0.25);
		aliveRatio.setBlockIncrement(0.05);
		aliveRatio.setLayoutX(offset);
		aliveRatio.setLayoutY(HEIGHT - offset*2);
		myRoot.getChildren().add(aliveRatio);
	}

}
