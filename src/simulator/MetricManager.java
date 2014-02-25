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
	
	public void setTransitionDuration(int time, String actorName, String stateName, int duration) {
		//Do nothing, JPF handles this.
	}
	
	public void setEnabledTransition(int time, String actorName, String stateName, int transition) {
		//Do nothing, JPF handles this.
	}
	
	public void setActiveInput(int time, String actorName, String stateName, String input) {
		//Do nothing, JPF handles this.
	}

	public void setActiveOutput(int elapsedTime, String name, String name2, String name3) {
		//Do nothing, JPF handles this.
	}

	public void endSimulation(String pathData) {
		//Do nothing, JPF handles this.
	}

}
