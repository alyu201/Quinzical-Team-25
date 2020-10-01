package controller;

import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javafx.beans.binding.Bindings;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

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
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}

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
		sayQuestion();
	}

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
					stdin.write("(Parameter.set 'Duration_Stretch " + (new Double(model.getSettings().getSpeed()) / 100)
							+ ")");
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
	private void onClickButtonBack(Event e) {
		model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonHint(Event e) {
		if (counter < 3) {
			int remaining = 3 - (counter + 1);
			buttonHint.setText("HINT " + "(" + remaining + ")");
			String currentHint = this.labelHint.getText();
			StringBuilder newHint = new StringBuilder(currentHint);
			int nextRand = hintIndices[counter];
			int hintIndex = nextRand*2;
			newHint.setCharAt(hintIndex, this.model.getCurrentQuestion().getAnswers().get(0).charAt(nextRand));
			this.labelHint.setText(newHint.toString());
			counter += 1;
		}
	}

	@FXML
	private void onClickButtonRepeat(Event e) {
		sayQuestion();
	}

	@FXML
	private void onClickButtonDontKnow(Event e) {
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
		} else {
			this.model.getCurrentQuestion().setCorrectlyAnswered(false);
			SceneManager.changeScene(getClass().getResource("/view/AnswerView.fxml"), e);
		}
	}

	@FXML
	private void onPressEnterTextFieldAnswer(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if (isAnswerCorrect()) {
				this.model.getCurrentQuestion().setCorrectlyAnswered(true);
			} else {
				this.model.getCurrentQuestion().setCorrectlyAnswered(false);
			}

			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"), ke);
				if (isAnswerCorrect()) {
					this.model.addWinnings(this.model.getCurrentQuestion().getWorth());
				}
			} else {
				SceneManager.changeScene(getClass().getResource("/view/AnswerView.fxml"), ke);
			}
		}
	}

	@FXML
	private void onClickButtonEnter(Event e) {
		if (isAnswerCorrect()) {
			this.model.getCurrentQuestion().setCorrectlyAnswered(true);
		} else {
			this.model.getCurrentQuestion().setCorrectlyAnswered(false);
		}

		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			SceneManager.changeScene(getClass().getResource("/view/RewardView.fxml"), e);
			if (isAnswerCorrect()) {
				this.model.addWinnings(this.model.getCurrentQuestion().getWorth());
			}
		} else {
			SceneManager.changeScene(getClass().getResource("/view/AnswerView.fxml"), e);
		}
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
