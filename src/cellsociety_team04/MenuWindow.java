package cellsociety_team04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuWindow extends Window {

	private static final int BUTTONOFFSET = 10;
	private List<Button> buttons;
	private List<String> buttonNames = new ArrayList<String>(Arrays.asList("Segregation", "Predator-Prey", "Fire", "Game of Life"));
	private double BUTTONPADDING = (WIDTH - BUTTONOFFSET*2)/buttonNames.size();

	public MenuWindow() {
		super();
		setupScene();
		userInteraction();
	}	

	private void userInteraction() {
/*		for (int i = 0; i < buttons.size(); i++) {
			buttonNames.get(i).setOnAction(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(ActionEvent e) {
					label.setText("Accepted");
				}
			});
		}*/
	}

	@Override
	public void setupScene() {
		buttons = new ArrayList<Button>();
		addButtons();
		addTitle();
	}

	private void addButtons() { //https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
		for (int i = 0; i < buttonNames.size(); i++) {
			Button button = new Button(buttonNames.get(i));
			button.setLayoutX(BUTTONOFFSET + i*BUTTONPADDING);
			button.setLayoutY(HEIGHT*2/3);
			buttons.add(button);
			myRoot.getChildren().add(button);
		}
	}

	private void addTitle() {
		Text text = new Text();
		text.setFont(new Font(75));
		text.setWrappingWidth(WIDTH);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setText("Cell Society");
		text.setY(HEIGHT/4);
		myRoot.getChildren().add(text);
	}
}
