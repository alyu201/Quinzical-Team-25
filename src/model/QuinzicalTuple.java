package model;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * jeopardyTuple represents a mutable tuple of the a jeopardy category,
 * question, question worth, question answer, completion status and correctly
 * answered status
 */
public class QuinzicalTuple {
	private final String category;
	private final String question;
	private final int worth;
	private final ArrayList<String> answers;
	private Boolean completed;
	private Boolean correctlyAnswered;

	
	public QuinzicalTuple(String category, String question, int worth, ArrayList<String> answers, Boolean completed,
			Boolean correctlyAnswered) {
		super();
		this.category = category;
		this.question = question;
		this.worth = worth;
		this.answers = answers;
		this.completed = completed;
		this.correctlyAnswered = correctlyAnswered;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Boolean getCorrectlyAnswered() {
		return correctlyAnswered;
	}

	public void setCorrectlyAnswered(Boolean correctlyAnswered) {
		this.correctlyAnswered = correctlyAnswered;
	}

	public String getCategory() {
		return category;
	}

	public String getQuestion() {
		return question;
	}

	public int getWorth() {
		return worth;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		QuinzicalTuple tuple = (QuinzicalTuple) obj;

		if (!category.equals(tuple.category) || !question.equals(tuple.question) || !answers.equals(tuple.answers)
				|| !completed.equals(tuple.completed) || !correctlyAnswered.equals(tuple.correctlyAnswered)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = category.hashCode();
		result = 31 * result + this.question.hashCode();
		result = 31 * result + this.answers.hashCode();
		result = 31 * result + this.completed.hashCode();
		result = 31 * result + this.correctlyAnswered.hashCode();
		return result;
	}

	@Override
	public String toString() {
		String monoid = "";
		for(String answer : this.answers) {
			monoid += answer + ", ";
		}
		return this.category + "," + this.worth + "," + this.question + "," + monoid + "," + this.completed + ","
				+ this.correctlyAnswered + "\n";
	}
}