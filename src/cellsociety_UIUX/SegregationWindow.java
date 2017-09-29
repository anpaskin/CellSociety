package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;

import cellsociety_Simulations.CellManager;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SegregationWindow extends SimulationWindow {
	
	private Slider minSimilar = new Slider();
	private Slider redToBlue = new Slider();
	private Slider emptyRatio = new Slider();
	
	public SegregationWindow(Stage s, CellManager sim) {
		super(s, sim);
//		addSlider(minSimilar);
//		addSlider(redToBlue);
//		addSlider(emptyRatio);
	}
	
/*	private void addExtras() {
		//TODO
		playButton = new Button();
		playButton.setGraphic(getImageView(PLAY_PNG));
		setControlButtonLayout(playButton, offset, offset);

		stepButton = new Button();
		stepButton.setGraphic(getImageView(STEP_PNG));
		setControlButtonLayout(stepButton, offset, offset + padding);

		buttons = new ArrayList<Node>(Arrays.asList(playButton, stepButton));
		myRoot.getChildren().addAll(buttons);
	}*/
}
