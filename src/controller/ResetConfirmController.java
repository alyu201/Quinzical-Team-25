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
 * ResetConfirmController acts as the controller for the ResetConfirmView. ResetConfirmController 
 * allows the user to confirm the reset was done by selecting the 'ok' option
 */
public class ResetConfirmController {

	private MainModel model;
	
	@FXML
	private Button buttonOk;

	/**
	 * Initialize the controller and get the main model of the game
	 */
	public void initialize() {
		this.model = MainModel.getMainModel();
	}

	/**
	 * Exits the ResetConfirm pop-up window by selecting the 'ok' option and reloads the main menu view again 
	 * to remove the user winning details
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonOk(Event e) {
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
		Main.getPrimaryStage().getScene().getRoot().setEffect(null);
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
