package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.MainModel;
import utilities.SceneManager;

public class RewardController {
	
	private MainModel model;

	@FXML
	private Button buttonInfo;

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
		this.labelAnswer.setText(this.model.getCurrentQuestion().getAnswers().get(0));
		this.labelQuestionWinnings.setText("$" + this.model.getCurrentQuestion().getWorth());
		if(!this.model.getCurrentQuestion().getCorrectlyAnswered()) {
			this.labelCorrect.setText("INCORRECT");
		}
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
		SceneManager.changeScene(getClass().getResource("/view/PointsView.fxml"), e);
	}
}
