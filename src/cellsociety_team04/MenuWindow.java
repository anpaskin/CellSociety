package cellsociety_team04;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuWindow extends Window {
	
	private static final int BUTTONOFFSET = 50;
	private Scene menu;
	private Group root;
	private List<Button> buttons;
	

	public MenuWindow() {
		super();
		setupScene();
	}	
	
	@Override
	public void setupScene() {
		buttons = new ArrayList<Button>();
		addbuttons();
	}
	
	private void addbuttons() {
		
	}
	

	private void formatMenuScene(double width, double height, Stage menuStage) {
		menu = new Scene(root, width, height, Color.WHITE);
		menuStage.setScene(menu);
		root.getChildren().add(titleText(width, height));
		
		for (int i = 0; i < buttons.size(); i++) {
			Button myButton = buttons.get(i);
			myButton = new Button("hellooo");//((Button) buttons.get(i)).getName());
			//myButton.s
			System.out.println(myButton);
			myButton.setLayoutX(BUTTONOFFSET + i*(width-2*(BUTTONOFFSET+myButton.getBoundsInLocal().getWidth()))/((buttons.size()-1)));//-myButton.getBoundsInLocal().getWidth()/2);
			myButton.setLayoutY(height*2/3);
			root.getChildren().add(myButton);
		}
	}
	
	private Text titleText(double width, double height) {
		Text myText = new Text();
		myText.setFont(new Font(75));
		myText.setWrappingWidth(width);
		myText.setTextAlignment(TextAlignment.CENTER);
		myText.setText("Cell Society");
		myText.setY(height/4);
		return myText;
	}
}
