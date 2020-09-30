package controller;

import java.io.IOException;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.MainModel;
import utilities.SceneManager;

public class ResetConfirmController {

	private MainModel model;
	@FXML
	private Button buttonOk;

	public void initialize() {
		this.model = model.getMainModel();
	}

	@FXML
	private void onClickButtonOk(Event e) {
		// TODO: change primary stage to reflect changes
		Stage stage = (Stage) buttonOk.getScene().getWindow();
		stage.close();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenuView.fxml"));
			Parent root = loader.load();
			Main.getPrimaryStage().getScene().setRoot(root);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
