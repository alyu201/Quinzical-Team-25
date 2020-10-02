package controller;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.MainModel;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import utilities.SceneManager;

public class CategoryController {

	private MainModel model;
	private int position;

	@FXML
	private Button buttonReturn;

	@FXML
	private Button buttonContinue;
	
	@FXML
	private Button buttonInfo;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;
	
	@FXML
	private HBox userDetails;

	@FXML
	private Button buttonLeft;

	@FXML
	private Button buttonRight;

	@FXML
	private Button buttonCategory;
	
	@FXML
	private Button buttonSettings;

	public void initialize() {
		this.model = model.getMainModel();
		if(this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if(this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}
		this.position = 0;
		this.buttonCategory.setText(this.model.getCategories().get(0));
	}

	@FXML
	private void onClickButtonContinue(Event e) {
		this.model.setCurrentCategory(this.buttonCategory.getText());
		SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
	}

	@FXML
	private void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonCategory(Event e) {
		// Reset current category and create a question set
		this.model.setCurrentCategory(this.buttonCategory.getText());
		this.model.setPracticeQuestions(new ArrayList<QuinzicalTuple>());
		SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
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
	private void onClickButtonLeft(Event e) {
		if (!(this.position <= 0)) {
			this.position -= 1;
		} else {
			this.position = this.model.getCategories().size() - 1;
		}
		this.buttonCategory.setText(this.model.getCategories().get(position));
	}

	@FXML
	private void onClickButtonRight(Event e) {
		if (!(this.position >= this.model.getCategories().size() - 1)) {
			this.position += 1;
		} else {
			this.position = 0;
		}
		this.buttonCategory.setText(this.model.getCategories().get(position));
	}

}
