package simulator;

import java.util.*;

public class Metric {

	private int _value = -1;
	private List<Object> _data = new ArrayList<Object>();
	
	public Metric(int value, Object data) {
		_value = value;
		_data.add(data);
	}
	
	public int getValue() {
		return _value;
	}
	
	public List<Object> getData() {
		return _data;
	}

	public void add(Metric other) {
		_value += other._value;
		_data.addAll(other.getData());
	}
	
	public String toString() {
		String result = Integer.toString(_value);
		
		for(Object data : _data)
			result += ", " + data;
		
		return result;
	}
}