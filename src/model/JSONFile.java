package model;

/**
 * A type that supports conversion from and to JSON format
 */
public interface JSONFile<T> {
	public T fromJSONFile();
	public void toJSONFile();
}
