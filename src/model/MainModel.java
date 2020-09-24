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
	private static File CATEGORIES_DIRECTORY = new File("./categories");

	private static MainModel mainModel;
	private ArrayList<JeopardyTuple> questions;
	private Leaderboard leaderboard;
	private JeopardyTuple currentQuestion;
	private Settings settings;
	private int winnings;

	private MainModel() {
		this.questions = new ArrayList<JeopardyTuple>();
		this.winnings = 0;
		this.currentQuestion = null;
		this.leaderboard = new Leaderboard();
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

	public Settings getSettings() {
		return settings;
	}

	public Leaderboard getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(Leaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
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

}
