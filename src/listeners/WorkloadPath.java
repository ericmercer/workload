package listeners;

import java.util.*;
import java.util.Map.Entry;

import simulator.Metric;
import simulator.MetricKey;

/**
 * A path stores non-deterministic workload metrics.
 */
public class WorkloadPath {

	private WorkloadPath _parent;
	private TreeMap<MetricKey, Metric> _values;
	private ArrayList<WorkloadPath> _children;
	private int CumulativeDecisionWorkload;
	private int CumulativeTemporalWorkload;
	private int CumulativeResourceWorkload;
	
	public WorkloadPath( WorkloadPath parent, TreeMap<MetricKey, Metric> values ) {
		_parent = parent;
		_values = values;
		_children = new ArrayList<WorkloadPath>( );
		CumulativeDecisionWorkload = 0;
		CumulativeTemporalWorkload = 0;
		CumulativeResourceWorkload = 0;
	}
	
	public WorkloadPath getParent( ) {
		return _parent;
	}
	
	public TreeMap<MetricKey, Metric> getValues( ) {
		return _values;
	}
	
	public ArrayList<WorkloadPath> getChildren( ) {
		return _children;
	}
	
	public int getCumulativeDecisionWorkload( ) {
		return CumulativeDecisionWorkload;
	}
	
	public int getCumulativeTemporalWorkload( ) {
		return CumulativeTemporalWorkload;
	}
	
	public int getCumulativeResourceWorkload( ) {
		return CumulativeResourceWorkload;
	}
	
	public void setParent( WorkloadPath parent ) {
		_parent = parent;
	}
	
	public void addChild( WorkloadPath child ) {
		_children.add( child );
	}
	
	public Metric get( MetricKey metricKey ) {
		return _values.get(metricKey);
	}
	
	public void put( MetricKey metricKey, Metric metric ) {
		_values.put(metricKey, metric);
		if( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION )
			CumulativeDecisionWorkload += metric.getValue();
		else if( metricKey.getType() == MetricKey.Type.TRANSITION_DURATION )
			CumulativeTemporalWorkload += metric.getValue();
		else if( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT )
			CumulativeResourceWorkload += metric.getValue();
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