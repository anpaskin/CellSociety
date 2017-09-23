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
import javafx.util.Duration;

/**
 * 
 * Driver for running the code
 * 
 * @author kelly
 *
 */

public class Driver extends Application {

	private static final String TITLE = "Cell Society";
	private boolean segregationWindow = false;
	private boolean segregationRunning = false;
	private boolean watorWindow = false;
	private boolean watorRunning = false;
	private boolean fireWindow = false;
	private boolean fireRunning = false;
	private boolean gameoflifeWindow = false;
	private boolean gameoflifeRunning = false;

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
	}

	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
