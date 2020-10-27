package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import model.GameMode.GameType;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import model.MainModel;
import utilities.SceneManager;
import utilities.TTSQuestionThread;

/**
 * QuestionController is the controller for QuestionView. It displays the
 * question that the user has selected, allows for hints in a 'hangman' styled
 * fashion and allows the user to pass the question.
 */
public class QuestionController {

	private MainModel model;
	private int counter = 0;
	private Timer timer = new Timer();
	private Integer[] hintIndices;

	@FXML
	private Label labelQuestion;

	@FXML
	private Label labelHint;

	@FXML
	private Label labelTimer;

	@FXML
	private TextField textFieldAnswer;

	@FXML
	private Button buttonRepeat;

	@FXML
	private Button buttonDontKnow;

	@FXML
	private Button buttonHint;

	@FXML
	private Button buttonReturnBoard;

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonEnter;

	@FXML
	private Button buttonSettings;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Label labelDash1;

	@FXML
	private Label labelDash2;

	@FXML
	private Label labelTimeLeft;
	
	@FXML
	private Button buttonReturnToMain;
	
	private TTSQuestionThread thread;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu. Load the current question into the scene.
	 */
	@FXML
	public void initialize() {
		this.model = MainModel.getMainModel();
		
		//Set thread to run for question
		this.thread = new TTSQuestionThread();
		
		// creates the shuffled indices for hints
		hintIndices = new Integer[this.model.getCurrentQuestion().getAnswers().get(0).length()];
		for (int i = 0; i < hintIndices.length; i++) {
			hintIndices[i] = i;
		}
		Collections.shuffle(Arrays.asList(hintIndices));

		// TODO: Decide how to handle the hints for things with multiple answers
		String hint = "_ ".repeat(this.model.getCurrentQuestion().getAnswers().get(0).length() - 1);
		hint += "_";
		this.labelHint.setText(hint);
		this.labelQuestion.setText(this.model.getCurrentQuestion().getQuestion());
		this.labelQuestion.setWrapText(true);

		// Set name
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}

		// Set the question controller to play view type or practice view type
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			this.buttonHint.setDisable(true);
			this.buttonHint.setText("HINTS UNAVAILABLE");
			this.labelHint.setText("");
		} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getInternationalWinnings().asString());
			this.buttonHint.setDisable(true);
			this.buttonHint.setText("HINTS UNAVAILABLE");
			this.labelHint.setText("");
		} else {
			this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());

			// Remove timer in practice mode
			labelDash1.setVisible(false);
			labelDash1.setPrefWidth(0);
			labelDash1.setPrefHeight(0);

			labelDash2.setVisible(false);
			labelDash2.setPrefWidth(0);
			labelDash2.setPrefHeight(0);

			labelTimeLeft.setVisible(false);
			labelTimeLeft.setPrefWidth(0);
			labelTimeLeft.setPrefHeight(0);

			labelTimer.setVisible(false);
			labelTimer.setPrefWidth(0);
			labelTimer.setPrefHeight(0);
		}

		// TTS the question
		this.thread.run();

		// show count-down timer if not in practice mode
		if (!this.model.getCurrentGameType().equals(GameType.PRACTICEMODULE)) {
			this.timer.scheduleAtFixedRate(new TimerTask() {
				int countDown = 60;

				@Override
				public void run() {
					if (countDown >= 0) {
						Platform.runLater(() -> {
							if (countDown < 10) {
								labelTimer.setText("0:0" + countDown);
							} else {
								labelTimer.setText("0:" + countDown);
							}
							countDown--;
						});
					} else {
						timer.cancel();
						// automatically change to reward screen
						Platform.runLater(() -> {
							if (isAnswerCorrect()) {
								model.getCurrentQuestion().setCorrectlyAnswered(true);
							} else {
								model.getCurrentQuestion().setCorrectlyAnswered(false);
							}
							if (isAnswerCorrect()) {
								if (model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
									model.addGameWinnings(model.getCurrentQuestion().getWorth());
								} else if (model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
									model.addInternationalWinnings(model.getCurrentQuestion().getWorth());
								} else {
									model.addPracticeWinnings(model.getCurrentQuestion().getWorth());
								}
							}
							SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"));
						});
					}
				}
			}, 0, 1000);
		}
	}

	/**
	 * Check if the currently selected question was correctly answered for the
	 * currently active question set.
	 * 
	 * @return boolean If the question was correctly answered.
	 */
	private boolean isAnswerCorrect() {
		String userAnswer = this.textFieldAnswer.getText();
		for (String x : this.model.getCurrentQuestion().getAnswers()) {
			if (userAnswer.toLowerCase().contains((String) x.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Navigate to the screen to prompt the user to enter a user name.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickLabelName(Event e) {
		this.thread.killVoice();
		this.thread.interrupt();
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	/**
	 * Opens the game settings pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	/**
	 * Provide a hint for the user in 'hangman' style. The user is given 3 hints
	 * maximum.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonHint(Event e) {
		if (counter < 3) {
			int remaining = 3 - (counter + 1);
			buttonHint.setText("HINT " + "(" + remaining + ")");
			String currentHint = this.labelHint.getText();
			StringBuilder newHint = new StringBuilder(currentHint);
			int nextRand = hintIndices[counter];
			int hintIndex = nextRand * 2;
			newHint.setCharAt(hintIndex, this.model.getCurrentQuestion().getAnswers().get(0).charAt(nextRand));
			this.labelHint.setText(newHint.toString());
			counter += 1;
		}
	}

	/**
	 * Repeat the question using festival
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonRepeat(Event e) {
		this.thread.run();
	}

	/**
	 * Stop speech synthesis and navigate to the RewardView.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonDontKnow(Event e) {
		this.thread.killVoice();
		this.thread.interrupt();
		this.model.getCurrentQuestion().setCorrectlyAnswered(false);
		SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"), e);
	}

	/**
	 * Check if the question is correctly answered and mark it answered in the main
	 * model. Add the value of the question to the users winnings
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onPressEnterTextFieldAnswer(KeyEvent ke) {
		if (this.textFieldAnswer.getText().trim().length() > 0) {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				this.timer.cancel();
				if (isAnswerCorrect()) {
					this.model.getCurrentQuestion().setCorrectlyAnswered(true);
				} else {
					this.model.getCurrentQuestion().setCorrectlyAnswered(false);
				}
				if (isAnswerCorrect()) {
					if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
						this.model.addGameWinnings(this.model.getCurrentQuestion().getWorth());
					} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
						this.model.addInternationalWinnings(this.model.getCurrentQuestion().getWorth());
					} else {
						this.model.addPracticeWinnings(this.model.getCurrentQuestion().getWorth());
					}
				}
				SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"), ke);
			}
		}
	}

	/**
	 * Check if the question is correctly answered and mark it answered in the main
	 * model. Add the value of the question to the users winnings
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonEnter(Event e) {
		this.thread.killVoice();
		this.thread.interrupt();
		if (this.textFieldAnswer.getText().trim().length() > 0) {
			this.timer.cancel();
			if (isAnswerCorrect()) {
				this.model.getCurrentQuestion().setCorrectlyAnswered(true);
			} else {
				this.model.getCurrentQuestion().setCorrectlyAnswered(false);
			}
			if (isAnswerCorrect()) {
				if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
					this.model.addGameWinnings(this.model.getCurrentQuestion().getWorth());
				} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
					this.model.addInternationalWinnings(this.model.getCurrentQuestion().getWorth());
				} else {
					this.model.addPracticeWinnings(this.model.getCurrentQuestion().getWorth());
				}
			}
			SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"), e);
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
	 * Return to the main menu screen.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonReturnToMain(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
