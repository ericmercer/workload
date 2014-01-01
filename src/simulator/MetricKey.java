package simulator;

public class MetricKey implements Comparable<MetricKey>{
	private int _time = -1;
	private String _actorName = "";
	private String _stateName = "";
	
	public int getTime() {
		return _time;
	}
	
	public String getActor() {
		return _actorName;
	}
	
	public String getState() {
		return _stateName;
	}
	
	public MetricKey(int time, String actor_name, String state_name){
		_time = time;
		_actorName = actor_name;
		_stateName  = state_name;
	}
	
	public String toString() {
		String result = "";
		
		result += "(";
		if(_time != -1){
			result += _time;
		}
		result += " ";
		if(_actorName != null){
			result += _actorName;
		}
		result += " ";
		if (_stateName != null){
			result += _stateName;
		}
		result += ")";
		
		return result;
	}

	@Override
	public int compareTo(MetricKey other) {
		if (_time < other._time) {
			return -1;
		} else {
			return 1;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_actorName == null) ? 0 : _actorName.hashCode());
		result = prime * result + ((_stateName == null) ? 0 : _stateName.hashCode());
		result = prime * result + _time;
		return result;
	}
}
