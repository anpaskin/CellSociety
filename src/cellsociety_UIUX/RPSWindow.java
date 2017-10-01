package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.RPS;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class RPSWindow extends SimulationWindow {
	private Slider rockPercent = new Slider();
	private Slider paperPercent = new Slider();
	private Slider scissorsPercent = new Slider();
	private List<Slider> extraSliders = new ArrayList<>();

	public RPSWindow(Stage s, CellManager sim) {
		super(s, sim);
		controls.add(rockPercent);
		controls.add(paperPercent);
		controls.add(scissorsPercent);
		rockPercent = addExtraSlider(rockPercent, 0.0, 1.0, ((RPS) sim).getRockPercent(), 0.25, 0.5);
		extraSliders.add(rockPercent);
		System.out.println("initial rockRatio = " + ((RPS) sim).getRockPercent());
		System.out.println("initial paperRatio = " + ((RPS) sim).getPaperPercent());
		System.out.println("initial scissorsRatio = " + ((RPS) sim).getScissorsPercent());
		//updateExtras(probCatch);
	}

}

