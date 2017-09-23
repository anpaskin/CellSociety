package cellsociety_team04;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOfLifeWindow extends SimulationWindow {
	
	private Button run, step;
	
	public GameOfLifeWindow(Stage stage, Group root, ArrayList<Button> buttons) {
		this.myStage = stage;
		this.myRoot = root;
		this.start = false;
		this.mySpeed = 1;
		this.myButtons = buttons;
		
		addParams();
		
	}

	@Override
	public void addParams() {
		// TODO Auto-generated method stub
		
	}

}
