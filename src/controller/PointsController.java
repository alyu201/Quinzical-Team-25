package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.binding.Bindings;
import model.QuinzicalTuple;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

public class PointsController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();

		// Scope to tuples for currentCategory
		String currentCategory = this.model.getCurrentCategory();
		ArrayList<QuinzicalTuple> xs = new ArrayList<QuinzicalTuple>();
		for(QuinzicalTuple question: this.model.getQuestions()) {
			if(question.getCategory().equals(currentCategory)) {
				xs.add(question);
			}
		}

		// If a category has less than 5 questions default to that length
		ArrayList<QuinzicalTuple> questionSet = new ArrayList<QuinzicalTuple>();
		Random rand = new Random();
		int limit = (xs.size() < 5) ? xs.size() : 5;

		// FIXME: brute add random question to bank
		int i = 0;
		while (i < limit) {
			int nextRand = Math.abs((rand.nextInt() % (xs.size()-1)));
			QuinzicalTuple current = xs.get(nextRand);
			if (!questionSet.contains(current)) {
				questionSet.add(current);
				++i;
			}
		}
		
		// Sort questions by lowest worth first
		questionSet.sort((x,y) -> {
			if(x.getWorth() < y.getWorth()) {
				return -1;
			}
			else if(x.getWorth() > y.getWorth()) {
				return 1;
			} else {
				return 0;
			}
		});

		// Add questions to screen
		for (int col = 0; col< limit; col++) {
			Button button = new Button("$" + questionSet.get(col).getWorth());
			button.setPrefWidth(150);
			button.setPrefHeight(150);
			button.setStyle("-fx-background-color: #00C3B1; -fx-background-radius: 30px;");
			//button.setStyle();
			this.gridPanePoints.add(button, col, 0);
		}
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
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}
}