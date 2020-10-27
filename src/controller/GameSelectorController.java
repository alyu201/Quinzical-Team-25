package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import utilities.SceneManager;

/**
 * GameSelectorController acts as the controller for the GameSelectorView which 
 * the allows the user to choose the game module they wish to play in: New Zealand, 
 * International, or Practice.
 *
 */

public class GameSelectorController {

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonSettings;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private ImageView imageLock;

	@FXML
	private Button buttonReturn;

	@FXML
	private Button buttonNewZealand;

	@FXML
	private Button buttonInternational;

	@FXML
	private Button buttonPractice;

	@FXML
	private Label labelUnlock;
	
	@FXML
	private Button buttonReturnToMain;

	@FXML
	private HBox hBox;
	private MainModel model;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu as well as the remaining categories to be completed 
	 * before unlocking the international section.
	 */
	public void initialize() {
		this.model = MainModel.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getInternationalWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}
		if (this.model.getInternationalUnlocked()) {
			this.imageLock.setVisible(false);
			this.buttonInternational.setDisable(false);
		}

		// set label to prompt the user to unlock international section
		if (!this.model.getInternationalUnlocked()) {

			labelUnlock.setText("COMPLETE " + (2 - this.model.getCompletedCategories())
					+ " MORE CATEGORIES FROM THE NEW ZEALAND MODULE TO UNLOCK INTERNATIONAL MODULE");
		} else {
			// TODO Some reason this isn't working, user workaround
			hBox.getChildren().remove(labelUnlock);

			labelUnlock.setVisible(false);
			labelUnlock.setPrefWidth(0);
			labelUnlock.setPrefHeight(0);
		}
	}

	/**
	 * Open the info pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	/**
	 * Open the game settings pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	/**
	 * Navigate to the screen to prompt the user to enter a user name.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	/**
	 * Return to the previous screen which is the main menu.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	/**
	 * Navigates to the New Zealand mode of the game. If completed, then the end view is shown 
	 * instead.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonNewZealand(Event e) {
		this.model.setCurrentGameType(GameType.GAMESMODULE);
		if (this.model.getAllCompletedGame()) {
			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
		}
	}

	/**
	 * Navigates to the International mode of the game. If completed, then the end view is shown 
	 * instead.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonInternational(Event e) {
		this.model.setCurrentGameType(GameType.INTERNATIONALMODULE);
		if (this.model.getAllCompletedInternational()) {
			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
		}
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
	 * Return to the main menu screen.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonReturnToMain(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
