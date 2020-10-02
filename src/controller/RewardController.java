package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import utilities.SceneManager;

public class RewardController {

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
	
	@FXML
	private Button buttonSettings;

	public void initialize() {
		this.model = model.getMainModel();
		if(!(this.model.getName().getValue() == null)) {
			this.labelName.textProperty().bind(this.model.getName());
			this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}
		this.labelAnswer.setText(this.model.getCurrentQuestion().getAnswers().get(0));
		this.labelQuestionWinnings.setText("" + this.model.getCurrentQuestion().getWorth());
		if(!this.model.getCurrentQuestion().getCorrectlyAnswered()) {
			this.labelAnswer.setText("The correct answer was \"" + this.model.getCurrentQuestion().getAnswers().get(0) + "\"");
			this.labelCorrect.setText("INCORRECT");
		}
		// set allCompleted when all possible questions completed
		List<Boolean> allCompleted = new ArrayList<Boolean>();
		for (QuinzicalTuple question : this.model.getQuestions()) {
			allCompleted.add(question.getCompleted());
		}
		if (!allCompleted.contains(false)) {
			this.model.setAllCompleted(true);
		} else {
			this.model.setAllCompleted(false);
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
	private void onClickButtonContinue(Event e) {
		if (this.model.getAllCompleted()) {
			// change to EndView after fixing it (not done yet)
			SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
		}
		SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);			
	}
}
