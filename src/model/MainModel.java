package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * MainModel is a singleton providing a shared model for the jeopardy game. It
 * contains the state of all jeopardy tuples, currently selected question and
 * the games current winnings.
 */
public class MainModel {

	// Constants
	private static File STATE_FILE = new File("./state");
	private static File LEADERBOARD_FILE = new File("./leaderboard.json");
	private static File CATEGORIES_DIRECTORY = new File("./categories");

	private static MainModel mainModel;
	private ArrayList<JeopardyTuple> questions;
	private HashMap<String, Integer> leaderboard;
	private JeopardyTuple currentQuestion;
	private Settings settings;
	private int winnings;

	private MainModel() {
		this.questions = new ArrayList<JeopardyTuple>();
		this.winnings = 0;
		this.currentQuestion = null;
		this.leaderboard = new HashMap<String, Integer>();
		this.getLeaderboardState();
		this.settings = new Settings();
	}

	// Singleton
	public static MainModel getMainModel() {
		if (mainModel == null) {
			mainModel = new MainModel();
			return mainModel;
		} else {
			return mainModel;
		}
	}

	public HashMap<String, Integer> getLeaderboard() {
		return this.leaderboard;
	}

	public void setLeaderboard(HashMap<String, Integer> map) {
		this.leaderboard = map;
	}

	/**
	 * Retrieve leaderboard from file TODO: file existence check
	 */
	public void getLeaderboardState() {
		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader(LEADERBOARD_FILE)) {

			JSONObject obj = (JSONObject) parser.parse(reader);

			obj.forEach((key, value) -> {
				// TODO: this is kinda backwards man
				this.leaderboard.put((String) key, Integer.valueOf(value.toString()));
			});
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write leaderboard to file TODO: existence
	 */
	public void putLeaderboardState() {
		JSONObject obj = new JSONObject();
		this.leaderboard.forEach((key, value) -> {
			obj.put(key, value);
		});
		try (FileWriter file = new FileWriter(LEADERBOARD_FILE)) {
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	/**
	 * Search for a question in the model and mark it completed
	 * 
	 * @param question
	 */
	public void setCompleted(JeopardyTuple question) {
		for (JeopardyTuple q : this.questions) {
			if (q.equals(question) && q.getCompleted().equals(false)) {
				q.setCompleted(true);
				int index = this.questions.indexOf(q);
				this.questions.set(index, q);
				break;
			}
		}
	}

	public ArrayList<JeopardyTuple> getQuestions() {
		return this.questions;
	}

	public void setQuestions(ArrayList<JeopardyTuple> xs) {
		this.questions = xs;
	}

	public int getWinnings() {
		return this.winnings;
	}

	public void setWinnings(int w) {
		this.winnings = w;
	}

	public void addWinnings(int w) {
		this.winnings += w;
	}

	public void setCurrentQuestion(JeopardyTuple q) {
		this.currentQuestion = q;
	}

	public JeopardyTuple getCurrentQuestion() {
		return this.currentQuestion;
	}
}
