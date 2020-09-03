package model;

/**
 * JepordayTuple represents a mutable tuple of the a jeporday category,
 * question, question worth, question answer, completion status and correctly
 * answered status.
 * 
 */
public class JepordayTuple {
	public final String category;
	public final String question;
	public final String worth;
	public final String answer;
	public Boolean completed;
	public Boolean correctlyAnswered;

	public JepordayTuple(String first, String second, String third, String fourth, Boolean fifth, Boolean sixth) {
		this.category = first;
		this.worth = second;
		this.question = third;
		this.answer = fourth;
		this.completed = fifth;
		this.correctlyAnswered = sixth;
	}

	public JepordayTuple(String name, String[] xs, Boolean fifth, Boolean sixth) {
		this.category = name;
		this.worth = xs[0];
		this.question = xs[1];
		this.answer = xs[2];
		this.completed = fifth;
		this.correctlyAnswered = sixth;
	}

	public JepordayTuple(String[] xs) {
		this.category = xs[0];
		this.worth = xs[1];
		this.question = xs[2];
		this.answer = xs[3];
		this.completed = Boolean.parseBoolean(xs[4]);
		this.correctlyAnswered = Boolean.parseBoolean(xs[5]);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		JepordayTuple tuple = (JepordayTuple) obj;

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
}