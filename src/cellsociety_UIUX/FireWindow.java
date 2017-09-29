package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;

import cellsociety_Simulations.CellManager;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FireWindow extends SimulationWindow {
	private Slider probCatch = new Slider();
	
	public FireWindow(Stage s, CellManager sim) {
		super(s, sim);
//		addSlider(probCatch);
	}
}
