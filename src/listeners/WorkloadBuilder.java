package listeners;

import java.util.Map.Entry;
import java.util.TreeMap;

import simulator.Metric;
import simulator.MetricKey;

public class WorkloadBuilder {

	public static String build( WorkloadPath path ) {
		
		String result = "time, resource data (Actor State [ActiveInput])*, resource workload, temporal data (Actor State [TransitionIndex])*, temporal workload, decision data (Actor State [TransitionDuration])*, decision workload";
		
		int currentTime = 0;
		int currentResourceWorkload = 0;
		String currentResourceData = "";
		int currentTemporalWorkload = 0;
		String currentTemporalData = "";
		int currentDecisionWorkload = 0;
		String currentDecisionData = "";
		String taskStarts = "";
		String taskStops = "";
		TreeMap<MetricKey, Metric> values = path.getValues();
		for( Entry<MetricKey, Metric> value : values.entrySet() ) {
			MetricKey metricKey = value.getKey();
			Metric metric = value.getValue();
			
			if ( currentTime != metricKey.getTime() ) {
				result += "\n" + currentTime + "," + currentResourceData + "," + currentResourceWorkload + "," + currentTemporalData + "," + currentTemporalWorkload + "," + currentDecisionData + "," + currentDecisionWorkload + "," + taskStarts + "," + taskStops;

				currentResourceWorkload = 0;
				currentResourceData = "";
				currentTemporalWorkload = 0;
				currentTemporalData = "";
				currentDecisionWorkload = 0;
				currentDecisionData = "";
				taskStarts = "";
				taskStops = "";
			}
			
//			System.out.println(metricKey.toString() + "\n\t" + metric.toString());
			
			currentTime = metricKey.getTime();
			
			if ( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT ) {
				currentResourceWorkload += metric.getValue();
				currentResourceData += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.TRANSITION_DURATION ) {
				currentTemporalWorkload += metric.getValue();
				currentTemporalData += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION ) {
				currentDecisionWorkload += metric.getValue();
				currentDecisionData += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.ACTIVE_OUTPUT ) {
				if ( metric.getData().toString().contains("_START_") ) {
					taskStarts += "(" + metric.getData() + ")";
				} else if ( metric.getData().toString().contains("_STOP_") ) {
					taskStops += "(" + metric.getData() + ")";
				}
			} 
		}
		
		return result;
	}
	
}
