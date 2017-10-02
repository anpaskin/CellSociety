package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Segregation;
import cellsociety_Simulations.WaTor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SegregationWindow extends SimulationWindow {
	
	private Slider redToBlue = new Slider();
	private Slider emptyRatio = new Slider();
	private List<Slider> extraSliders = new ArrayList<>();
	
	public SegregationWindow(Stage s, CellManager sim) {
		super(s, sim);

		controls.add(redToBlue);
		redToBlue = addExtraSlider(redToBlue, 0.0, 1.0, ((Segregation) sim).getRedToBlue(), 0.25, 0.5);

		controls.add(emptyRatio);
		emptyRatio = addExtraSlider(emptyRatio, 0.0, 1.0, ((Segregation) sim).getEmptyRatio(), 0.25, 0.5);

		extraSliders.add(redToBlue);
		extraSliders.add(emptyRatio);
		
		System.out.println("initial redToBlue = " + ((Segregation) sim).getRedToBlue());
		System.out.println("initial emptyRatio = " + ((Segregation) sim).getEmptyRatio());
	}
	
	@Override
	protected void updateExtra(Slider mySlider) {
		running = false;
		playButton.setGraphic(getImageView(RESTART_PNG));
		System.out.println("current = " + mySlider.getClass() + mySlider.getValue());
		System.out.println("need to restart");
		update();
	}
	
	private void update() {
		simType = new Segregation(((Segregation)simType).getMinSimilar(), redToBlue.getValue(), emptyRatio.getValue(), simType.getSize(), simType.getShape(), simType.getToroidal());
		simType.initializeCurrentCells();
		displayGrid(simType.getCurrentCells());
	}
	
	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}
	
}
