package model;

public class JepordayTuple {
	public final String category; // first field of a Triplet
	public final String worth; // second field of a Triplet
	public final String question; // third field of a Triplet
	public final String answer; // third field of a Triplet
	public Boolean completed; // third field of a Triplet

	// Constructs a new Triplet with the given values
	public JepordayTuple(String first, String second, String third, String fourth, Boolean fifth) {
		this.category = first;
		this.worth = second;
		this.question = third;
		this.answer = fourth;
		this.completed = fifth;
	}

	public JepordayTuple(String name, String[] xs, Boolean fifth) {
		this.category = name;
		this.worth = xs[0];
		this.question = xs[1];
		this.answer = xs[2];
		this.completed = fifth;
	}

	public JepordayTuple(String[] xs) {
		this.category = xs[0];
		this.worth = xs[1];
		this.question = xs[2];
		this.answer = xs[3];
		this.completed = Boolean.parseBoolean(xs[4]);
	}

	@Override
	public boolean equals(Object o) {
		/* Checks specified object is "equal to" current object or not */

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		JepordayTuple tuple = (JepordayTuple) o;

		// call equals() method of the underlying objects
		if (!category.equals(tuple.category) || !worth.equals(tuple.worth) || !question.equals(tuple.question)
				|| !answer.equals(tuple.answer) || !completed.equals(tuple.completed))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = category.hashCode();
		result = 31 * result + worth.hashCode();
		result = 31 * result + question.hashCode();
		result = 31 * result + answer.hashCode();
		result = 31 * result + completed.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return category + "," + worth + "," + question + "," + answer + "," + completed;
	}
}