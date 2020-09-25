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
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * MainModel is a singleton providing a shared model for the jeopardy game. It
 * contains the state of all jeopardy tuples, currently selected question and
 * the games current winnings.
 */
public class MainModel implements JSONString<MainModel>, JSONFile<MainModel> {

	private static final String STATE_FILENAME = "state.json";

	private static MainModel mainModel;
	private ArrayList<QuinzicalTuple> questions;
	private Leaderboard leaderboard;
	private QuinzicalTuple currentQuestion;
	private Settings settings;
	private int winnings;

	MainModel() {
		this.fromJSONFile();
	}

	private MainModel(ArrayList<QuinzicalTuple> questions, Leaderboard leaderboard, QuinzicalTuple currentQuestion,
			Settings settings, int winnings) {
		super();
		this.mainModel = this;
		this.questions = questions;
		this.leaderboard = leaderboard;
		this.currentQuestion = currentQuestion;
		this.settings = settings;
		this.winnings = winnings;
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

	public ArrayList<QuinzicalTuple> getQuestions() {
		return this.questions;
	}

	public void setQuestions(ArrayList<QuinzicalTuple> xs) {
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

	public void setCurrentQuestion(QuinzicalTuple q) {
		this.currentQuestion = q;
	}

	public QuinzicalTuple getCurrentQuestion() {
		return this.currentQuestion;
	}

	/**
	 * Search for a question in the model and mark it completed
	 * 
	 * @param question
	 */
	public void setCompleted(QuinzicalTuple question) {
		for (QuinzicalTuple q : this.questions) {
			if (q.equals(question) && q.getCompleted().equals(false)) {
				q.setCompleted(true);
				int index = this.questions.indexOf(q);
				this.questions.set(index, q);
				break;
			}
		}
	}

	@Override
	public MainModel fromJSONFile() {
		JSONParser parser = new JSONParser();
		try (Reader reader = new FileReader(STATE_FILENAME)) {
			JSONObject obj = (JSONObject) parser.parse(reader);
			return this.fromJSONString(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void toJSONFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public MainModel fromJSONString(String xs) {
			try {
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(xs);
				Object precast = obj.get("categories");
				//////////asdfasdfasd
				System.out.println(obj.get("categories"));
				return null;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		
		//filter for unique categories
		ArrayList<String> unique = new ArrayList<String>();
		this.getQuestions().forEach(tuple -> {
			if(!unique.contains(tuple.getCategory())) {
				unique.add(tuple.getCategory());
			}
		});

		obj.put("categories", JSONValue.toJSONString(unique));
		return obj.toJSONString();
	}

}
