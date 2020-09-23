package model;

/**
 * A type that supports conversion from and to JSON format
 * @author wqsz7xn
 *
 */
public interface JSONable {
	public void fromJSONFile();
	public void toJSONFile();
}
