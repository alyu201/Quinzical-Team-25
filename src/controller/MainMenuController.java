package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.MainModel;
import utilities.SceneManager;

public class MainMenuController {

	private MainModel model;
	@FXML
	private Button buttonPlay;

	@FXML
	private Button buttonPractice;

	@FXML
	private Button buttonLeaderboard;

	@FXML
	private Button buttonCreateQuestions;

	@FXML
	private Button buttonSettings;

	@FXML
	private Button buttonQuit;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	public void initialize() {
		this.model = model.getMainModel();
		this.labelName.setText(this.model.getName());
		this.labelWinnings.setText("$" + this.model.getWinnings());

	}

	@FXML
	private void onClickButtonPlay(Event e) {
		// name is empty and needs to be set
		if(this.model.getName().equals("")) {
			SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/PlayView.fxml"), e);
		}
	}

	@FXML
	private void onClickButtonTraining(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/PlayView.fxml"), e);
	}

	@FXML
	private void onClickButtonLeaderboard(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/LeaderboardView.fxml"), e);
	}

	@FXML
	private void onClickButtonCreateQuestions(Event e) {
		System.out.println("create questions");
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonQuit(Event e) {
		// save and quit
		this.model.toJSONFile();
		Stage stage = (Stage) buttonQuit.getScene().getWindow();
		stage.close();
		System.exit(0);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

}
