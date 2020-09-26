package model;

import java.io.FileReader;

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
	private final String answer;
	private Boolean completed;
	private Boolean correctlyAnswered;

	public QuinzicalTuple(String category, String question, int worth, String answer, Boolean completed, Boolean correctlyAnswered) {
		this.category = category;
		this.worth = worth;
		this.question = question;
		this.answer = answer;
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

	public String getAnswer() {
		return answer;
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

		if (!category.equals(tuple.category) || !question.equals(tuple.question)
				|| !answer.equals(tuple.answer) || !completed.equals(tuple.completed)
				|| !correctlyAnswered.equals(tuple.correctlyAnswered)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = category.hashCode();
		result = 31 * result + this.question.hashCode();
		result = 31 * result + this.answer.hashCode();
		result = 31 * result + this.completed.hashCode();
		result = 31 * result + this.correctlyAnswered.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return this.category + "," + this.worth + "," + this.question + "," + this.answer + "," + this.completed + ","
				+ this.correctlyAnswered;
	}

	public QuinzicalTuple fromJSONString(String xs) {
			try {
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(xs);
				return new QuinzicalTuple(
					(String) obj.get("category"),
					(String) obj.get("question"),
					((Long) obj.get("worth")).intValue(),
					(String) obj.get("answer"),
					(Boolean) obj.get("completed"),
					(Boolean) obj.get("correctlyAnswered")
					);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}

	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("category", this.getCategory());
		obj.put("question", this.getQuestion());
		obj.put("worth", this.getWorth());
		obj.put("answer", this.getAnswer());
		obj.put("completed", this.getCorrectlyAnswered());
		return obj.toJSONString();
	}
}