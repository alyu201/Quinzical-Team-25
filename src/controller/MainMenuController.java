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

/**
 * MainMenuController is the controller for MainMenuView. MainMenuController
 * acts as the central hub for accessing the functionality of the game and is
 * the first screen the user is greeted with. MainMenuController has multiple
 * buttons that allows users to navigate to different functionalities within th
 * Quinzical game.
 * 
 */
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

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu.
	 */
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

	/**
	 * Navigate to PointsPlayView. If the user has completed all the questions
	 * within the game then navigate to the EndView screen instead.
	 * 
	 * @param e Event that triggered this function
	 */
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

	/**
	 * Navigate to StartOverView, a screen where you can 'start over' the state of
	 * the game.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonStartOver(Event e) {
		SceneManager.addStage(getClass().getResource("/view/StartOverView.fxml"), e);
	}

	/**
	 * Navigate to PointsPracticeView. If the user has completed all the questions
	 * within the practice game then navigate to the EndView screen instead.
	 * 
	 * @param e Event that triggered this function
	 */
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

	/**
	 * Navigate to LeaderboardView. A screen which ranks all previous games of
	 * Quinzical.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonLeaderboard(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/LeaderboardView.fxml"), e);
	}

	@FXML
	private void onClickButtonCreateQuestion(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/CreateQuestionView.fxml"), e);
	}

	/**
	 * Navigate to SettingsView. A screen in which the user can change the speed and
	 * volume of the Quinzical.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	/**
	 * Navigate to ResetView. A screen in which the user can completely reset the
	 * main model that manages Quinzical game state
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonReset(Event e) {
		SceneManager.addStage(getClass().getResource("/view/ResetView.fxml"), e);
	}

	/**
	 * Quit Quinzical game without error.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonQuit(Event e) {
		// save and quit
		this.model.toJSONFile();
		Stage stage = (Stage) buttonQuit.getScene().getWindow();
		stage.close();
		System.exit(0);
	}

	/**
	 * Navigate to NameView. A screen that gives the user the oportunity to change
	 * their name for the game that they are playing. This name will be reported on
	 * the leaderboard when the user completed a game.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}
}
