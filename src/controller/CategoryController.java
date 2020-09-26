package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.MainModel;
import utilities.SceneManager;

public class CategoryController {

	private MainModel model;
	private int position;

	@FXML
	private Button buttonReturn;

	@FXML
	private Button buttonContinue;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private Button buttonLeft;

	@FXML
	private Button buttonRight;

	@FXML
	private Button buttonCategory;

	public void initialize() {
		this.model = model.getMainModel();
		this.labelName.setText(this.model.getName());
		this.labelWinnings.setText("$" + this.model.getWinnings());
		this.position = 0;
		this.buttonCategory.setText(this.model.getCategories().get(0));
	}

	@FXML
	private void onClickButtonContinue(Event e) {
		System.out.println("continue");
		//SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonLeft(Event e) {
		if(!(this.position < 0)) {
			this.position -= 1;
		}
		this.buttonCategory.setText(this.model.getCategories().get(position));
	}

	@FXML
	private void onClickButtonRight(Event e) {
		if(!(this.position >= this.model.getCategories().size()-1)) {
			this.position += 1;
		}
		this.buttonCategory.setText(this.model.getCategories().get(position));
	}
	
	@FXML
	private void onClickButtonCategory(Event e) {
	}
	
}
