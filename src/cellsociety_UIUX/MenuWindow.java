package cellsociety_UIUX;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.sun.xml.internal.txw2.Document;

import cellsociety_Simulations.CellManager;
import cellsociety_team04.Driver;
import cellsociety_team04.XMLParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuWindow extends Window {

	private static final double WIDTH = 1000;
	private static final double HEIGHT = 500;
	
	private static final int BUTTONOFFSET = 50;
	private List<Button> buttons;
	private Button newSimButton;
	private double buttonPadding;

	private Stage simStage = new Stage();
	private boolean newSim = true;
	private CellManager simChoice;
	private boolean pressed;
	
	public MenuWindow(Stage s) {
		super(s);
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
	
	/*public void newSimulation(Stage stage, XMLParser parser) {
		newSimButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				newSim = true;
				parser.chooseFile(stage);
				simulation = parser.getSimulation();
			}
		});
	}	*/

	private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
		//http://docs.oracle.com/javafx/2/ui_controls/button.htm
		Image newSimImage = new Image(getClass().getClassLoader().getResourceAsStream("newsim.png"));
		newSimButton = new Button();
		newSimButton.setGraphic(new ImageView(newSimImage));
		
		newSimButton.setLayoutX(WIDTH/2 - newSimButton.getMaxWidth()/2);
		newSimButton.setLayoutY(HEIGHT*1/2);
		myRoot.getChildren().add(newSimButton);
		
		Button segregationButton = createButton("segregation.png", "Segregation");
		Button watorButton = createButton("wator.png", "PredatorPrey");
		Button fireButton = createButton("fire.png", "Fire");
		Button gameoflifeButton = createButton("gameoflife.png", "GameOfLife");
		
		buttons = new ArrayList<Button>(Arrays.asList(segregationButton, watorButton, fireButton, gameoflifeButton));
		buttonPadding = (WIDTH - BUTTONOFFSET*2 - buttons.get(0).getWidth())/buttons.size();
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			button.setLayoutX(BUTTONOFFSET + buttons.get(i).getMaxWidth() + i*buttonPadding);
			button.setLayoutY(HEIGHT*2/3);
			myRoot.getChildren().add(button);
		}
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
	
	public boolean getNewSim() {
		return newSim;
	}
	
	public void setNewSim(boolean b) {
		newSim = b;
	}
}
