package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class RPSWindow extends SimulationWindow {
	private Slider rockPercent = new Slider();
	private Slider paperPercent = new Slider();
	private Slider scissorsPercent = new Slider();
	private List<Slider> extraSliders = new ArrayList<>(Arrays.asList(rockPercent, paperPercent, scissorsPercent));

	public RPSWindow(Stage s, CellManager sim) {
		super(s, sim);
		setupExtras();
	}
	
	protected void sliderDrag() {
		for (int i = 0; i < extraSliders.size(); i++) {
			Slider mySlider = extraSliders.get(i);
			mySlider.setOnMouseReleased(e -> {
				running = false;
				playButton.setGraphic(getImageView(RESET_PNG));
				updateExtra(mySlider);
				System.out.println("press reset");
			});
		}
	}

	private void setupExtras() {
		for (int i = 0; i < extraSliders.size(); i++) {
			controls.add(extraSliders.get(i));
			addExtraSliders(extraSliders.get(i), 0.0, 1.0, 0.5, 0.25, 0.5);
			//updateExtras();
		}
	}

//	protected void updateExtras() {
//		//simType = new RPS(rockPercent.getValue(), paperPercent.getValue(), scissorsPercent.getValue());
//		for (int i = 0; i < extraSliders.size(); i++) {
//			System.out.println(extraSliders.get(i).getValue());
//		}
//	}
}

