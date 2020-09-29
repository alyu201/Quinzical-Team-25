package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.binding.Bindings;
import model.QuinzicalTuple;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

public class QuestionController {

	private MainModel model;
	
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
	public void initialize() {
		this.model = model.getMainModel();
		
		// TODO: Decide how to handle the hints for things with multiple answers
		String hint = "_ ".repeat(this.model.getCurrentQuestion().getAnswers().get(0).length()-1);
		hint += "_";
		this.labelHint.setText(hint);
		this.labelQuestion.setText(this.model.getCurrentQuestion().getQuestion());
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
	private void onClickButtonInfo(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonHint(Event e) {
		
	}

	@FXML
	private void onClickButtonRepeat(Event e) {
	}

	@FXML
	private void onClickButtonDontKnow(Event e) {
		
	}

	@FXML
	private void onClickButtonEnter(Event e) {
	}
}