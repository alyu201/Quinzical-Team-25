package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.SceneManager;

public class ResetConfirmController {
	
	@FXML
	private Button buttonOk;
	
	@FXML
	private void onClickButtonOk(Event e) {
		// TODO: change primary stage to reflect changes
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
	}
}
