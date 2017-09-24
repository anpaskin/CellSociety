package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FireWindow extends SimulationWindow {
	private Slider probCatch;
	
	public FireWindow(Stage s) {
		super(s);
	}
	
	public void addSlider() {
		probCatch.setMin(0);
		probCatch.setMax(1);
		probCatch.setValue(0.5);
		probCatch.setShowTickLabels(true);
		probCatch.setShowTickMarks(true);
		probCatch.setMajorTickUnit(0.25);
		probCatch.setBlockIncrement(0.05);
		probCatch.setLayoutX(offset);
		probCatch.setLayoutY(offset + buttons.size()*padding);
		myRoot.getChildren().add(probCatch);
	}

}
