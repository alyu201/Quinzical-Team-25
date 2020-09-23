package model;

/**
 * A type that supports conversion from and to JSON format
 */
public interface JSONString<T> {
	public T fromJSONString(String xs);

	public String toJSONString();
}
