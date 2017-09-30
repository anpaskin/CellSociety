package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	public FireWindow(Stage s, CellManager sim) {
		super(s, sim);
		controls.add(probCatch);
		addExtraSliders(probCatch, 0.0, 1.0, 0.5, 0.25, 0.5);
		updateExtras(probCatch);
	}
	
	protected void sliderDrag() {
		probCatch.setOnMouseReleased(e -> {
			running = false;
			playButton.setGraphic(getImageView(RESET_PNG));
			updateExtras(probCatch);
			System.out.println("press reset");
		});
	}
	
	protected void updateExtras(Slider mySlider) {
		//simType = new Fire(mySlider.getValue());
		System.out.println(mySlider.getValue());
	}
}
