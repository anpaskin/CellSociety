package cellsociety_UIUX;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_Simulations.CellManager;
import cellsociety_team04.XMLParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuWindow extends Window {

	private static final String GAMEOFLIFE_TAG = "GameOfLife";
	private static final String FIRE_TAG = "Fire";
	private static final String PREDATORPREY_TAG = "PredatorPrey";
	private static final String SEGREGATION_TAG = "Segregation";
	private static final String GAMEOFLIFE_PNG = "gameoflife.png";
	private static final String FIRE_PNG = "fire.png";
	private static final String WATOR_PNG = "wator.png";
	private static final String SEGREGATION_PNG = "segregation.png";
	private static final double WIDTH = 1000;
	private static final double HEIGHT = 500;
	
	private static final int BUTTONOFFSET = 50;
	private List<Button> buttons;
//	private Button newSimButton;
	private double buttonPadding;

//	private Stage simStage = new Stage();
//	private boolean newSim = true;
	private CellManager simChoice;
	
	public MenuWindow(Stage s) {
		super(s);
		simChoice = null;
		setupSceneDimensions();
		setupScene();
	}	

	@Override
	public void setupScene() {
		addButtons();
		addTitle();
	}
	
	protected void setupSceneDimensions() {
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}

	public void chooseSim() { //http://www.java2s.com/Code/Java/JavaFX/AddClickactionlistenertoButton.htm
		for (int i = 0; i < buttons.size(); i ++) {
			Button button = buttons.get(i);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					simChoice = getSimFromFile(button);
					System.out.println("button pressed!!");
					//System.out.println(file);
				}
			});
			
		}
	}
	
	public void resetMenu() {
		simChoice = null;
	}
	
	private CellManager getSimFromFile(Button buttonPressed) {
		String simFileString = buttonPressed.getAccessibleText();
		simFileString += ".xml";
		ClassLoader cl = getClass().getClassLoader();
		File simFile = new File(cl.getResource(simFileString).getFile());
		XMLParser parser = new XMLParser();
		parser.buttonChooseFile(simFile);
		return parser.getSimulation();
	}
	
	private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
		//http://docs.oracle.com/javafx/2/ui_controls/button.htm
		
		Button segregationButton = createMenuButton(SEGREGATION_PNG, SEGREGATION_TAG);
		Button watorButton = createMenuButton(WATOR_PNG, PREDATORPREY_TAG);
		Button fireButton = createMenuButton(FIRE_PNG, FIRE_TAG);
		Button gameoflifeButton = createMenuButton(GAMEOFLIFE_PNG, GAMEOFLIFE_TAG);
		
		buttons = new ArrayList<Button>(Arrays.asList(segregationButton, watorButton, fireButton, gameoflifeButton));
		buttonPadding = (WIDTH - BUTTONOFFSET*2 - buttons.get(0).getWidth())/buttons.size();
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			setMenuButtonLayout(button, BUTTONOFFSET + buttons.get(i).getMaxWidth() + i*buttonPadding, HEIGHT*2/3);
		}
		myRoot.getChildren().addAll(buttons);
	}
	
	private void setMenuButtonLayout(Button button, Double x, Double y) {
		button.setLayoutX(x);
		button.setLayoutY(y);
	}
	
	private Button createMenuButton(String imageName, String buttonText) {
		Image buttonImage = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		Button simButton = new Button();
		simButton.setGraphic(new ImageView(buttonImage));
		simButton.setAccessibleText(buttonText);
		
		return simButton;
	}

	private void addTitle() {
		Image titleImage = new Image(getClass().getClassLoader().getResourceAsStream("cellsociety.png"));
		ImageView title = new ImageView(titleImage);
		title.setLayoutX(WIDTH/2-title.getBoundsInLocal().getWidth()/2);
		title.setLayoutY(HEIGHT*1/3-title.getBoundsInLocal().getHeight()/2);
		myRoot.getChildren().add(title);
	}
	
	public CellManager getSimChoice() {
		return simChoice;
	}
}
