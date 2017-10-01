package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	private Slider aliveRatio = new Slider();
	
	public GameOfLifeWindow(Stage s, CellManager sim) {
		super(s, sim);
		controls.add(aliveRatio);
		addExtraSliders(aliveRatio, 0.0, 1.0, 0.5, 0.25, 0.5);
		updateExtras(aliveRatio);
	}
	
	protected void sliderDrag() {
		aliveRatio.setOnMouseReleased(e -> {
			running = false;
			playButton.setGraphic(getImageView(RESTART_PNG));
			updateExtras(aliveRatio);
			System.out.println("press reset");
		});
	}
	
	protected void updateExtras(Slider mySlider) {
		//simType = new GameOfLife(mySlider.getValue());
		System.out.println(mySlider.getValue());
	}
}
