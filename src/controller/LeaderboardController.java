package controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Leaderboard;
import model.MainModel;
import utilities.SceneManager;

public class LeaderboardController {

	private MainModel model;

	@FXML
	ListView<String> rankList;

	@FXML
	ListView<String> nameList;

	@FXML
	ListView<String> scoreList;

	@FXML
	ListView<String> testList;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonSettings;

	public void initialize() {
		this.model = model.getMainModel();
		if(this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}

		// Populate listView
		ObservableList<String> userRank = FXCollections.observableArrayList();
		ObservableList<String> userName = FXCollections.observableArrayList();
		ObservableList<String> userScore = FXCollections.observableArrayList();
		HashMap<String,Integer> map = this.model.getLeaderboard().getMap();
		List<String> keys = new ArrayList<String>(map.keySet());
		for (int i = 0; i < keys.size(); i++) {
			userRank.add(Integer.toString(i+1));
			userName.add(keys.get(i));
			userScore.add("$" + map.get(keys.get(i)).toString());
		}
		rankList.setItems(userRank);
		nameList.setItems(userName);
		scoreList.setItems(userScore);
	}

	@FXML
	public void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}
}
