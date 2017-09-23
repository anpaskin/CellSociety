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
	private Stage menuStage;

	/**
	 * Displays the menu in the window
	 */
	@Override
	public void start(Stage stage) {
		menuStage = stage;
		menuStage.setTitle(TITLE);

		Window menu = new MenuWindow();
		menuStage.setScene(menu.getScene());
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
		// TODO Auto-generated method stub
		return null;
	}*/

/*	private void formatStage(Rectangle2D dimensions, double width, double height) {
		menuStage.setX(dimensions.getMinX());
		menuStage.setY(dimensions.getMinY());
		menuStage.setWidth(width);
		menuStage.setHeight(height);
	}*/

	/*	private Button simButton() {

	}*/


	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
