package listeners;

import java.util.*;
import java.util.Map.Entry;

import simulator.Metric;
import simulator.MetricKey;

/**
 * A path stores non-deterministic workload metrics.
 */
public class Path {

	private Path _parent;
	private TreeMap<MetricKey, Metric> _values;
	private ArrayList<Path> _children;
	
	public Path( Path parent, TreeMap<MetricKey, Metric> values ) {
		_parent = parent;
		_values = values;
		_children = new ArrayList<Path>( );
	}
	
	public Path getParent( ) {
		return _parent;
	}
	
	public TreeMap<MetricKey, Metric> getValues() {
		return _values;
	}
	
	public ArrayList<Path> getChildren( ) {
		return _children;
	}
	
	public void setParent( Path parent ) {
		_parent = parent;
	}
	
	public void addChild( Path child ) {
		_children.add( child );
	}
	
	public Metric get( MetricKey metricKey ) {
		return _values.get(metricKey);
	}
	
	public void put( MetricKey metricKey, Metric metric ) {
		_values.put(metricKey, metric);
	}
	
	public String toString( ) {
		String result = "";
		
		for( Entry<MetricKey, Metric> value : _values.entrySet() ) {
			MetricKey metricKey = value.getKey();
			Metric metric = value.getValue();
			result += "\n" + metricKey.toString() + "\n\t" + metric.toString();
		}
		
		return result;
	}
}