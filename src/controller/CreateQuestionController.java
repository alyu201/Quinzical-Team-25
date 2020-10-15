package controller;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import model.QuestionTypeEnum.QuestionType;
import model.QuinzicalTuple;
import utilities.SceneManager;

public class CreateQuestionController {

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

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

	private MainModel model;

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

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonAddQuestion(Event e) {
		String question = this.textFieldQuestion.getText();
		String category = this.textFieldCategory.getText();
		int worth = Integer.valueOf(this.textFieldWorth.getText());
		QuestionType type = (this.choiceBoxQuestionType.getSelectionModel().getSelectedItem().equals("New Zealand"))
				? QuestionType.NEWZEALAND
				: QuestionType.INTERNATIONAL;

		ArrayList<String> answers = new ArrayList<String>();
		answers.add(this.textFieldAnswer.getText());
		this.model.getQuestions().add(new QuinzicalTuple(category, question, worth, answers, false, false, type));

		this.textFieldAnswer.clear();
		this.textFieldCategory.clear();
		this.textFieldQuestion.clear();
		this.textFieldWorth.clear();
		this.choiceBoxQuestionType.getSelectionModel().clearAndSelect(0);
	}

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

	@FXML
	private void onClickListViewCategory(Event e) {
		this.textFieldCategory.setText(listViewCategory.getSelectionModel().getSelectedItem());
	}
}
