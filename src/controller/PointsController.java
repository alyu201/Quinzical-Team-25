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
		// scope to tuples for currentCategory
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
		System.out.println("limit: " + limit);
		int i = 0;
		while (i < limit) {
			int nextRand = (rand.nextInt() % (limit- 1));
			QuinzicalTuple current = xs.get(nextRand);
			if (!questionSet.contains(current)) {
				questionSet.add(current);
				++i;
			}
		}
		
		for(QuinzicalTuple a : questionSet) {
			System.out.println(a.toString());
		}

		for (int col = 0; col< limit; col++) {
			Button button = new Button(questionSet.get(col).getQuestion());
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
