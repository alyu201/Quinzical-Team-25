package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainModel {

	private static File STATE_FILE = new File("./state");
	private static File CATEGORIES_DIRECTORY = new File("./categories");

	private ArrayList<JepordayTuple> questions;
	private int winnings;

	// Get state from STATE_FILE or fall back to categories folder
	public MainModel() {
		this.questions = new ArrayList<JepordayTuple>();
		this.winnings = 0;
		getState();
	}

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
							questions.add(new JepordayTuple(line.split(",")));
						}
					}
					scanner.close();
				} catch (IOException e) {
					System.out.println("bad boy!");
				}
			} else {
				// read from categories
				if (CATEGORIES_DIRECTORY.exists() && CATEGORIES_DIRECTORY.isDirectory()) {
					Arrays.stream(CATEGORIES_DIRECTORY.listFiles()).forEach(file -> {
						try {
							Scanner scanner = new Scanner(file);
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
								this.questions.add(new JepordayTuple(file.getName(), line.split(","), false));
							}
							winnings = 0;
							scanner.close();
						} catch (IOException e) {
							System.out.println("bad boy!");
						}
					});

				} else {
					System.out.println("Neither exist");
					// throw
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void putState() {
		try {
			STATE_FILE.createNewFile();
			FileWriter fw = new FileWriter(STATE_FILE);
			for (JepordayTuple quad : this.questions) {
				fw.write(String.format("%s\n", quad.toString()));
			}
			fw.write(String.format("%s\n", Integer.toString(winnings)));
			fw.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public void resetState() {
		if (STATE_FILE.exists()) {
			STATE_FILE.delete();
		}
		this.questions = new ArrayList<JepordayTuple>();
		this.winnings = 0;
		this.getState();
		this.putState();

	}

	public ArrayList<JepordayTuple> getQuestions() {
		return this.questions;
	}

	public void setQuestions(ArrayList<JepordayTuple> xs) {
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
	
	public void setCompleted(JepordayTuple question) {
		for(JepordayTuple q : this.questions) {
			if(q.equals(question) && q.completed.equals(false)) {
				q.completed = true;
				int index = this.questions.indexOf(q);
				this.questions.set(index, q);
				break;
			}
		}
	}
}
