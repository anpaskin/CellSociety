import cellsociety_team04.*;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * 
 * Driver for running the code
 * 
 * @author kelly
 *
 */

public class main extends Application {

	/**
	 * makes a new driver
	 */
	public void start(Stage stage) {
		Driver startingDriver = new Driver(stage);
	}

	/**
	 * Start of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
