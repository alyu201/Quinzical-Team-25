package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
	private JeopardyTuple currentQuestion;
	private int winnings;

	private MainModel() {
		this.questions = new ArrayList<JeopardyTuple>();
		this.winnings = 0;
		this.currentQuestion = null;
		getState();
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

	/**
	 * Attempt to fetch the game state from a defined state file. If this state file
	 * does not exist then fallback to reading state from the given jeopardy
	 * categories folder
	 */
	public void getState() {
		try {
			if (STATE_FILE.exists()) {
				// read from state file
				try {
					Scanner scanner = new Scanner(STATE_FILE);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						if (!scanner.hasNextLine()) {
							winnings = Integer.valueOf(line);
						} else {
							questions.add(new JeopardyTuple(line.split(",")));
						}
					}
					scanner.close();
				} catch (IOException e) {
					System.out.println("Error reading from state file: " + e.toString());
				}
			} else {
				// read from categories
				if (CATEGORIES_DIRECTORY.exists() && CATEGORIES_DIRECTORY.isDirectory()) {
					Arrays.stream(CATEGORIES_DIRECTORY.listFiles()).forEach(file -> {
						try {
							Scanner scanner = new Scanner(file);
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
								this.questions.add(new JeopardyTuple(file.getName(), line.split(","), false, false));
							}
							winnings = 0;
							scanner.close();
						} catch (IOException e) {
							System.out.println(e.toString());
						}
					});
				} else {
					System.out.println(
							"Neither the state file or categories folder exist, are you sure you are running this in the correct directory?");
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading from categories folder or state file: " + e.toString());
		}
	}

	/**
	 * Write the state of the model to disk
	 */
	public void putState() {
		try {
			STATE_FILE.createNewFile();
			FileWriter fw = new FileWriter(STATE_FILE);
			for (JeopardyTuple quad : this.questions) {
				fw.write(String.format("%s\n", quad.toString()));
			}
			fw.write(String.format("%s\n", Integer.toString(winnings)));
			fw.close();
		} catch (IOException e) {
			System.out.println("Error writing state to file: " + e.toString());
		}
	}

	/**
	 * Reset the state of the model and write this fresh state to disk
	 */
	public void resetState() {
		if (STATE_FILE.exists()) {
			STATE_FILE.delete();
		}
		this.questions = new ArrayList<JeopardyTuple>();
		this.winnings = 0;
		this.getState();
		this.putState();

	}

	/**
	 * Search for a question in the model and mark it completed
	 * 
	 * @param question
	 */
	public void setCompleted(JeopardyTuple question) {
		for (JeopardyTuple q : this.questions) {
			if (q.equals(question) && q.getCompeted().equals(false)) {
				q.setCompeted(true);
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
