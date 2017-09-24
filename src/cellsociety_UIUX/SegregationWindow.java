package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SegregationWindow extends SimulationWindow {
	
	private Button run, step;
	private Slider minSimilar;
	private Slider redToBlue;
	private Slider emptyRatio;
	
	public SegregationWindow(Stage s) {
		super(s);
		addSlider(minSimilar);
		addSlider(redToBlue);
		addSlider(emptyRatio);
	}
	
	private void addButtons() {
		//TODO
		Image startImage = new Image(getClass().getClassLoader().getResourceAsStream("start.png"));
		startButton.setGraphic(new ImageView(startImage));

		Image stepImage = new Image(getClass().getClassLoader().getResourceAsStream("step.png"));
		stepButton.setGraphic(new ImageView(stepImage));

		buttons = new ArrayList<Button>(Arrays.asList(startButton, stepButton));
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			button.setLayoutX(offset);
			button.setLayoutY(offset + i*padding);
			myRoot.getChildren().add(button);
		}
	}
	
	public void addSlider(Slider slider) {
		slider.setMin(0);
		slider.setMax(1);
		slider.setValue(0.5);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.25);
		slider.setBlockIncrement(0.05);
		slider.setLayoutX(offset);
		slider.setLayoutY(offset + buttons.size()*padding);
		myRoot.getChildren().add(slider);
	}


}
