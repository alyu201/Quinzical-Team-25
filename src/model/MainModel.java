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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GameMode.GameType;

/**
 * MainModel is a singleton providing a shared model for the jeopardy game. It
 * contains the state of all jeopardy tuples, currently selected question and
 * the games current winnings.
 */
public class MainModel {

	private static final String STATE_FILENAME = "state.json";

	private static MainModel mainModel;
	private ArrayList<QuinzicalTuple> questions;
	private ArrayList<QuinzicalTuple> gameQuestions;
	private ArrayList<QuinzicalTuple> practiceQuestions;
	private ArrayList<String> categories;
	private Leaderboard leaderboard;
	private QuinzicalTuple currentQuestion;
	private String currentCategory;
	private Settings settings;
	private StringProperty name = new SimpleStringProperty();
	private IntegerProperty practiceWinnings = new SimpleIntegerProperty();
	private IntegerProperty gameWinnings = new SimpleIntegerProperty();
	private GameType currentGameType;
	private boolean allCompletedGame;
	private boolean allCompletedPractice;
	// private GameState currentGameState;

	public MainModel(ArrayList<QuinzicalTuple> questions, ArrayList<QuinzicalTuple> gameQuestions,
			ArrayList<QuinzicalTuple> practiceQuestions, ArrayList<String> categories, Leaderboard leaderboard,
			QuinzicalTuple currentQuestion, String currentCategory, Settings settings, StringProperty name,
			IntegerProperty gameWinnings, IntegerProperty practiceWinnings, GameType currentGameMode,
			boolean allCompletedGame, boolean allCompletedPractice) {
		super();
		this.questions = questions;
		this.gameQuestions = gameQuestions;
		this.practiceQuestions = practiceQuestions;
		this.categories = categories;
		this.leaderboard = leaderboard;
		this.currentQuestion = currentQuestion;
		this.currentCategory = currentCategory;
		this.settings = settings;
		this.name = name;
		this.gameWinnings = gameWinnings;
		this.practiceWinnings = practiceWinnings;
		this.currentGameType = currentGameMode;
		this.allCompletedGame = allCompletedGame;
		this.allCompletedPractice = allCompletedPractice;
	}

	public static MainModel getMainModel() {
		if (mainModel == null) {
			mainModel = fromJSONFile();
			return mainModel;
		} else {
			return mainModel;
		}
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
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

	public IntegerProperty getGameWinnings() {
		return this.gameWinnings;
	}

	public void setGameWinnings(int w) {
		this.gameWinnings.set(w);
	}

	public void addGameWinnings(int w) {
		this.gameWinnings.set(gameWinnings.get() + w);
	}

	public IntegerProperty getPracticeWinnings() {
		return this.practiceWinnings;
	}

	public void setPracticeWinnings(int w) {
		this.practiceWinnings.set(w);
	}

	public void addPracticeWinnings(int w) {
		this.practiceWinnings.set(practiceWinnings.get() + w);
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

	public ArrayList<QuinzicalTuple> getGameQuestions() {
		return gameQuestions;
	}

	public void setGameQuestions(ArrayList<QuinzicalTuple> gameQuestions) {
		this.gameQuestions = gameQuestions;
	}

	public ArrayList<QuinzicalTuple> getPracticeQuestions() {
		return this.practiceQuestions;
	}

	public void setPracticeQuestions(ArrayList<QuinzicalTuple> practiceQuestions) {
		this.practiceQuestions = practiceQuestions;
	}

	public GameType getCurrentGameType() {
		return currentGameType;
	}

	public void setCurrentGameType(GameType currentGameType) {
		this.currentGameType = currentGameType;
	}

	public boolean getAllCompletedGame() {
		return this.allCompletedGame;
	}

	public void setAllCompletedGame(boolean allCompletedGame) {
		this.allCompletedGame = allCompletedGame;
	}

	public boolean getAllCompletedPractice() {
		return this.allCompletedPractice;
	}

	public void setAllCompletedPractice(boolean allCompletedPractice) {
		this.allCompletedPractice = allCompletedPractice;
	}

	/**
	 * Search for a question in the model and mark it completed
	 * 
	 * @param question
	 */
	public void setCompleted(GameType type, QuinzicalTuple question) {
		if (type.equals(GameType.GAMESMODULE)) {
			for (QuinzicalTuple q : this.gameQuestions) {
				if (q.equals(question) && q.getCompleted().equals(false)) {
					q.setCompleted(true);
					int index = this.gameQuestions.indexOf(q);
					this.gameQuestions.set(index, q);
					break;
				}
			}
		} else if (type.equals(GameType.PRACTICEMODULE)) {
			for (QuinzicalTuple q : this.practiceQuestions) {
				if (q.equals(question) && q.getCompleted().equals(false)) {
					q.setCompleted(true);
					int index = this.practiceQuestions.indexOf(q);
					this.practiceQuestions.set(index, q);
					break;
				}
			}

		} else {
			System.err.println("Illegal GameType (undefined): " + type.toString());
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
		for (QuinzicalTuple tuple : this.getQuestions()) {
			JSONObject question = new JSONObject();
			JSONArray answers = new JSONArray();

			question.put("category", tuple.getCategory());
			question.put("question", tuple.getQuestion());
			question.put("worth", tuple.getWorth());

			tuple.getAnswers().forEach(xs -> {
				answers.add((String) xs);
			});
			question.put("answers", answers);

			question.put("completed", tuple.getCompleted());
			question.put("correctlyAnswered", tuple.getCorrectlyAnswered());

			questions.add(question);

		}
		obj.put("questions", questions);

		// gameQuestions
		JSONArray gameQuestions = new JSONArray();
		for (QuinzicalTuple tuple : this.getGameQuestions()) {
			JSONObject question = new JSONObject();
			JSONArray answers = new JSONArray();

			question.put("category", tuple.getCategory());
			question.put("question", tuple.getQuestion());
			question.put("worth", tuple.getWorth());

			tuple.getAnswers().forEach(xs -> {
				answers.add(xs);
			});
			question.put("answers", answers);

			question.put("completed", tuple.getCompleted());
			question.put("correctlyAnswered", tuple.getCorrectlyAnswered());

			gameQuestions.add(question);

		}
		obj.put("gameQuestions", gameQuestions);

		// practiceQuestions
		JSONArray practiceQuestions = new JSONArray();
		for (QuinzicalTuple tuple : this.getPracticeQuestions()) {
			JSONObject question = new JSONObject();
			JSONArray answers = new JSONArray();

			question.put("category", tuple.getCategory());
			question.put("question", tuple.getQuestion());
			question.put("worth", tuple.getWorth());

			tuple.getAnswers().forEach(xs -> {
				answers.add((String) xs);
			});
			question.put("answers", answers);

			question.put("completed", tuple.getCompleted());
			question.put("correctlyAnswered", tuple.getCorrectlyAnswered());

			practiceQuestions.add(question);

		}
		obj.put("practiceQuestions", practiceQuestions);

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
		obj.put("name", this.getName().getValue());

		// gameWinnings
		obj.put("gameWinnings", this.gameWinnings.get());

		// practiceWinnings
		obj.put("practiceWinnings", this.practiceWinnings.get());

		// currentCategory
		obj.put("currentCategory", this.getCurrentCategory());

		// currentGameType
		obj.put("currentGameType", this.getCurrentGameType().toString());

		// allCompletedGame
		obj.put("allCompletedGame", this.getAllCompletedGame());

		// allCompletedPractice
		obj.put("allCompletedPractice", this.getAllCompletedPractice());

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
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
			JSONArray JSONQuestions = (JSONArray) obj.get("questions");
			ArrayList<QuinzicalTuple> questions = new ArrayList<QuinzicalTuple>();

			JSONQuestions.forEach(question -> {
				ArrayList<String> answers = new ArrayList<String>();
				((JSONArray) ((JSONObject) question).get("answers")).forEach(answer -> {
					answers.add((String) answer);
				});
				questions.add(new QuinzicalTuple((String) ((JSONObject) question).get("category"),
						(String) ((JSONObject) question).get("question"),
						((Long) ((JSONObject) question).get("worth")).intValue(), answers,
						(Boolean) ((JSONObject) question).get("completed"),
						(Boolean) ((JSONObject) question).get("correctlyAnswered")));
			});
			// gameQuestions
			JSONArray JSONGameQuestions = (JSONArray) obj.get("gameQuestions");
			ArrayList<QuinzicalTuple> gameQuestions = new ArrayList<QuinzicalTuple>();
			JSONGameQuestions.forEach(question -> {
				ArrayList<String> answers = new ArrayList<String>();
				((JSONArray) ((JSONObject) question).get("answers")).forEach(answer -> {
					answers.add((String) answer);
				});
				gameQuestions.add(new QuinzicalTuple((String) ((JSONObject) question).get("category"),
						(String) ((JSONObject) question).get("question"),
						((Long) ((JSONObject) question).get("worth")).intValue(), answers,
						(Boolean) ((JSONObject) question).get("completed"),
						(Boolean) ((JSONObject) question).get("correctlyAnswered")));
			});

			// practiceQuestions
			JSONArray JSONPracticeQuestions = (JSONArray) obj.get("practiceQuestions");
			ArrayList<QuinzicalTuple> practiceQuestions = new ArrayList<QuinzicalTuple>();
			JSONPracticeQuestions.forEach(question -> {
				ArrayList<String> answers = new ArrayList<String>();
				((JSONArray) ((JSONObject) question).get("answers")).forEach(answer -> {
					answers.add((String) answer);
				});
				practiceQuestions.add(new QuinzicalTuple((String) ((JSONObject) question).get("category"),
						(String) ((JSONObject) question).get("question"),
						((Long) ((JSONObject) question).get("worth")).intValue(), answers,
						(Boolean) ((JSONObject) question).get("completed"),
						(Boolean) ((JSONObject) question).get("correctlyAnswered")));
			});

			// name
			StringProperty name = new SimpleStringProperty((String) obj.get("name"));

			// gameWinnings
			IntegerProperty gameWinnings = new SimpleIntegerProperty(((Long) obj.get("gameWinnings")).intValue());

			// practiceWinnings
			IntegerProperty practiceWinnings = new SimpleIntegerProperty(
					((Long) obj.get("practiceWinnings")).intValue());

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
			String currentCategory = (String) obj.get("currentCategory");

			// currentGameMode
			GameType currentGameType = GameType.valueOf((String) obj.get("currentGameType"));

			// allCompletedGame
			boolean allCompletedGame = (boolean) obj.get("allCompletedGame");

			// allCompletedPractice
			boolean allCompletedPractice = (boolean) obj.get("allCompletedPractice");

			return new MainModel(questions, gameQuestions, practiceQuestions, categories, leaderboard, null,
					currentCategory, settings, name, gameWinnings, practiceWinnings, currentGameType, allCompletedGame,
					allCompletedPractice);

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
