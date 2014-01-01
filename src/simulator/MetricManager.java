package simulator;

public class MetricManager {
	
	private static MetricManager _metricManager;
	
	private MetricManager ( ) {
		
	}

	public static MetricManager getInstance() {
		if ( _metricManager == null )
			_metricManager = new MetricManager();
		return _metricManager;
	}
	
	public void setTransitionDuration(int time, String actorName, String stateName, int value) {
		//Do nothing, JPF handles this.
	}
	
	public void setEnabledTransitions(int time, String actorName, String stateName, int value) {
		//Do nothing, JPF handles this.
	}
	
	public void setActiveInputs(int time, String actorName, String stateName, int value) {
		//Do nothing, JPF handles this.
	}

	public void endSimulation() {
		//Do nothing, JPF handles this.
	}

}
