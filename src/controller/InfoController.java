package controller;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for MainView. Displays a small, grid of buttons containing each
 * category question and the users total winnings.
 */
public class InfoController {

	@FXML
	private Button buttonOk;

	@FXML
	private static Label header;

	@FXML
	private static Label content;

	/**
	 * Closes the info dialog window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonOk(Event e) {
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
		Main.getPrimaryStage().getScene().getRoot().setEffect(null);
	}
}
