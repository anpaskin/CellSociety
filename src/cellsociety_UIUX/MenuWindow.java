package cellsociety_UIUX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuWindow extends Window {

	private static final int BUTTONOFFSET = 50;
	private List<Button> buttons;
	private static final double buttonScale = 0.5;
	
	private double buttonPadding;

	public MenuWindow() {
		super();
		setupScene();
		userInteraction();
	}	

	@Override
	public void setupScene() {
		addButtons();
		addTitle();
	}

	private void userInteraction() { //http://www.java2s.com/Code/Java/JavaFX/AddClickactionlistenertoButton.htm
		for (int i = 0; i < buttons.size(); i ++) {
			Button button = buttons.get(i);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					//TODO need to have new stage open instead
					
				}
			});
		}
	}

	private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
		//http://docs.oracle.com/javafx/2/ui_controls/button.htm
		Image segregationImage = new Image(getClass().getClassLoader().getResourceAsStream("segregation.png"));
		Button segregationButton = new Button();
		segregationButton.setGraphic(new ImageView(segregationImage));
		
		Image watorImage = new Image(getClass().getClassLoader().getResourceAsStream("wator.png"));
		Button watorButton = new Button();
		watorButton.setGraphic(new ImageView(watorImage));
		
		Image fireImage = new Image(getClass().getClassLoader().getResourceAsStream("fire.png"));
		Button fireButton = new Button();
		fireButton.setGraphic(new ImageView(fireImage));
		
		Image gameoflifeImage = new Image(getClass().getClassLoader().getResourceAsStream("gameoflife.png"));
		Button gameoflifeButton = new Button();
		gameoflifeButton.setGraphic(new ImageView(gameoflifeImage));
		
		buttons = new ArrayList<Button>(Arrays.asList(segregationButton, watorButton, fireButton, gameoflifeButton));
		System.out.println(buttons);
		buttonPadding = (WIDTH - BUTTONOFFSET*2 - buttons.get(0).getWidth())/buttons.size();
		
		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			button.setLayoutX(BUTTONOFFSET + buttons.get(i).getWidth() + i*buttonPadding);
			button.setLayoutY(HEIGHT*2/3);
			myRoot.getChildren().add(button);
		}
	}

	private void addTitle() {
		Image titleImage = new Image(getClass().getClassLoader().getResourceAsStream("cellsociety.png"));
		ImageView title = new ImageView(titleImage);
		title.setLayoutX(WIDTH/2-title.getBoundsInLocal().getWidth()/2);
		title.setLayoutY(HEIGHT*1/3-title.getBoundsInLocal().getHeight()/2);
		myRoot.getChildren().add(title);
	}
}
