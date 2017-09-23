package cellsociety_team04;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public abstract class SimulationWindow extends Window {
	protected boolean start;
	protected Button run;
	protected Button step;
	protected double mySpeed;
	protected ArrayList<Button> myButtons;
	protected double numCells;
	protected double cellSize;
	private ArrayList<Button> buttons;
	
	public SimulationWindow() {
		super();
		setupScene();
	}
	
	@Override
	public void setupScene() {
		buttons = new ArrayList<Button>();
		addButtons();
	}
	
	private void addButtons() {
		
	}
	
	private void addSlider() {
		
	}
	
	public GridPane addGridPane() { //https://stackoverflow.com/questions/35367060/gridpane-of-squares-in-javafx
	    GridPane grid = new GridPane();
	    for (int row = 0; row < numCells; row++) {
	    	for (int col = 0; col < numCells; col++) {
	    		Rectangle rect = new Rectangle();
	    		rect.setWidth(cellSize);
	    		rect.setHeight(cellSize);
	    		GridPane.setRowIndex(rect, row);
	    		GridPane.setColumnIndex(rect, col);
	    		grid.getChildren().add(rect);
	    	}
	    }
	    myRoot.getChildren().add(grid);
	    return grid;
	}
	
	
}
