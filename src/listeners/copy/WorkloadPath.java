package listeners.copy;

import java.util.*;
import java.util.Map.Entry;

import simulator.Metric;
import simulator.MetricKey;

/**
 * A path stores non-deterministic workload metrics.
 */
public class WorkloadPath {

	private TreeMap<MetricKey, Metric> _values;
	private int CumulativeDecisionWorkload;
	private int CumulativeTemporalWorkload;
	private int CumulativeResourceWorkload;

	public WorkloadPath( WorkloadPath old ) {
		if(old == null) {
			_values = new TreeMap<MetricKey, Metric>();
			CumulativeDecisionWorkload = 0;
			CumulativeTemporalWorkload = 0;
			CumulativeResourceWorkload = 0;
		} else {
			TreeMap<MetricKey, Metric> oldValues = new TreeMap<MetricKey, Metric>();
			oldValues.putAll(old.getValues());
			_values = oldValues;
			CumulativeDecisionWorkload = old.getCumulativeDecisionWorkload();
			CumulativeTemporalWorkload = old.getCumulativeTemporalWorkload();
			CumulativeResourceWorkload = old.getCumulativeResourceWorkload();
		}
	}

	public TreeMap<MetricKey, Metric> getValues( ) {
		return _values;
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

	public Metric get( MetricKey metricKey ) {
		return _values.get(metricKey);
	}
	private int ld = 0;
	private int lt = 0;
	private int lr = 0;
	public void put( MetricKey metricKey, Metric metric ) {
		_values.put(metricKey, metric);
		int delta_time;
		if( metricKey.getType() == MetricKey.Type.ENABLED_TRANSITION || metricKey.getType() == MetricKey.Type.TRANSITION_DURATION){
			delta_time = 0;
			if(ld != metricKey.getTime()){
				delta_time = metricKey.getTime()-ld;
				ld = metricKey.getTime();
			}
			CumulativeDecisionWorkload += metric.getValue()*delta_time;
		}else if( metricKey.getType() == MetricKey.Type.OP_TEMPO ){
			delta_time = 0;
			if(lt != metricKey.getTime()){
				delta_time = metricKey.getTime()-lt;
				lt = metricKey.getTime();
			}
			CumulativeTemporalWorkload += metric.getValue()*delta_time;
		}else if( metricKey.getType() == MetricKey.Type.ACTIVE_INPUT ){
			delta_time = 0;
			if(lr != metricKey.getTime()){
				delta_time = metricKey.getTime()-lr;
				lr = metricKey.getTime();
			}
			CumulativeResourceWorkload += metric.getValue()*delta_time;
		}
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