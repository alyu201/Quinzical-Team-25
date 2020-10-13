package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import model.MainModel;
import utilities.SceneManager;

/**
 * PointsPlayController is the controller for PointsPlayView. It is the main
 * official game mode of Quinzical. PointsPlayController loads an existing
 * question set if one exists or creates a new set of questions from randomly
 * selected categories. PointsPlayController displays a grid of questions that
 * are neatly organized for the user to answer.
 */
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
	private Button buttonSettings;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu. If a set of questions has not been generated
	 * then it will generate a set. Otherwise it loads an existing question set from
	 * the main model.
	 */
	@FXML
	public void initialize() {
		// Restore and set on screen elements
		this.model = MainModel.getMainModel();
		this.labelName.textProperty().bind(this.model.getName());
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
		} else {
			this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
		}

		// New questions (no game questions or game questions all completed)
		if (this.model.getGameQuestions().size() == 0) {
			ArrayList<String> categoriesSet = new ArrayList<String>();
			Random rand = new Random();

			while (categoriesSet.size() < 5) {
				int nextRandBounded = Math.abs(rand.nextInt() % (this.model.getCategories().size() - 1));
				String currentCategory = this.model.getCategories().get(nextRandBounded);
				if (!categoriesSet.contains(currentCategory)) {
					categoriesSet.add(currentCategory);
				}
			}

			// Create a new question set
			ArrayList<QuinzicalTuple> questionSet = new ArrayList<QuinzicalTuple>();
			for (String category : categoriesSet) {
				ArrayList<QuinzicalTuple> questionSetCurrentCategory = new ArrayList<QuinzicalTuple>();
				ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();

				for (QuinzicalTuple question : this.model.getQuestions()) {
					if (question.getCategory().equals(category)) {
						filteredQuestions.add(question);
					}
				}

				while (questionSetCurrentCategory.size() < 5) {
					int nextRandBounded = Math.abs(rand.nextInt() % (filteredQuestions.size() - 1));
					QuinzicalTuple currentQuestion = filteredQuestions.get(nextRandBounded);
					if (!questionSetCurrentCategory.contains(currentQuestion)) {
						// Size increases by one (1) here.
						questionSetCurrentCategory.add(currentQuestion);

						// Loop ticks over to next category an size 5.
						if (questionSetCurrentCategory.size() == 5) {
							questionSet.addAll(questionSetCurrentCategory);
						}
					}
				}

				// Finally add those questions
				this.model.setGameQuestions(questionSet);
			}
		}

		// Fetch the names of the question cateogories
		ArrayList<String> questionCategories = new ArrayList<String>();
		for (QuinzicalTuple question : this.model.getGameQuestions()) {
			if (!questionCategories.contains(question.getCategory())) {
				questionCategories.add(question.getCategory());
			}
		}

		// Add the questions to the grid. Only mark the lowest unanswered question as a
		// 'active' question to answer.
		this.gridPanePoints.setAlignment(Pos.CENTER);
		int r = 0;
		int c = 0;
		for (String category : questionCategories) {
			boolean flag = false;
			Label label = new Label(category);
			label.setWrapText(true);
			label.setPrefWidth(200);
			label.setStyle("-fx-font-weight: bold; -fx-text-fill: #0b2247; -fx-font-size: 18px;");
			label.setAlignment(Pos.CENTER);
			label.setTextAlignment(TextAlignment.CENTER);
			this.gridPanePoints.add(label, c, r);
			r++;
			ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : this.model.getGameQuestions()) {
				if (!filteredQuestions.contains(question) && category.equals(question.getCategory())) {
					filteredQuestions.add(question);
				}
			}

			// gameQuestions only those that are not completed
			// sort questions by worth lowest worth first
			Collections.sort(filteredQuestions, ((x, y) -> {
				return Integer.compare(((QuinzicalTuple) x).getWorth(), ((QuinzicalTuple) y).getWorth());
			}));

			for (QuinzicalTuple question : filteredQuestions) {
				Button button = new Button("$" + question.getWorth());
				button.setStyle("-fx-background-color: #00c3b1; -fx-background-radius: 30; -fx-text-fill: #f2fff3;"
						+ " -fx-font-weight: bold; -fx-font-size: 16px;");
				if (question.getCompleted() == true || flag == true) {
					button.setStyle("-fx-background-color: #0b2247; -fx-background-radius: 30; -fx-font-size: 16px;"
							+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
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

				if (question.getCompleted() == false) {
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
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
