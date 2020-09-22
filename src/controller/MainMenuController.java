package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.SceneManager;

public class MainMenuController {

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

	public void initialize() {
		System.out.println("init");
	}

	@FXML
	private void onClickButtonPlay(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/PlayView.fxml"), e);
	}

	@FXML
	private void onClickButtonPractice(Event e) {
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
		Stage stage = (Stage) buttonQuit.getScene().getWindow();
		stage.close();
		System.exit(0);
	}

}
