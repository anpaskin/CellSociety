package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.RPS;
import cellsociety_Simulations.WaTor;
import javafx.scene.control.Slider;
import javafx.stage.Stage;


/**
 * 
 * @author Kelly Zhang
 *
 */
public class RPSWindow extends SimulationWindow {
	private Slider rockPercent = new Slider();
	private Slider paperPercent = new Slider();
	private Slider scissorsPercent = new Slider();
	private List<Slider> extraSliders = new ArrayList<>();

	public RPSWindow(Stage s, CellManager sim) {
		super(s, sim);

		controls.add(rockPercent);
		rockPercent = addExtraSlider(rockPercent, 0.0, 1.0, ((RPS) sim).getRockPercent(), 0.25, 0.5);
		
		controls.add(paperPercent);
		paperPercent = addExtraSlider(paperPercent, 0.0, 1.0, ((RPS) sim).getPaperPercent(), 0.25, 0.5);
		
		controls.add(scissorsPercent);
		scissorsPercent = addExtraSlider(scissorsPercent, 0.0, 1.0, ((RPS) sim).getScissorsPercent(), 0.25, 0.5);

		extraSliders.add(rockPercent);
		extraSliders.add(paperPercent);
		extraSliders.add(scissorsPercent);
		
		System.out.println("initial rockRatio = " + ((RPS) sim).getRockPercent());
		System.out.println("initial paperRatio = " + ((RPS) sim).getPaperPercent());
		System.out.println("initial scissorsRatio = " + ((RPS) sim).getScissorsPercent());
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
		simType = new RPS(rockPercent.getValue(), paperPercent.getValue(), scissorsPercent.getValue(), simType.getSize(), simType.getShape());
		displayGrid(simType.getCurrentCells());
	}
	
	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}
	
}

