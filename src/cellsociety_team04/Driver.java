package cellsociety_team04;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 
 * Driver for running the code
 * 
 * @author kelly
 *
 */

public class Driver extends Application {

	private static final String TITLE = "Cell Society";
	/*	private static final double MILLISECOND_DELAY = 0;
	private static final String SECOND_DELAY = null;*/
	private Group root;
	private Stage menuStage;
	private Scene menu;
	private Button GameofLife, PredatorPrey, Fire, Segregation;
	private ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(GameofLife, PredatorPrey, Fire, Segregation));
	private double BUTTONOFFSET = 150;

	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		root = new Group();
		menuStage = stage;
		menuStage.setTitle(TITLE);

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		setupMenuStage(primaryScreenBounds, menu); //http://www.java2s.com/Code/Java/JavaFX/GetScreensize.htm

		menuStage.show();

		/*segregation.setOnAction(e -> {
			Stage segStage = new Stage();
			//segStage.run();
		});
		*//*		//attach the game loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> step(SECOND_DELAY, root));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();*/
	}

	/*	private Object step(String secondDelay, Group root2) {
		// Get current cells from CellManager
		// Pass current cells to UI to update grid display
		// Update Cells in CellManager
		// Repeat
		// Need to check for the end?
		return null;
	}*/

	private void setupMenuStage(Rectangle2D dimensions, Scene scene) {
		//menuStage.setFullScreen(true);
		double width = dimensions.getMaxX();
		double height = dimensions.getMaxY();
		formatStage(dimensions, width, height);
		formatMenuScene(width, height);
	}

	private void formatMenuScene(double width, double height) {
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

	private void formatStage(Rectangle2D dimensions, double width, double height) {
		menuStage.setX(dimensions.getMinX());
		menuStage.setY(dimensions.getMinY());
		menuStage.setWidth(width);
		menuStage.setHeight(height);
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

	/*	private Button simButton() {

	}*/


	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
