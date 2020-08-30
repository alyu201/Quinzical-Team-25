package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.JepordayTuple;

public class QuestionController {

	private JepordayTuple model;

	@FXML
	private Label labelQuestionTitle;

	@FXML
	private Label labelQuestion;

	@FXML
	private TextField textfieldAnswer;

	@FXML
	private Button buttonAnswerSubmit;

	@FXML
	private Label labelAnswer;

	@FXML
	private Label labelAddedWinnings;

	public void initialize() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println(this.model.toString());
		this.labelAnswer.setText("hi");
	}

	QuestionController(JepordayTuple model) {
		this.model = model;
	}

	public void setQuestion(JepordayTuple question) {
		this.model = question;
	}
}
