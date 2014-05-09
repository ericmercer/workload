package simulator;



import simulator.Simulator.RunsJPF;
import simulator.WorkloadManager;

public class MetricManager {
	private static MetricManager _metricManager;
	private MetricManager ( ) {
		
	}

	public static MetricManager getInstance() {
		if ( _metricManager == null )
			_metricManager = new MetricManager();
		

		return _metricManager;
	}
	
	//these are for the non JPF runs
	public void setTransitionDuration(int time, String actorName, String stateName, int duration,WorkloadManager manager) {
		manager.executeInstruction(new MetricKey(time, actorName, stateName, MetricKey.Type.TRANSITION_DURATION),new Metric(duration, duration));


	}
	
	public void setEnabledTransition(int time, String actorName, String stateName, int transition,WorkloadManager manager) {
		manager.executeInstruction(new MetricKey(time, actorName, stateName, MetricKey.Type.ENABLED_TRANSITION),new Metric(transition, transition));
	

	}
	
	public void setActiveInput(int time, String actorName, String stateName, String input,WorkloadManager manager) {
		
		manager.executeInstruction(new MetricKey(time, actorName, stateName, MetricKey.Type.ACTIVE_INPUT),new Metric(1, input));
		

	}

	public void setActiveOutput(int elapsedTime, String name, String name2, String name3,WorkloadManager manager) {
		manager.executeInstruction(new MetricKey(elapsedTime,name,name2,MetricKey.Type.ACTIVE_OUTPUT), new Metric(1,name3));
	}

	public void endSimulation(String pathData,WorkloadManager manager) {
		manager.storePath(pathData);

	}
	
	
	
	//these below are for JPF
	
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

	}
	public void endSimulation(String pathData) {
		//Do nothing, JPF handles this.
	}

}
