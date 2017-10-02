package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.WaTor;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class FireWindow extends SimulationWindow {
	private Slider probCatch = new Slider();
	private List<Slider> extraSliders = new ArrayList<>(Arrays.asList(probCatch));


	public FireWindow(Stage s, CellManager sim) {
		super(s, sim);
		
		controls.add(probCatch);
		probCatch = addExtraSlider(probCatch, 0.0, 1.0, ((Fire) sim).getPCatch(), 0.25, 0.5);
		
		extraSliders.add(probCatch);
		System.out.println("initial probCatch = " + ((Fire) sim).getPCatch());
	}

	@Override
	protected void updateExtra(Slider mySlider) {
		((Fire)simType).setPCatch(probCatch.getValue());
		System.out.println("new probCatch = " + mySlider.getValue());
	}
	
	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}
}
