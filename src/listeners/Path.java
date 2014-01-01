package listeners;

import java.util.*;
import java.util.Map.Entry;

import simulator.Metric;
import simulator.MetricKey;



	/**
 * A path stores non-deterministic workload metrics.
 */
public class Path {

	public Path _parentPath;
	public TreeMap<MetricKey, Metric> _values;
	public ArrayList<Path> _childPaths;
	
	public Path(Path parent, TreeMap<MetricKey, Metric> values) {
		_parentPath = parent;
		_values = values;
		_childPaths = new ArrayList<Path>( );
	}

	public String toString( ) {
		String result = "time, resource data ((Actor State Value)*), resource workload, temporal data ((Actor State Value)*), temporal workload, decision data ((Actor State Value)*), decision workload, task data, fired outputs\n";
		for ( int time = 0; time < 200; time++ )
			result += getMetricsByTime( time );
		
		return result;
	}
	
	private String getMetricsByTime( int time ) {
		String result = time + ", ";

		int cumulativeResourceValue = 0;
		boolean found = false;
		for ( Entry<MetricKey, Metric> metric : _values.entrySet( ) )
			if ( found && ( metric.getKey().getTime() != time ) )
				break;
			else if ( found = ( metric.getKey().getTime() == time ) )
				result += "( " + metric.getKey().getActor() + " " + metric.getKey().getState() + " " + (cumulativeResourceValue += metric.getValue()._value) + " ) ";
		result += ", " + cumulativeResourceValue;
		
		return result + "\n";
	}
}