package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import model.GameMode.GameType;
import utilities.SceneManager;

public class EndController {

	private MainModel model;

	@FXML
	private Button buttonInfo;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Label labelCorrect;

	@FXML
	private Label labelAnswer;

	@FXML
	private Label labelQuestionWinnings;

	@FXML
	private Button buttonContinue;

	public void initialize() {
		this.model = model.getMainModel();
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonContinue(Event e) {
		// Practice Mode
		/*if(this.model.getCurrentGameType().equals(GameType.PRACTICEMODULE)) {
			SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
		} else {
			// Play Mode
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
			
		}*/

		// after added answerView
		SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);			
	}
}
