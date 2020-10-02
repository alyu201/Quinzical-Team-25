package controller;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.GameMode.GameType;
import model.MainModel;
import utilities.SceneManager;

public class MainMenuController {

	private MainModel model;
	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonPlay;

	@FXML
	private Button buttonStartOver;

	@FXML
	private Button buttonPractice;

	@FXML
	private Button buttonLeaderboard;

	@FXML
	private Button buttonCreateQuestions;

	@FXML
	private Button buttonSettings;

	@FXML
	private Button buttonReset;

	@FXML
	private Button buttonQuit;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	public void initialize() {
		this.model = MainModel.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonPlay(Event e) {
		this.model.setCurrentGameType(GameType.GAMESMODULE);
		if (this.model.getAllCompletedGame()) {
			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else if (this.model.getName().getValue() == null) {
			SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
		}
	}

	@FXML
	private void onClickButtonStartOver(Event e) {
		SceneManager.addStage(getClass().getResource("/view/StartOverView.fxml"), e);
	}

	@FXML
	private void onClickButtonPractice(Event e) {
		this.model.setCurrentGameType(GameType.PRACTICEMODULE);
		if (this.model.getAllCompletedPractice()) {
			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else if (this.model.getPracticeQuestions().size() != 0) {
			SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/CategoryView.fxml"), e);
		}
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
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonReset(Event e) {
		SceneManager.addStage(getClass().getResource("/view/ResetView.fxml"), e);
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
