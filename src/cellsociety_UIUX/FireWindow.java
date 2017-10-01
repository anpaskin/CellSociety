package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FireWindow extends SimulationWindow {
	private Slider probCatch = new Slider();
	private List<Slider> extraSliders = new ArrayList<>(Arrays.asList(probCatch));


	public FireWindow(Stage s, CellManager sim) {
		super(s, sim);
		controls.add(probCatch);
		probCatch = addExtraSliders(probCatch, 0.0, 1.0, ((Fire) sim).getPCatch(), 0.25, 0.5);
		extraSliders.add(probCatch);
		System.out.println("initial probCatch = " + ((Fire) sim).getPCatch());
		//updateExtras(probCatch);
	}

	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}

	@Override
	protected void updateExtra(Slider mySlider) {
		//running = false;
		playButton.setGraphic(getImageView(PLAY_PNG));
		((Fire)simType).setPCatch(probCatch.getValue());
		System.out.println("new probCatch = " + mySlider.getValue());
		System.out.println("press reset");
		//simType = new Fire(mySlider.getValue());
		System.out.println("probCatch = " + mySlider.getValue());
	}
}
