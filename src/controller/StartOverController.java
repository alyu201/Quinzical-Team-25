package controller;

import java.util.ArrayList;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.MainModel;
import model.QuinzicalTuple;
import utilities.SceneManager;

public class StartOverController {
	
	private MainModel model;

	@FXML
	private Button buttonYes;

	@FXML
	private Button buttonNo;

	public void initialize() {
		this.model = model.getMainModel();

	}

	@FXML
	private void onClickButtonNo(Event e) {
		Stage stage = (Stage) buttonNo.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void onClickButtonYes(Event e) {
		this.model.setWinnings(0);
		this.model.setCurrentQuestion(null);
		this.model.setPracticeQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
		Stage stage = (Stage) buttonYes.getScene().getWindow();

		stage.close();
	}
}
