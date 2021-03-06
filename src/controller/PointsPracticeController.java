package controller;

import java.util.ArrayList;
import java.util.Random;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.MainModel;
import utilities.SceneManager;

/**
 * PointsPracticeController is the controller for PointsPracticeView. It is the secondary  
 * game mode of Quinzical. PointsPracticeController loads an existing
 * question set if one exists or creates a new set of questions from randomly
 * selected categories. PointsPlayController displays a grid of questions that
 * are neatly organized for the user to answer.
 */
public class PointsPracticeController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonEnter;

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

	@FXML
	private Button buttonReturnToMain;
	
	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu. If a set of questions has not been generated
	 * then it will generate a set. Otherwise it loads an existing question set from
	 * the main model.
	 */
	@FXML
	public void initialize() {
		this.model = MainModel.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.labelCategory.setText(this.model.getCurrentCategory());
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}

		// There is no existing practice question set yet
		if (!(this.model.getPracticeQuestions().size() == 0)) {

		} else {
			// Scope to tuples for currentCategory
			String currentCategory = this.model.getCurrentCategory();
			ArrayList<QuinzicalTuple> xs = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : this.model.getQuestions()) {
				if (question.getCategory().equals(currentCategory)) {
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
				int nextRand = Math.abs((rand.nextInt() % (xs.size())));
				QuinzicalTuple current = xs.get(nextRand);
				if (!questionSet.contains(current)) {
					questionSet.add(current);
					++i;
				}
			}

			// Sort questions by lowest worth first
			questionSet.sort((x, y) -> {
				if (x.getWorth() < y.getWorth()) {
					return -1;
				} else if (x.getWorth() > y.getWorth()) {
					return 1;
				} else {
					return 0;
				}
			});

			// set practice questions set
			this.model.setPracticeQuestions(questionSet);

		}

		// Add questions to screen
		int col = 0;
		for (QuinzicalTuple question : this.model.getPracticeQuestions()) {

			Button button = new Button("$" + question.getWorth());
			button.setPrefWidth(200);
			button.setPrefHeight(150);
			DropShadow dropshadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.2), 10, 0, 0, 0);
			button.setEffect(dropshadow);
			button.setTextAlignment(TextAlignment.CENTER);
			button.setStyle("-fx-background-color: #00C3B1; -fx-background-radius: 30; -fx-font-size: 20px;"
					+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
			if (question.getCompleted() == true) {
				button.setText(button.getText() + "\n(Completed)");
				button.setStyle("-fx-background-color: #0b2247; -fx-background-radius: 30; -fx-font-size: 20px;"
						+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
				button.setDisable(true);
			}
			button.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					model.setCompleted(GameType.PRACTICEMODULE, question);
					model.setCurrentQuestion(question);
					model.toJSONFile();
					SceneManager.changeScene(getClass().getResource("/view/QuestionView.fxml"), me);
				}
			});
			this.gridPanePoints.add(button, col, 0);
			col++;
		}
	}

	/**
	 * Return to the previous view which is the CategoryView.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonBack(Event e) {
		model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/CategoryView.fxml"), e);
	}

	/**
	 * Navigate to the screen to prompt the user to enter a user name.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	/**
	 * Opens the game settings pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	/**
	 * Opens the info pop-up window.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
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
