package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.JepordayTuple;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class QuestionController {

	private JepordayTuple model;

	@FXML
	private Text labelQuestionTitle;

	@FXML
	private Text labelQuestion;

	@FXML
	private TextField textfieldAnswer;

	@FXML
	private Button buttonAnswerSubmit;

	@FXML
	private Text labelAnswer;

	@FXML
	private Text labelAddedWinnings;

	public void initialize() throws InterruptedException {
		this.labelQuestion.setText(this.model.question);

		EventHandler<KeyEvent> answerHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					String answer = textfieldAnswer.getText();
					if (answer.toLowerCase().contains(model.answer.toLowerCase())) {
						labelAnswer.setFill(Color.GREEN);
						labelAnswer.setText("Correct!");
						labelAnswer.setVisible(true);

						labelAddedWinnings.setText("$" + model.worth + " have been added to your winnings");
						labelAddedWinnings.setVisible(true);
						// MainController.model.addWinnings(Integer.valueOf(model.worth));
					} else {
						labelAnswer.setFill(Color.RED);
						labelAnswer.setText("Incorrect! The correct answer was \"" + model.answer + "\"");
						labelAnswer.setVisible(true);
					}
					textfieldAnswer.setDisable(true);
				}
			}
		};

		this.textfieldAnswer.setOnKeyPressed(answerHandler);
		// this.buttonAnswerSubmit.setOnMouseClicked(answerHandler);

		// Text to speech the model question
		new Thread() {

			@Override
			public void run() {

				try {
					String command = "echo " + model.question + " | festival --tts";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
					Process process = pb.start();
					int exitStatus = process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}.start();

	}

	QuestionController(JepordayTuple model) {
		this.model = model;
	}

}
