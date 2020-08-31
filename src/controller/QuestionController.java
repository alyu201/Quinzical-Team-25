package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		this.textfieldAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					String answer = textfieldAnswer.getText(); 
					if(answer.toLowerCase().contains(model.answer.toLowerCase())) {
						System.out.println("correct!");
					}
					else {
						System.out.println("incorrect!");
						System.out.println(model.answer);
					}
				}
			}
		});
		
	}

	QuestionController(JepordayTuple model) {
		this.model = model;
	}

	public void setQuestion(JepordayTuple question) {
		this.model = question;
	}
}
