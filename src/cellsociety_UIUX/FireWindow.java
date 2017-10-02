package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.WaTor;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * 
 * This is the specific window for Fire simulations, subclass of SimulationWindow
 * Creates all the specific user input sliders (probCatch) and uses inputs to update how the simulation runs
 * 		can be changed while the simulation is running
 * 
 * @author Kelly Zhang
 *
 */
public class FireWindow extends SimulationWindow {
	private static final String PROB_CATCH = "probCatch";
	private Slider probCatch = new Slider();
	private List<Slider> extraSliders = new ArrayList<>(Arrays.asList(probCatch));


	public FireWindow(Stage s, CellManager sim) {
		super(s, sim);
		
		controls.add(probCatch);
		probCatch = addExtraSlider(probCatch, 0.0, 1.0, ((Fire) sim).getPCatch(), 0.25, 0.5);
		addExtraSliderLabel(probCatch, PROB_CATCH);
		
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
