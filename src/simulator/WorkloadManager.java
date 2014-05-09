///**
// * Michael Sharp
// * 
// * This is the workload manager to run the simulator stand alone from JPF.  It uses the metrics that JPF also uses.
// */
//package simulator;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Stack;
//
//import simulator.Metric;
//import simulator.MetricKey;
//import listeners.*;
//
//public class WorkloadManager{
//	
//	private static WorkloadManager _instance;
//	
//	public WorkloadManager()
//	{
//	}
//	
//	private List<Integer> test = new ArrayList<Integer>();
//	/**
//	 * stores the metrics
//	 */
//	Stack<WorkloadPath> branchStack = new Stack<WorkloadPath>();
//	WorkloadPath currentPath = new WorkloadPath( null );
//	WorkloadPath HighestCumulativeDescisionWorkloadPath;
//	WorkloadPath HighestCumulativeTemporalWorkloadPath;
//	WorkloadPath HighestCumulativeResourceWorkloadPath;
//	WorkloadPath LowestCumulativeDescisionWorkloadPath;
//	WorkloadPath LowestCumulativeTemporalWorkloadPath;
//	WorkloadPath LowestCumulativeResourceWorkloadPath;
//	static int HCDW=0;
//	static int HCTW=0;
//	static int HCRW=0;
//	static int LCDW=2147483647;
//	static int LCTW=2147483647;
//	static int LCRW=2147483647;
//	
//	
//	//static int tesst = 0;
//	/**
//	 * acts whenever certain methods execute
//	 */
//
//	public void executeInstruction ( MetricKey metrickey,Metric metric ) {
//		//get the methods information
//		
//		String fullMethodName = metrickey.getType().toString();
//		//System.out.println(fullMethodName);
//		//only act on these methods
//		if ( fullMethodName.contains( "TRANSITION_DURATION" ) )
//			storeTransitionDuration( metrickey,metric );
//		else if ( fullMethodName.contains( "ENABLED_TRANSITION" ) )
//			storeEnabledTransitions( metrickey,metric);
//		else if ( fullMethodName.contains( "ACTIVE_INPUT" ) )
//			storeActiveInputs( metrickey,metric );
//		else if ( fullMethodName.contains( "ACTIVE_OUTPUT" ) )
//			storeActiveOutputs( metrickey,metric );
//	}
//
//	private void printCurrentPath(String pathData) {
//		//System.out.println(++tesst);
//		
//		if( currentPath.getCumulativeDecisionWorkload( ) > HCDW) {
//			HCDW = currentPath.getCumulativeDecisionWorkload( );
//			Printer.getInstance( ).print( "HCDW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeDecisionWorkload() );
//			Printer.getInstance( ).print( "HCDW.txt", pathData);
//			HighestCumulativeDescisionWorkloadPath = currentPath;
//		}
//		if( currentPath.getCumulativeResourceWorkload( ) > HCRW ) {
//			HCRW = currentPath.getCumulativeResourceWorkload( );
//			Printer.getInstance( ).print( "HCRW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeResourceWorkload() );
//			Printer.getInstance( ).print( "HCRW.txt", pathData);
//			HighestCumulativeResourceWorkloadPath = currentPath;
//		}
//		if( currentPath.getCumulativeTemporalWorkload( ) > HCTW) {
//			HCTW = currentPath.getCumulativeTemporalWorkload( );
//			Printer.getInstance( ).print( "HCTW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeTemporalWorkload() );
//			Printer.getInstance( ).print( "HCTW.txt", pathData);
//			HighestCumulativeTemporalWorkloadPath = currentPath;
//		}
//		if( currentPath.getCumulativeDecisionWorkload( ) < LCDW ) {
//			LCDW = currentPath.getCumulativeDecisionWorkload( );
//			Printer.getInstance( ).print( "LCDW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeDecisionWorkload() );
//			Printer.getInstance( ).print( "LCDW.txt", pathData);
//			LowestCumulativeDescisionWorkloadPath = currentPath;
//		}
//		if( currentPath.getCumulativeResourceWorkload( ) < LCRW) {
//			LCRW = currentPath.getCumulativeResourceWorkload( );
//			Printer.getInstance( ).print( "LCRW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeResourceWorkload() );
//			Printer.getInstance( ).print( "LCRW.txt", pathData);
//			LowestCumulativeResourceWorkloadPath = currentPath;
//		}
//		if(currentPath.getCumulativeTemporalWorkload( ) < LCTW) {
//			LCTW = currentPath.getCumulativeTemporalWorkload( );
//			Printer.getInstance( ).print( "LCTW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeTemporalWorkload() );
//			Printer.getInstance( ).print( "LCTW.txt", pathData);
//			LowestCumulativeTemporalWorkloadPath = currentPath;
//		}
//	}
//
//	private void storeTransitionDuration(MetricKey metrickey,Metric metric) {
//		//get parameters
//		int time = metrickey.getTime();
//		String actorName = metrickey.getActor();
//		String stateName = metrickey.getState();
//		int duration = metric.getValue();
//
//		//don't measure mock (watered down) model objects
//		if( notRecorded( actorName ) )
//			return;
//		
//		//form metrics and keys
//		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.TRANSITION_DURATION );
//		Metric currentMetric = new Metric( duration, duration );
//		storeMetric(currentKey, currentMetric);
//
//		//do funky optempo stuff
//		test.add(time-duration);
//		for(int i = 0; i < test.size(); i++)
//		{
//			if((time-duration) - test.get(i) > 9)
//			{
//				test.remove(i);
//				i--;
//			}
//		}	
//		//System.out.println("\nt - d = "+(time - duration));
//		//System.out.println("size= "+test.size());
//		currentKey = new MetricKey(time-duration,actorName,stateName,MetricKey.Type.OP_TEMPO);
//		currentMetric = new Metric(1, 0);
//		storeMetric(currentKey,currentMetric);
//	}
//
//	private void storeEnabledTransitions(MetricKey metrickey,Metric metric) {
//
//		//get parameters
//		int time = metrickey.getTime();
//		String actorName = metrickey.getActor();
//		String stateName = metrickey.getState();
//		int transitions = metric.getValue();
//
//		//don't measure mock (watered down) model objects
//		if( notRecorded( actorName ) )
//			return;
//
//		//form metrics and keys
//		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ENABLED_TRANSITION );
//		Metric currentMetric = new Metric( transitions, transitions );
//		storeMetric(currentKey, currentMetric);
//
//	}
//
//	private void storeActiveInputs(MetricKey metrickey,Metric metric) {
//
//		//get parameters
//		int time = metrickey.getTime();
//		String actorName = metrickey.getActor();
//		String stateName = metrickey.getState();
//		String input = metric.getData().toString().substring(1, metric.getData().toString().length()-1);
//
//		//don't measure mock (watered down) model objects
//		if( notRecorded( actorName ) )
//			return;
//
//		//form metrics and keys
//		if(!input.contains("_START_") && !input.contains("_STOP_")){
//			MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ACTIVE_INPUT );
//			Metric currentMetric = new Metric( 1, input );
//			storeMetric(currentKey, currentMetric);
//		}
//
//	}
//
//	private void storeActiveOutputs(MetricKey metrickey,Metric metric) {
//
//		//get parameters
//		int time = metrickey.getTime();
//		String actorName = metrickey.getActor();
//		String stateName = metrickey.getState();
//		String output = metric.getData().toString().substring(1, metric.getData().toString().length()-1);
//
//		//don't measure mock (watered down) model objects
//		if( notRecorded( actorName ) )
//			return;
//
//		//form metrics and keys
//		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ACTIVE_OUTPUT );
//		Metric currentMetric = new Metric( 1, output );
//		storeMetric(currentKey, currentMetric);
//
//	}
//
//	void storePath(String pathData) {
//
//		//get parameters
//		printCurrentPath(pathData);
//	}
//
//
//	private boolean notRecorded( String actorName ) {
//		if (actorName.contains("UAV"))
//			return true;
//		if (actorName.contains("ater"))
//			return true;
//		return false;
//	}
//
//	private void storeMetric( MetricKey currentKey, Metric currentMetric) {
//		Metric metric = currentPath.get( currentKey );
//		if ( metric == null )
//			currentPath.put( currentKey, currentMetric );
//		else
//			 metric.add( metric );
//	}
//
//	/**
//	 * must always return a !null string
//	 * @param object a generic object returned by getLocalOrFieldValue
//	 * @return a string representation of the object
//	 */
//}

/**
 * Michael Sharp
 * 
 * This is the workload manager to run the simulator stand alone from JPF.  It uses the metrics that JPF also uses.
 */
package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import simulator.Metric;
import simulator.MetricKey;

public class WorkloadManager{
	
	private static WorkloadManager _instance;
	
	public static WorkloadManager getWork() 
	{
       if (_instance == null) {
            _instance = new WorkloadManager();
       }
        return _instance;
	}
	
	public void clean()
	{
		_instance = null;
	}
	public WorkloadManager()
	{
	}
	
	private List<Integer> test = new ArrayList<Integer>();
	/**
	 * stores the metrics
	 */
	Stack<WorkloadPath> branchStack = new Stack<WorkloadPath>();
	WorkloadPath currentPath = new WorkloadPath( null );
	WorkloadPath HighestCumulativeDescisionWorkloadPath;
	WorkloadPath HighestCumulativeTemporalWorkloadPath;
	WorkloadPath HighestCumulativeResourceWorkloadPath;
	WorkloadPath LowestCumulativeDescisionWorkloadPath;
	WorkloadPath LowestCumulativeTemporalWorkloadPath;
	WorkloadPath LowestCumulativeResourceWorkloadPath;
	static int HCDW=0;
	static int HCTW=0;
	static int HCRW=0;
	static int LCDW=Integer.MAX_VALUE;
	static int LCTW=Integer.MAX_VALUE;
	static int LCRW=Integer.MAX_VALUE;
	
	
	/**
	 * acts whenever certain methods execute
	 */

	public void executeInstruction ( MetricKey metrickey,Metric metric ) {
		//get the methods information
		
		String fullMethodName = metrickey.getType().toString();
		//System.out.println(fullMethodName);
		//only act on these methods
		if ( fullMethodName.contains( "TRANSITION_DURATION" ) )
			storeTransitionDuration( metrickey,metric );
		if ( fullMethodName.contains( "ENABLED_TRANSITION" ) )
			storeEnabledTransitions( metrickey,metric);
		else if ( fullMethodName.contains( "ACTIVE_INPUT" ) )
			storeActiveInputs( metrickey,metric );
		else if ( fullMethodName.contains( "ACTIVE_OUTPUT" ) )
			storeActiveOutputs( metrickey,metric );
	}

	private void printCurrentPath(String pathData) {
		//gets actors to print out the stats for the individual ators
		HashMap<String, TreeMap<MetricKey, Metric>> actors = currentPath.getActors();
		
		if( currentPath.getCumulativeDecisionWorkload( ) > HCDW) {
			HCDW = currentPath.getCumulativeDecisionWorkload();
			Printer.getInstance( ).print( "HCDW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeDecisionWorkload() );
			for (String actor : actors.keySet())
				{
					Printer.getInstance().printNoOutput("HCDW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
				}
			Printer.getInstance( ).print( "HCDW.txt", pathData);
			HighestCumulativeDescisionWorkloadPath = currentPath;
		}
		if( currentPath.getCumulativeResourceWorkload( ) > HCRW ) {
			HCRW = currentPath.getCumulativeResourceWorkload( );
			Printer.getInstance( ).print( "HCRW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeResourceWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("HCRW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "HCRW.txt", pathData);
			HighestCumulativeResourceWorkloadPath = currentPath;
		}
		if( currentPath.getCumulativeTemporalWorkload( ) > HCTW) {
			HCTW = currentPath.getCumulativeTemporalWorkload( );
			Printer.getInstance( ).print( "HCTW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeTemporalWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("HCTW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "HCTW.txt", pathData);
			HighestCumulativeTemporalWorkloadPath = currentPath;
		}
		if( currentPath.getCumulativeDecisionWorkload( ) < LCDW ) {
			LCDW = currentPath.getCumulativeDecisionWorkload( );
			Printer.getInstance( ).print( "LCDW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeDecisionWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCDW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCDW.txt", pathData);
			LowestCumulativeDescisionWorkloadPath = currentPath;
		}
		if( currentPath.getCumulativeResourceWorkload( ) < LCRW) {
			LCRW = currentPath.getCumulativeResourceWorkload( );
			Printer.getInstance( ).print( "LCRW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeResourceWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCRW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCRW.txt", pathData);
			LowestCumulativeResourceWorkloadPath = currentPath;
		}
		if(currentPath.getCumulativeTemporalWorkload( ) < LCTW) {
			LCTW = currentPath.getCumulativeTemporalWorkload( );
			Printer.getInstance( ).print( "LCTW/TOTAL.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeTemporalWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCTW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCTW.txt", pathData);
			LowestCumulativeTemporalWorkloadPath = currentPath;
		}
	}

	private void storeTransitionDuration(MetricKey metrickey,Metric metric) {
		//get parameters
		int time = metrickey.getTime();
		String actorName = metrickey.getActor();
		String stateName = metrickey.getState();
		int duration = metric.getValue();

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;
		
		//form metrics and keys
		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.TRANSITION_DURATION );
		Metric currentMetric = new Metric( duration, duration );
		storeMetric(currentKey, currentMetric);

		//do funky optempo stuff
		test.add(time-duration);
		for(int i = 0; i < test.size(); i++)
		{
			if((time-duration) - test.get(i) > 9)
			{
				test.remove(i);
				i--;
			}
		}	
		//System.out.println("\nt - d = "+(time - duration));
		//System.out.println("size= "+test.size());
		currentKey = new MetricKey(time-duration,actorName,stateName,MetricKey.Type.OP_TEMPO);
		currentMetric = new Metric(1, 0);
		storeMetric(currentKey,currentMetric);
	}

	private void storeEnabledTransitions(MetricKey metrickey,Metric metric) {

		//get parameters
		int time = metrickey.getTime();
		String actorName = metrickey.getActor();
		String stateName = metrickey.getState();
		int transitions = metric.getValue();

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;

		//form metrics and keys
		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ENABLED_TRANSITION );
		Metric currentMetric = new Metric( transitions, transitions );
		storeMetric(currentKey, currentMetric);

	}

	private void storeActiveInputs(MetricKey metrickey,Metric metric) {

		//get parameters
		int time = metrickey.getTime();
		String actorName = metrickey.getActor();
		String stateName = metrickey.getState();
		String input = metric.getData().toString().substring(1, metric.getData().toString().length()-1);

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;

		//form metrics and keys
		if(!input.contains("_START_") && !input.contains("_STOP_")){
			MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ACTIVE_INPUT );
			Metric currentMetric = new Metric( 1, input );
			storeMetric(currentKey, currentMetric);
		}

	}

	private void storeActiveOutputs(MetricKey metrickey,Metric metric) {

		//get parameters
		int time = metrickey.getTime();
		String actorName = metrickey.getActor();
		String stateName = metrickey.getState();
		String output = metric.getData().toString().substring(1, metric.getData().toString().length()-1);

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;

		//form metrics and keys
		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ACTIVE_OUTPUT );
		Metric currentMetric = new Metric( 1, output );
		storeMetric(currentKey, currentMetric);

	}

	void storePath(String pathData) {

		//get parameters
		printCurrentPath(pathData);
	}


	private boolean notRecorded( String actorName ) {
		if (actorName.contains("UAV"))
			return true;
		if (actorName.contains("ater"))
			return true;
		return false;
	}

	private void storeMetric( MetricKey currentKey, Metric currentMetric) {
		Metric metric = currentPath.get( currentKey );
		if ( metric == null )
			currentPath.put( currentKey, currentMetric );
		else
		{
			 metric.add( metric );
			 currentPath.addActor(currentKey, currentMetric);
		}
	}

	/**
	 * must always return a !null string
	 * @param object a generic object returned by getLocalOrFieldValue
	 * @return a string representation of the object
	 */
}