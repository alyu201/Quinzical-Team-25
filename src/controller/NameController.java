package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.MainModel;
import utilities.SceneManager;

/**
 * Controller for MainView. Displays a title, grid of buttons containing each
 * category question and the users total winnings.
 */
public class NameController {

	public MainModel model;

	@FXML
	private Label labelTitleName;

	@FXML
	private TextField textFieldName;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private Button buttonContinue;
	
	@FXML
	private Button buttonInfo;

	public void initialize() {
		this.model = model.getMainModel();
		//this.labelName.setText(this.model.getName());
		//this.labelWinnings.setText("$" + this.model.getWinnings());
		this.labelName.textProperty().bind(this.model.getName());
		this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
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
	private void onClickButtonContinue(Event e) {
		this.model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onInputTextFieldName(KeyEvent ke) {
		this.model.setName(this.textFieldName.getText());
		if(ke.getCode().equals(KeyCode.ENTER)) {
			this.onClickButtonContinue(ke);
		}
	}
}
