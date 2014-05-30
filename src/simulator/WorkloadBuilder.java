//package simulator;
//
//import java.util.Map.Entry;
//import java.util.TreeMap;
//
//public class WorkloadBuilder {
//
//	public static String build( WorkloadPath path ) {
//
//		TreeMap<MetricKey, Metric> values = path.getValues();
//
//		String result = "time, Active Inputs (Actor State [ActiveInput])*, Total Active Inputs, Enabled Transitions (Actor State [NumberOfEnabledTransitions])*, Total Enabled Transitions, Transition Durations (Actor State [TransitionDuration])*, Total Transitions, TaskStarts, TaskStops, Op Tempo";
//
//		int currentTime = 0;
//		int totalTime = values.lastEntry().getKey().getTime();
//		double window = Math.floor((totalTime / 10));
//		int lastTime = -1;
//
//		int totalActiveInputs = 0;
//		String activeInputs = "";
//		int totalEnabledTransitions = 0;
//		String enabledTransitions = "";
//		int totalTransitionDurations = 0;
//		String transitionDurations = "";
//		String taskStarts = "";
//		String taskStops = "";
//		int firedTransitions = 0;
//		double totalOpTempo = 0;
//		int interval = 1;
//		for( Entry<MetricKey, Metric> value : values.entrySet() ) {
//			MetricKey metricKey = value.getKey();
//			Metric metric = value.getValue();
//
//			if ( currentTime != metricKey.getTime() ) {
//				result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," 
//						+ enabledTransitions + "," + totalEnabledTransitions + "," + transitionDurations
//						+ "," + totalTransitionDurations + "," + taskStarts + "," + taskStops + "," + totalOpTempo;
//
//				totalActiveInputs = 0;
//				activeInputs = "";
//				totalTransitionDurations = 0;
//				transitionDurations = "";
//				totalEnabledTransitions = 0;
//				enabledTransitions = "";
//				taskStarts = "";
//				taskStops = "";
//				totalOpTempo = 0;
//
//				currentTime = metricKey.getTime();
//
//			}
//
//			if ( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT ) {
//				totalActiveInputs += metric.getValue();
//				activeInputs += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
//			} else if ( metricKey.getType() == MetricKey.Type.TRANSITION_DURATION ) {
//				totalTransitionDurations += metric.getValue();
//				transitionDurations += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
//				firedTransitions++;
//			} else if (metricKey.getType() == MetricKey.Type.OP_TEMPO) {
//				totalOpTempo += metric.getValue();
//			}
//			else if ( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION ) {
//				totalEnabledTransitions += metric.getValue();
//				enabledTransitions += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
//			} else if ( metricKey.getType() == MetricKey.Type.ACTIVE_OUTPUT ) {
//				if ( metric.getData().toString().contains("_START_") ) {
//					taskStarts += "(" + metric.getData() + ")";
//				}else if ( metric.getData().toString().contains("_STOP_") ) {
//					taskStops += "(" + metric.getData() + ")";
//				}
//			} 
//
//			lastTime = currentTime;
//		}
//		double opTempo = firedTransitions;// / window;
//		result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," + enabledTransitions 
//				+ "," + totalEnabledTransitions + "," + transitionDurations + "," + totalTransitionDurations + "," 
//				+ taskStarts + "," + taskStops + "," + totalOpTempo;
//		return result;
//	}
//
//}
package simulator;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.TreeMap;

public class WorkloadBuilder {

	public static String build( WorkloadPath path ) {

		TreeMap<MetricKey, Metric> values = path.getValues();

		String result = "Time,Active Inputs (Actor State [ActiveInput])*,Total Active Inputs,Enabled Transitions (Actor State [NumberOfEnabledTransitions])*,Total Enabled Transitions,Transition Durations (Actor State [TransitionDuration])*,Total Transitions,TaskStarts,TaskStops,Op Tempo";

		int currentTime = 0;
		int totalTime = values.lastEntry().getKey().getTime();
		double window = Math.floor((totalTime / 10));
		int lastTime = -1;

		int totalActiveInputs = 0;
		String activeInputs = "";
		int totalEnabledTransitions = 0;
		String enabledTransitions = "";
		int totalTransitionDurations = 0;
		String transitionDurations = "";
		String taskStarts = "";
		String taskStops = "";
		String activeOutput = "";
		int firedTransitions = 0;
		int totalOpTempo = 0;
		int interval = 1;
		for( Entry<MetricKey, Metric> value : values.entrySet() ) {
			MetricKey metricKey = value.getKey();
			Metric metric = value.getValue();

			if ( currentTime != metricKey.getTime() ) {
				result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," 
						+ enabledTransitions + "," + totalEnabledTransitions + "," + transitionDurations
						+ "," + totalTransitionDurations + "," + taskStarts + "," + taskStops + "," + totalOpTempo + activeOutput;

				totalActiveInputs = 0;
				activeInputs = "";
				totalTransitionDurations = 0;
				transitionDurations = "";
				totalEnabledTransitions = 0;
				enabledTransitions = "";
				taskStarts = "";
				taskStops = "";
				totalOpTempo = 0;
				activeOutput = "";
				currentTime = metricKey.getTime();

			}

			if ( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT ) {
				totalActiveInputs += metric.getValue();
				activeInputs += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.TRANSITION_DURATION ) {
				totalTransitionDurations += metric.getValue();
				transitionDurations += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
				firedTransitions++;
			} else if (metricKey.getType() == MetricKey.Type.OP_TEMPO) {
				totalOpTempo += metric.getValue();
			}
			else if ( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION ) {
				totalEnabledTransitions += metric.getValue();
				enabledTransitions += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.ACTIVE_OUTPUT ) {
				if ( metric.getData().toString().contains("_START_") ) {
					taskStarts += "(" + metric.getData() + ")";
				}else if ( metric.getData().toString().contains("_STOP_") ) {
					taskStops += "(" + metric.getData() + ")";
				}
				else
					activeOutput+= "(" + metric.getData() + ")";
			} 

			lastTime = currentTime;
		}
		double opTempo = firedTransitions;// / window;
		result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," + enabledTransitions 
				+ "," + totalEnabledTransitions + "," + transitionDurations + "," + totalTransitionDurations + "," 
				+ taskStarts + "," + taskStops + "," + totalOpTempo+","+activeOutput;

		return result;
	}

	public static String buildActor(TreeMap<MetricKey, Metric> values ) {

		String result = "Time,Active Inputs (Actor State [ActiveInput])*,Total Active Inputs,Enabled Transitions (Actor State [NumberOfEnabledTransitions])*,Total Enabled Transitions,Transition Durations (Actor State [TransitionDuration])*,Total Transitions,TaskStarts,TaskStops,Op Tempo,Active Output";

		int currentTime = 0;
		int totalTime = values.lastEntry().getKey().getTime();
		double window = Math.floor((totalTime / 10));
		int lastTime = -1;

		int totalActiveInputs = 0;
		String activeInputs = "";
		int totalEnabledTransitions = 0;
		String enabledTransitions = "";
		int totalTransitionDurations = 0;
		String transitionDurations = "";
		String taskStarts = "";
		String taskStops = "";
		String activeOutput = "";
		int firedTransitions = 0;
		int totalOpTempo = 0;
		int interval = 1;
		for( Entry<MetricKey, Metric> value : values.entrySet() ) {
			MetricKey metricKey = value.getKey();
			Metric metric = value.getValue();

			if ( currentTime != metricKey.getTime() ) {
				result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," 
						+ enabledTransitions + "," + totalEnabledTransitions + "," + transitionDurations
						+ "," + totalTransitionDurations + "," + taskStarts + "," + taskStops + "," + totalOpTempo+","+activeOutput;

				totalActiveInputs = 0;
				activeInputs = "";
				totalTransitionDurations = 0;
				transitionDurations = "";
				totalEnabledTransitions = 0;
				enabledTransitions = "";
				taskStarts = "";
				taskStops = "";
				totalOpTempo = 0;
				activeOutput = "";
				
				currentTime = metricKey.getTime();

			}

			if ( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT ) {
				totalActiveInputs += metric.getValue();
				activeInputs += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.TRANSITION_DURATION ) {
				totalTransitionDurations += metric.getValue();
				transitionDurations += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
				firedTransitions++;
			} else if (metricKey.getType() == MetricKey.Type.OP_TEMPO) {
				totalOpTempo += metric.getValue();
			}
			else if ( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION ) {
				totalEnabledTransitions += metric.getValue();
				enabledTransitions += "(" + metricKey.getActor() + " " + metricKey.getState() + " " + metric.getData() + ")";
			} else if ( metricKey.getType() == MetricKey.Type.ACTIVE_OUTPUT ) {
				if ( metric.getData().toString().contains("_START_") ) {
					taskStarts += "(" + metric.getData() + ")";
				}else if ( metric.getData().toString().contains("_STOP_") ) {
					taskStops += "(" + metric.getData() + ")";
				}
				else
					activeOutput+= "(" + metric.getData() + ")";
			} 

			lastTime = currentTime;
		}
		double opTempo = firedTransitions;// / window;
		result += "\n" + currentTime + "," + activeInputs + "," + totalActiveInputs + "," + enabledTransitions 
				+ "," + totalEnabledTransitions + "," + transitionDurations + "," + totalTransitionDurations + "," 
				+ taskStarts + "," + taskStops + "," + totalOpTempo+","+activeOutput;
		return result;
	}
}