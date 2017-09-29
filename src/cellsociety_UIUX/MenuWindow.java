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
	private boolean pressed;
	
	public MenuWindow(Stage s) {
		super(s);
		simChoice = null;
		setupSceneDimensions();
		setupScene();
	}
	
	public void resetMenu() {
		simChoice = null;
		pressed = false;
	}

	@Override
	public void setupScene() {
		addButtons();
		addTitle();
	}
	
	protected void setupSceneDimensions() {
		myScene = new Scene(myRoot, WIDTH, HEIGHT);
	}

	
	
	public boolean chooseSim() { //http://www.java2s.com/Code/Java/JavaFX/AddClickactionlistenertoButton.htm
		pressed = false;
		for (int i = 0; i < buttons.size(); i ++) {
			Button button = buttons.get(i);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					String fileString = button.getAccessibleText();
					fileString = fileString + ".xml";
					ClassLoader cl = getClass().getClassLoader();
					File file = new File(cl.getResource(fileString).getFile());
					XMLParser parser = new XMLParser();
					parser.buttonChooseFile(file);
					simChoice = parser.getSimulation();
					System.out.println("button pressed!!");
					System.out.println(file);
					pressed = true;
				}
			});
			
		}
		return pressed;
	}
	
	private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
		//http://docs.oracle.com/javafx/2/ui_controls/button.htm
		
/*		Button newSimButton = createButton("newsim.png", "NewSim");
		setButtonLayout(newSimButton, WIDTH/2 - newSimButton.getMaxWidth()/2, HEIGHT*1/2);
		myRoot.getChildren().add(newSimButton);*/
		
		Button segregationButton = createButton(SEGREGATION_PNG, SEGREGATION_TAG);
		Button watorButton = createButton(WATOR_PNG, PREDATORPREY_TAG);
		Button fireButton = createButton(FIRE_PNG, FIRE_TAG);
		Button gameoflifeButton = createButton(GAMEOFLIFE_PNG, GAMEOFLIFE_TAG);
		
		buttons = new ArrayList<Button>(Arrays.asList(segregationButton, watorButton, fireButton, gameoflifeButton));
		buttonPadding = (WIDTH - BUTTONOFFSET*2 - buttons.get(0).getWidth())/buttons.size();
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			setButtonLayout(button, BUTTONOFFSET + buttons.get(i).getMaxWidth() + i*buttonPadding, HEIGHT*2/3);
		}
		myRoot.getChildren().addAll(buttons);
	}
	
	private void setButtonLayout(Button button, Double x, Double y) {
		button.setLayoutX(x);
		button.setLayoutY(y);
	}
	
	private Button createButton(String imageName, String buttonText) {
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
	
/*	public boolean getNewSim() {
		return newSim;
	}
	
	public void setNewSim(boolean b) {
		newSim = b;
	}*/
	
}
