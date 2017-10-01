package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
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
		addExtraSliders(probCatch, 0.0, 1.0, 0.5, 0.25, 0.5);
		//updateExtras(probCatch);
	}

	@Override
	protected void sliderDrag() {
		for (int i = 0; i < extraSliders.size(); i++) {
			Slider extraSlider = extraSliders.get(i);
			extraSlider.setOnMouseReleased(e -> {
				updateExtra(extraSlider);
			});
		}
	}

	private void updateProbCatch() {
		running = false;
		playButton.setGraphic(getImageView(RESET_PNG));
		updateExtra(probCatch);
		System.out.println("press reset");
	}

/*	@Override
	protected void updateExtras(Slider mySlider) {
		//simType = new Fire(mySlider.getValue());
		System.out.println("probCatch = " + mySlider.getValue());
	}*/
}
