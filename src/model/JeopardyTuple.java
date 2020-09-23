package model;

/**
 * jeopardyTuple represents a mutable tuple of the a jeopardy category,
 * question, question worth, question answer, completion status and correctly
 * answered status
 */
public class JeopardyTuple implements JSONable {
	private final String category;
	private final String question;
	private final String worth;
	private final String answer;
	private Boolean completed;
	private Boolean correctlyAnswered;

	public JeopardyTuple(String name, String[] xs, Boolean fifth, Boolean sixth) {
		this.category = name;
		this.worth = xs[0];
		this.question = xs[1];
		this.answer = xs[2];
		this.completed = fifth;
		this.correctlyAnswered = sixth;
	}

	public JeopardyTuple(String[] xs) {
		this.category = xs[0];
		this.worth = xs[1];
		this.question = xs[2];
		this.answer = xs[3];
		this.completed = Boolean.parseBoolean(xs[4]);
		this.correctlyAnswered = Boolean.parseBoolean(xs[5]);
	}

	public String getCategory() {
		return this.category;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getWorth() {
		return this.worth;
	}

	public String getAnswer() {
		return this.answer;
	}

	public Boolean getCompeted() {
		return this.completed;
	}

	public void setCompeted(Boolean c) {
		this.completed = c;
	}

	public Boolean getCorrectlyAnswered() {
		return this.correctlyAnswered;
	}

	public void setCorrectlyAnswered(Boolean c) {
		this.correctlyAnswered = c;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		JeopardyTuple tuple = (JeopardyTuple) obj;

		if (!category.equals(tuple.category) || !worth.equals(tuple.worth) || !question.equals(tuple.question)
				|| !answer.equals(tuple.answer) || !completed.equals(tuple.completed)
				|| !correctlyAnswered.equals(tuple.correctlyAnswered)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = category.hashCode();
		result = 31 * result + this.worth.hashCode();
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

	@Override
	public void fromJSONFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toJSONFile() {
		// TODO Auto-generated method stub
		
	}
}