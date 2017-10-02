package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.GameOfLife;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	private Slider aliveRatio = new Slider();
	private List<Slider> extraSliders = new ArrayList<>(Arrays.asList(aliveRatio));
	
	
	public GameOfLifeWindow(Stage s, CellManager sim) {
		super(s, sim);

		controls.add(aliveRatio);
		aliveRatio = addExtraSlider(aliveRatio, 0.0, 1.0, ((GameOfLife) sim).getAliveRatio(), 0.25, 0.5);
		
		extraSliders.add(aliveRatio);
		System.out.println("initial aliveRatio = " + ((GameOfLife) sim).getAliveRatio());
	}
	
	@Override
	protected void updateExtra(Slider mySlider) {
		running = false;
		playButton.setGraphic(getImageView(RESTART_PNG));
		System.out.println("current aliveratio = " + mySlider.getValue());
		System.out.println("need to restart");
		simType = new GameOfLife(mySlider.getValue(), simType.getSize(), simType.getShape());
		simType.initializeCurrentCells();
		displayGrid(simType.getCurrentCells());
	}
	
	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}
}
