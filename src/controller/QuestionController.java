package controller;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import model.MainModel;

/**
 * Controller for QuestionView. Handles a single question in the jeopardy
 * question set. The user must type an answer to the question hint and the GUI
 * will display if the user has input correct answer. If the user's input answer
 * is incorrect then the correct answer will be displayed
 */
public class QuestionController {

	private MainModel model;

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

	@FXML
	private Button buttonReturnBoard;

	public void initialize() {

		// Wrap text and set question to GUI
		this.labelQuestion.setWrappingWidth(780);
		this.labelAddedWinnings.setWrappingWidth(780);
		this.labelQuestion.setText(this.model.getCurrentQuestion().getQuestion());

		// Key event detect on press 'Enter' key. Show on GUI if question answered
		// correctly and winnings
		EventHandler<KeyEvent> answerHandlerKeyEvent = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					answerQuestion();
				}
			}
		};

		// Mouse event show on GUI if question answered correctly and winnings
		EventHandler<MouseEvent> answerHandlerMouseEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				answerQuestion();
			}
		};

		// Set handlers
		this.textfieldAnswer.setOnKeyPressed(answerHandlerKeyEvent);
		this.buttonAnswerSubmit.setOnMouseClicked(answerHandlerMouseEvent);

		// Text to speech the model question
		new Thread() {

			@Override
			public void run() {
				try {
					String command = "echo " + model.getCurrentQuestion().getQuestion() + " | festival --tts";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
					pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		// Return to main scene
		buttonReturnBoard.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
				Parent sParent = loader.load();
				Scene sScene = new Scene(sParent, 800, 800);
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(sScene);
				window.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	QuestionController() {
		this.model = MainModel.getMainModel();
	}

	/**
	 * Check if the question has been answered correctly and display the result of
	 * the answer to the GUI
	 */
	public void answerQuestion() {
		String answer = textfieldAnswer.getText();
		if (answer.toLowerCase().contains(model.getCurrentQuestion().getAnswer().toLowerCase().replaceAll(" ", ""))) {
			labelAnswer.setFill(Color.GREEN);
			labelAnswer.setText("Correct!");
			labelAddedWinnings
					.setText("$" + model.getCurrentQuestion().getWorth() + " have been added to your winnings");
			labelAddedWinnings.setVisible(true);
			model.addWinnings(Integer.valueOf(model.getCurrentQuestion().getWorth()));
		} else {
			labelAnswer.setFill(Color.RED);
			labelAnswer.setText("Incorrect!");
			labelAddedWinnings.setText("The correct answer was \"" + model.getCurrentQuestion().getAnswer() + "\"");
		}
		labelAnswer.setVisible(true);
		labelAddedWinnings.setVisible(true);
		textfieldAnswer.setDisable(true);
		model.putState();
	}
}
