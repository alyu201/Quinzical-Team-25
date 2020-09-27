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
public class MainModel {

	private static final String STATE_FILENAME = "state.json";

	private static MainModel mainModel;
	private ArrayList<QuinzicalTuple> questions;
	private ArrayList<String> categories;
	private Leaderboard leaderboard;
	private QuinzicalTuple currentQuestion;
	private String currentCategory;
	private Settings settings;
	private String name;
	private int winnings;

	public MainModel(ArrayList<QuinzicalTuple> questions, ArrayList<String> categories, Leaderboard leaderboard,
			QuinzicalTuple currentQuestion, String currentCategory, Settings settings, String name, int winnings) {
		super();
		this.questions = questions;
		this.categories = categories;
		this.leaderboard = leaderboard;
		this.currentQuestion = currentQuestion;
		this.currentCategory = currentCategory;
		this.settings = settings;
		this.name = name;
		this.winnings = winnings;
	}

	public static MainModel getMainModel() {
		if (mainModel == null) {
			mainModel = fromJSONFile();
			return mainModel;
		} else {
			return mainModel;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public String getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(String currentCategory) {
		this.currentCategory = currentCategory;
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

	@SuppressWarnings("unchecked")
	public String toJSONString() {
		JSONObject obj = new JSONObject();

		// categories
		ArrayList<String> unique = new ArrayList<String>();
		this.getQuestions().forEach(tuple -> {
			if (!unique.contains(tuple.getCategory())) {
				unique.add(tuple.getCategory());
			}
		});
		JSONArray categories = new JSONArray();
		unique.forEach(x -> {
			categories.add(x);
		});

		obj.put("categories", categories);

		// questions
		JSONArray questions = new JSONArray();
		this.getQuestions().forEach(tuple -> {
			JSONObject question = new JSONObject();
			question.put("category", tuple.getCategory());
			question.put("question", tuple.getQuestion());
			question.put("worth", tuple.getWorth());
			question.put("completed", tuple.getCompleted());
			question.put("correctlyAnswered", tuple.getCorrectlyAnswered());

			questions.add(question);

		});
		obj.put("questions", questions);

		// leaderboard
		JSONArray leaderboard = new JSONArray();
		this.getLeaderboard().getMap().forEach((key, value) -> {
			JSONObject map = new JSONObject();
			map.put("name", key);
			map.put("score", value);
			leaderboard.add(map);
		});
		obj.put("leaderboard", leaderboard);

		// settings
		JSONObject settings = new JSONObject();
		settings.put("volume", this.getSettings().getVolume());
		settings.put("voiceType", this.getSettings().getVoiceType());
		settings.put("speed", this.getSettings().getSpeed());

		obj.put("settings", settings);

		// name
		obj.put("name", this.getName());

		// winnings
		obj.put("winnings", this.getWinnings());
		
		//currentCategory
		obj.put("currentCategory", this.getCurrentCategory());

		return obj.toJSONString();
	}

	public static MainModel fromJSONString(String xs) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(xs);
			// categories
			JSONArray JSONcategories = (JSONArray) obj.get("categories");
			ArrayList<String> categories = new ArrayList<String>();
			JSONcategories.forEach(category -> {
				categories.add((String) category);
			});

			// questions
			JSONArray JSONquestions = (JSONArray) obj.get("questions");
			ArrayList<QuinzicalTuple> questions = new ArrayList<QuinzicalTuple>();
			JSONquestions.forEach(question -> {
				questions.add(new QuinzicalTuple((String) ((JSONObject) question).get("category"),
						(String) ((JSONObject) question).get("question"),
						((Long) ((JSONObject) question).get("worth")).intValue(),
						(String) ((JSONObject) question).get("answer"),
						(Boolean) ((JSONObject) question).get("completed"),
						(Boolean) ((JSONObject) question).get("correctlyAnswered")));
			});

			// name
			String name = (String) obj.get("name");

			// winnings
			int winnings = ((Long) obj.get("winnings")).intValue();

			// leaderboard
			JSONArray JSONleaderboard = (JSONArray) obj.get("leaderboard");
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			JSONleaderboard.forEach(store -> {
				map.put((String) ((JSONObject) store).get("name"),
						((Long) ((JSONObject) store).get("score")).intValue());
			});
			Leaderboard leaderboard = new Leaderboard(map);

			// settings
			JSONObject JSONsettings = (JSONObject) obj.get("settings");
			Settings settings = new Settings((String) JSONsettings.get("voiceType"),
					((Long) JSONsettings.get("speed")).intValue(), ((Long) JSONsettings.get("volume")).intValue());

			// currentCategory
			String currentCategory = (String) obj.get("name");

			return new MainModel(questions, categories, leaderboard, null, currentCategory, settings, name, winnings);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void toJSONFile() {
		try (FileWriter file = new FileWriter(STATE_FILENAME)) {
			file.write(this.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MainModel fromJSONFile() {
		JSONParser parser = new JSONParser();
		try (Reader reader = new FileReader(STATE_FILENAME)) {
			JSONObject obj = (JSONObject) parser.parse(reader);
			return fromJSONString(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

}
