package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.MainModel;
import utilities.SceneManager;

public class PointsPlayController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	private Button buttonInfo;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Label labelCategory;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		this.labelName.textProperty().bind(this.model.getName());
		this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());

		// New questions
		if (this.model.getGameQuestions().size() == 0) {
			ArrayList<String> categoriesSet = new ArrayList<String>();
			Random rand = new Random();

			while (categoriesSet.size() < 5) {
				int nextRandBounded = Math.abs(rand.nextInt() % (this.model.getCategories().size() - 1));
				// System.out.println(nextRandBounded);
				String currentCategory = this.model.getCategories().get(nextRandBounded);
				if (!categoriesSet.contains(currentCategory)) {
					categoriesSet.add(currentCategory);
				}
			}

			ArrayList<QuinzicalTuple> questionSet = new ArrayList<QuinzicalTuple>();
			for (String category : categoriesSet) {
				ArrayList<QuinzicalTuple> questionSetCurrentCategory = new ArrayList<QuinzicalTuple>();
				ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();

				for (QuinzicalTuple question : this.model.getQuestions()) {
					if (question.getCategory().equals(category)) {
						filteredQuestions.add(question);
					}
				}

				int i = 0;
				while (i < 5) {
					int nextRandBounded = Math.abs(rand.nextInt() % (filteredQuestions.size() - 1));
					// System.out.println(nextRandBounded);
					QuinzicalTuple currentQuestion = filteredQuestions.get(nextRandBounded);
					if (!questionSetCurrentCategory.contains(currentQuestion)) {
						questionSetCurrentCategory.add(currentQuestion);
						++i;
					}
					System.out.println("" + i);
					questionSet.addAll(questionSetCurrentCategory);
				}

				// Finally add those questions
				this.model.setGameQuestions(questionSet);
				for (QuinzicalTuple q : this.model.getGameQuestions()) {
					System.out.println(q.toString());
				}
			}
		}

		ArrayList<String> questionCategories = new ArrayList<String>();
		for (QuinzicalTuple question : this.model.getGameQuestions()) {
			if (!questionCategories.contains(question.getCategory())) {
				questionCategories.add(question.getCategory());
			}
		}

		int r = 0;
		int c = 0;
		for (String category : questionCategories) {
			boolean flag = false;
			Label label = new Label(category);
			label.setTextFill(Color.web("#FF0000"));
			this.gridPanePoints.add(label, c, r);
			r++;
			ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : this.model.getGameQuestions()) {
				if (!filteredQuestions.contains(question) && category.equals(question.getCategory())) {
					filteredQuestions.add(question);
				}
			}

			// sort questions by worth lowest worth first
			Collections.sort(filteredQuestions, ((x, y) -> {
				return Integer.compare(((QuinzicalTuple) x).getWorth(), ((QuinzicalTuple) y).getWorth());
			}));

			for (QuinzicalTuple question : filteredQuestions) {
				Button button = new Button("$" + question.getWorth());
				if (question.getCompleted() == true || flag == true) {
					button.setDisable(true);
				}


				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent me) {
						model.setCompleted(GameType.GAMESMODULE, question);
						model.setCurrentQuestion(question);
						model.toJSONFile();
						SceneManager.changeScene(getClass().getResource("/view/QuestionView.fxml"), me);
					}
				});
				button.setPrefSize(200, 100);
				this.gridPanePoints.add(button, c, r);

				if(question.getCompleted() == false) {
					flag = true;
				}

				++r;
			}
			++c;
			r = 0;
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

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
