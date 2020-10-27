package controller;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import model.QuestionTypeEnum.QuestionType;
import model.QuinzicalTuple;
import utilities.SceneManager;

/**
 * CreateQuestionController acts as the controller for CreateQuestionView. CreateQuestionController 
 * provides textfields for the user to enter their question, category, worth, answers, and the game 
 * type before pressing 'Add Question' to store the question into the state file.
 */

public class CreateQuestionController {

	private String question;
	private String category;
	private int worth;
	private ArrayList<String> answers  = new ArrayList<String>();;
	private QuestionType type;
	private boolean confirmYes = false;
	private boolean confirmNo = false;
	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;
	
	@FXML
	private Label labelConfirm;
	
	@FXML
	private Button buttonYes;
	
	@FXML
	private Button buttonNo;

	@FXML
	private HBox userDetails;

	@FXML
	private TextField textFieldQuestion;

	@FXML
	private TextField textFieldCategory;

	@FXML
	private ListView<String> listViewCategory;

	@FXML
	private TextField textFieldAnswer;

	@FXML
	private TextField textFieldWorth;

	@FXML
	private ComboBox<String> choiceBoxQuestionType;
	
	@FXML
	private Button buttonReturnToMain;

	private MainModel model;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu.
	 */
	public void initialize() {
		this.model = MainModel.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}

		// Populate listVIew
		this.model.getCategories().stream().forEach(x -> {
			listViewCategory.getItems().add(x);
		});

		choiceBoxQuestionType.getItems().add("New Zealand");
		choiceBoxQuestionType.getItems().add("International");
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
	 * Open the game settings pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	/**
	 * Return to the previous screen which is the main menu.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	/**
	 * Prompts the user to confirm the given question set will be added or indicate the 
	 * necessary fields are missing.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonAddQuestion(Event e) {
		// check if a question had been added before
		if (this.confirmYes) {
			this.labelConfirm.setText("Your question has been added!");
			this.buttonYes.setVisible(false);
			this.buttonNo.setVisible(false);
		} else if (this.confirmNo) {
			this.labelConfirm.setVisible(false);
			this.buttonYes.setVisible(false);
			this.buttonNo.setVisible(false);
		} else {
			// check for any empty fields
			if (this.textFieldQuestion.getText() == null || this.textFieldCategory.getText() == null || this.textFieldWorth.getText() == null ||
					this.textFieldAnswer.getText() == null || this.choiceBoxQuestionType.getSelectionModel().getSelectedItem() == null) {
				this.labelConfirm.setText("Some required fields may be missing.\nTry again.");
				this.labelConfirm.setVisible(true);
			} else {
				question = this.textFieldQuestion.getText().trim();
				category = this.textFieldCategory.getText().trim();
				String worthGiven = this.textFieldWorth.getText().trim();
				String answerGiven = this.textFieldAnswer.getText().trim();
				String typeGiven = this.choiceBoxQuestionType.getSelectionModel().getSelectedItem().trim();
				
				// check for empty text fields after trimming for whitespaces
				if (question.length() == 0 || category.length() == 0 || worthGiven.length() == 0 ||
						answerGiven.length() == 0 || typeGiven.length() == 0) {
					this.labelConfirm.setText("Some required fields may be missing.\nTry again.");
					this.labelConfirm.setVisible(true);
				} else {
					worth = Integer.valueOf(worthGiven);
					type = (typeGiven.equals("New Zealand"))
							? QuestionType.NEWZEALAND
							: QuestionType.INTERNATIONAL;

					answers.add(answerGiven);
					this.confirmYes = true;
					this.labelConfirm.setText("Ouestion will be added.\nAre you sure?");
					this.labelConfirm.setVisible(true);
					this.buttonYes.setVisible(true);
					this.buttonNo.setVisible(true);
				}
			}
		}
		this.confirmYes = false;
		this.confirmNo = false;
	}
	
	/**
	 * Removes the confirmation prompt for adding the question given by the user.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonNo(Event e) {
		this.confirmNo = true;
	}
	
	/**
	 * Adds the given question to the model after the user confirms the question will be added.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonYes(Event e) {
		this.model.getQuestions().add(new QuinzicalTuple(category, question, worth, answers, false, false, type));
		this.confirmYes = true;
		this.textFieldAnswer.clear();
		this.textFieldCategory.clear();
		this.textFieldQuestion.clear();
		this.textFieldWorth.clear();
		this.choiceBoxQuestionType.getSelectionModel().clearAndSelect(0);
	}

	/**
	 * Displays the categories available to the user and filters the selection range when 
	 * the user types in a category in textfield.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onInputTextFieldCategory(Event e) {
		this.listViewCategory.getItems().clear();
		this.model.getCategories().stream().filter(x -> {
			if (x.contains(textFieldCategory.getText())) {
				return true;
			} else {
				return false;
			}
		}).forEach(x -> {
			listViewCategory.getItems().add(x);
		});
	}

	/**
	 * Displays the category selected by the user on the textfield.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickListViewCategory(Event e) {
		this.textFieldCategory.setText(listViewCategory.getSelectionModel().getSelectedItem());
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
