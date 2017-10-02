package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_Simulations.Fire;
import cellsociety_Simulations.Segregation;
import cellsociety_Simulations.WaTor;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * 
 * @author Kelly Zhang
 *
 */
public class WatorWindow extends SimulationWindow {

	private Slider sharkRatio = new Slider();
	private Slider fishRatio = new Slider();
	private Slider initialSharkEnergy = new Slider();
	private Slider sharkBreedLifeCount = new Slider();
	private Slider fishBreedLifeCount = new Slider();
	private Slider fishEnergyContent = new Slider();
	private List<Slider> extraSliders = new ArrayList<>();

	public WatorWindow(Stage s, CellManager sim) {
		super(s, sim);

		controls.add(sharkRatio);
		sharkRatio = addExtraSlider(sharkRatio, 0.0, 1.0, ((WaTor) sim).getSharkRatio(), 0.25, 0.5);

		controls.add(fishRatio);
		fishRatio = addExtraSlider(fishRatio, 0.0, 1.0, ((WaTor) sim).getFishRatio(), 0.25, 0.5);

		controls.add(initialSharkEnergy);
		initialSharkEnergy = addExtraSlider(initialSharkEnergy, 0.0, 5.0, ((WaTor) sim).getInitialSharkEnergy(), 0.25, 0.5);
		initialSharkEnergy.setValue((int) Math.round(initialSharkEnergy.getValue()));

		controls.add(sharkBreedLifeCount);
		sharkBreedLifeCount = addExtraSlider(sharkBreedLifeCount, 0.0, 1.0, ((WaTor) sim).getSharkBreedCount(), 0.25, 0.5);
		sharkBreedLifeCount.setValue((int) Math.round(sharkBreedLifeCount.getValue()));

		controls.add(fishBreedLifeCount);
		fishBreedLifeCount = addExtraSlider(fishBreedLifeCount, 0.0, 1.0, ((WaTor) sim).getFishBreedCount(), 0.25, 0.5);
		fishBreedLifeCount.setValue((int) Math.round(fishBreedLifeCount.getValue()));

		controls.add(fishEnergyContent);
		fishEnergyContent = addExtraSlider(fishEnergyContent, 0.0, 5.0, ((WaTor) sim).getFishEnergyContent(), 0.25, 0.5);
		fishEnergyContent.setValue((int) Math.round(fishEnergyContent.getValue()));

		extraSliders.add(sharkRatio);
		extraSliders.add(fishRatio);
		extraSliders.add(initialSharkEnergy);
		extraSliders.add(sharkBreedLifeCount);
		extraSliders.add(fishBreedLifeCount);
		extraSliders.add(fishEnergyContent);

		System.out.println("initial sharkRatio = " + ((WaTor) sim).getSharkRatio());
		System.out.println("initial fishRatio = " + ((WaTor) sim).getFishRatio());
		System.out.println("initial initialSharkEnergy = " + ((WaTor) sim).getInitialSharkEnergy());
		System.out.println("initial sharkBreedLifeCount = " + ((WaTor) sim).getSharkBreedCount());
		System.out.println("initial fishBreedLifeCount = " + ((WaTor) sim).getFishBreedCount());
		System.out.println("initial fishEnergyContent = " + ((WaTor) sim).getFishEnergyContent());
	}

	@Override
	protected void updateExtra(Slider mySlider) {
		if (mySlider.equals(sharkRatio) || mySlider.equals(fishRatio)) {
			running = false;
			playButton.setGraphic(getImageView(RESTART_PNG));
			System.out.println("current = " + mySlider.getClass() + mySlider.getValue());
			System.out.println("need to restart");
			update();
		}
		else {
			if (mySlider.equals(initialSharkEnergy)) {
				((WaTor)simType).setInitialSharkEnergy((int) Math.round(mySlider.getValue()));
				System.out.println("new initialSharkEnergy = " + mySlider.getValue());
			}
			if (mySlider.equals(sharkBreedLifeCount)) {
				((WaTor)simType).setSharkBreedCount((int) Math.round(mySlider.getValue()));
				System.out.println("new sharkBreedLifeCount = " + mySlider.getValue());
			}
			if (mySlider.equals(fishBreedLifeCount)) {
				((WaTor)simType).setFishBreedCount((int) Math.round(mySlider.getValue()));
				System.out.println("new fishBreedLifeCount = " + mySlider.getValue());
			}
			if (mySlider.equals(fishEnergyContent)) {
				((WaTor)simType).setFishEnergyContent((int) Math.round(mySlider.getValue()));
				System.out.println("new fishEnergyContent = " + mySlider.getValue());
			}
		}
	}

	private void update() {
		simType = new WaTor(((WaTor) simType).getSharkRatio(), ((WaTor) simType).getFishRatio(), simType.getSize(), (int) Math.round(initialSharkEnergy.getValue()), (int) Math.round(sharkBreedLifeCount.getValue()),(int) Math.round(fishBreedLifeCount.getValue()), (int) Math.round(fishEnergyContent.getValue()), simType.getShape(), simType.getToroidal());
		simType.initializeCurrentCells();
		displayGrid(simType.getCurrentCells());
	}

	@Override
	protected List<Slider> getExtraSliders() {
		return extraSliders;
	}
}
