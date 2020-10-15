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

	private MainModel model;

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
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonNewZealand(Event e) {
		this.model.setCurrentGameType(GameType.GAMESMODULE);
		if (this.model.getAllCompletedGame()) {
			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
		}
	}

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
}
