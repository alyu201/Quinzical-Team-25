package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.MainModel;

/**
 * Controller for MainView. Displays a title, grid of buttons containing each
 * category question and the users total winnings.
 */
public class InfoController {

	private MainModel model;
	@FXML
	private Button buttonOk;
	
	@FXML
	private static Label header;
	
	@FXML
	private static Label content;

	public void initialize() {
		this.model = model.getMainModel();
	}

	@FXML
	private void onClickButtonOk(Event e) {
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
	}
}
