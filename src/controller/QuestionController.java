package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;

import model.GameMode.GameType;
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

/**
 * QuestionController is the controller for QuestionView. It displays the
 * question that the user has selected, allows for hints in a 'hangman' styled
 * fashion and allows the user to pass the question.
 */
public class QuestionController {

	private MainModel model;
	private int counter = 0;
	private Integer[] hintIndices;

	@FXML
	private Label labelQuestion;

	@FXML
	private Label labelHint;

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

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu. Load the current question into the scene.
	 */
	@FXML
	public void initialize() {
		this.model = MainModel.getMainModel();

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
		}

		sayQuestion();
	}

	/**
	 * Use the program festival to speak the currently selected question. Voice is
	 * adjusted to the users settings.
	 */
	private void sayQuestion() {
		new Thread() {
			@Override
			public void run() {
				try {
					String command = "festival";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
					Process process = pb.start();
					BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
					BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
					BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

					// TODO: Voice volume
					stdin.write("(Parameter.set 'Duration_Stretch "
							+ (1.0 - (new Double(model.getSettings().getSpeed()) / 100)) + ")");
					stdin.write("(SayText \"" + model.getCurrentQuestion().getQuestion() + "\")");
					stdin.flush();

					stdin.close();
					stdout.close();
					stderr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
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

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

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
		sayQuestion();
	}

	@FXML
	private void onClickButtonDontKnow(Event e) {
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
		if (this.textFieldAnswer.getText().trim().length() > 0) {
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

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
