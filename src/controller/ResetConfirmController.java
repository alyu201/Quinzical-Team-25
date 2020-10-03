package controller;

import java.io.IOException;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.MainModel;

/**
 * Confirm with the user if they would like to reset the state of the main model
 * for Quinzical.
 * 
 * @author wqsz7xn
 *
 */
public class ResetConfirmController {

	private MainModel model;
	@FXML
	private Button buttonOk;

	public void initialize() {
		this.model = MainModel.getMainModel();
	}

	/**
	 * Reloads the MainView
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonOk(Event e) {
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
		// Load a new MainMenuView to hide the user details
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenuView.fxml"));
			Parent root = loader.load();
			Main.getPrimaryStage().getScene().setRoot(root);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
