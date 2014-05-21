package listeners;

import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.DynamicElementInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import simulator.Metric;
import simulator.MetricKey;
import simulator.Printer;
import simulator.Simulator;
import simulator.WorkloadBuilder;
import simulator.WorkloadPath;

public class WorkloadListener extends ListenerAdapter {
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

	
	@Override
	public void choiceGeneratorSet ( VM vm, ChoiceGenerator<?> newCG ) {
		if( newCG.getInsn( ).getMethodInfo( ).getName( ).equals( "getIntFromList" ) ) {
			branchStack.push(new WorkloadPath(currentPath));
		}
	}
	@Override
	public void choiceGeneratorAdvanced ( VM vm, ChoiceGenerator<?> currentCG ) {
		if( currentCG.getInsn( ).getMethodInfo( ).getName( ).equals( "getIntFromList" ) ) {
			currentPath = new WorkloadPath(branchStack.peek());
		}
	}
	@Override
	public void choiceGeneratorProcessed ( VM vm, ChoiceGenerator<?> processedCG ) {
		if( processedCG.getInsn( ).getMethodInfo( ).getName( ).equals( "getIntFromList" ) ) {
			branchStack.pop();
		}
	}

	/**
	 * acts whenever certain methods execute
	 */
	@Override
	public void executeInstruction ( VM vm, ThreadInfo ti, Instruction insnToExecute ) {
		//get the methods information
		MethodInfo mi = insnToExecute.getMethodInfo( );
		String fullMethodName = mi.getFullName( );

		//only act on these methods
		if ( fullMethodName.contains( "setTransitionDuration" ) )
			storeTransitionDuration( ti, insnToExecute, mi );
		if ( fullMethodName.contains( "setEnabledTransition" ) )
			storeEnabledTransitions( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "setActiveInput" ) )
			storeActiveInputs( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "setActiveOutput" ) )
			storeActiveOutputs( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "endSimulation" ) )
			storePath( ti, insnToExecute, mi );
	}

	private void printCurrentPath(String pathData) {
		HashMap<String, TreeMap<MetricKey, Metric>> actors = currentPath.getActors();
		
		if( HighestCumulativeDescisionWorkloadPath == null || currentPath.getCumulativeDecisionWorkload( ) > HighestCumulativeDescisionWorkloadPath.getCumulativeDecisionWorkload( ) ) {
			//Printer.getInstance( ).print( "HCDW/TOTALHCDW.csv",WorkloadBuilder.build(currentPath),currentPath.getCumulativeDecisionWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("HCDW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "HCDW.txt", pathData);
			HighestCumulativeDescisionWorkloadPath = currentPath;
		}
		if( HighestCumulativeResourceWorkloadPath == null || currentPath.getCumulativeResourceWorkload( ) > HighestCumulativeResourceWorkloadPath.getCumulativeResourceWorkload( ) ) {
			//Printer.getInstance( ).print( "HCRW/TOTALHCRW.csv", WorkloadBuilder.build(currentPath),currentPath.getCumulativeResourceWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("HCRW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "HCRW.txt", pathData);
			HighestCumulativeResourceWorkloadPath = currentPath;
		}
		if( HighestCumulativeTemporalWorkloadPath == null || currentPath.getCumulativeTemporalWorkload( ) > HighestCumulativeTemporalWorkloadPath.getCumulativeTemporalWorkload( ) ) {
			//Printer.getInstance( ).print( "HCTW/TOTALHCTW.csv", WorkloadBuilder.build(currentPath),currentPath.getCumulativeTemporalWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("HCTW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "HCTW.txt", pathData);
			HighestCumulativeTemporalWorkloadPath = currentPath;
		}
		if( LowestCumulativeDescisionWorkloadPath == null || currentPath.getCumulativeDecisionWorkload( ) < LowestCumulativeDescisionWorkloadPath.getCumulativeDecisionWorkload( ) ) {
			//Printer.getInstance( ).print( "LCDW/TOTALLCDW.csv",WorkloadBuilder.build(currentPath),currentPath.getCumulativeDecisionWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCDW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCDW.txt", pathData);
			LowestCumulativeDescisionWorkloadPath = currentPath;
		}
		if( LowestCumulativeResourceWorkloadPath == null || currentPath.getCumulativeResourceWorkload( ) < LowestCumulativeResourceWorkloadPath.getCumulativeResourceWorkload( ) ) {
			//Printer.getInstance( ).print( "LCRW/TOTALLCRW.csv",WorkloadBuilder.build(currentPath), currentPath.getCumulativeResourceWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCRW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCRW.txt", pathData);
			LowestCumulativeResourceWorkloadPath = currentPath;
		}
		if( LowestCumulativeTemporalWorkloadPath == null || currentPath.getCumulativeTemporalWorkload( ) < LowestCumulativeTemporalWorkloadPath.getCumulativeTemporalWorkload( ) ) {
			//Printer.getInstance( ).print( "LCTW/TOTALLCTW.csv", WorkloadBuilder.build(currentPath), currentPath.getCumulativeTemporalWorkload() );
			for (String actor : actors.keySet())
			{
				Printer.getInstance().printNoOutput("LCRW/"+actor+".csv", WorkloadBuilder.buildActor(actors.get(actor)));
			}
			Printer.getInstance( ).print( "LCTW.txt", pathData);
			LowestCumulativeTemporalWorkloadPath = currentPath;
		}
	}

	private void storeTransitionDuration(ThreadInfo ti,
			Instruction insnToExecute, MethodInfo mi) {

		//get parameters
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
		int time = (int) parameters.get(1);
		String actorName = DEIToString( parameters.get(2) );
		String stateName = DEIToString( parameters.get(3) );
		int duration = (int) parameters.get(4);

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

	private void storeEnabledTransitions(ThreadInfo ti,
			Instruction insnToExecute, MethodInfo mi) {

		//get parameters
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
		int time = (int) parameters.get(1);
		String actorName = DEIToString( parameters.get(2) );
		String stateName = DEIToString( parameters.get(3) );
		int transitions = (int) parameters.get(4);

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;

		//form metrics and keys
		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ENABLED_TRANSITION );
		Metric currentMetric = new Metric( transitions, transitions );
		storeMetric(currentKey, currentMetric);

	}

	private void storeActiveInputs(ThreadInfo ti, Instruction insnToExecute,
			MethodInfo mi) {

		//get parameters
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
		int time = (int) parameters.get(1);
		String actorName = DEIToString( parameters.get(2) );
		String stateName = DEIToString( parameters.get(3) );
		String input = DEIToString( parameters.get(4) );

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

	private void storeActiveOutputs(ThreadInfo ti, Instruction insnToExecute,
			MethodInfo mi) {

		//get parameters
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
		int time = (int) parameters.get(1);
		String actorName = DEIToString( parameters.get(2) );
		String stateName = DEIToString( parameters.get(3) );
		String output = DEIToString( parameters.get(4) );

		//don't measure mock (watered down) model objects
		if( notRecorded( actorName ) )
			return;

		//form metrics and keys
		MetricKey currentKey = new MetricKey( time, actorName, stateName, MetricKey.Type.ACTIVE_OUTPUT );
		Metric currentMetric = new Metric( 1, output );
		storeMetric(currentKey, currentMetric);

	}

	private void storePath(ThreadInfo ti, Instruction insnToExecute, MethodInfo mi) {

		//get parameters
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
		String pathData = DEIToString( parameters.get(1) );

		printCurrentPath(pathData);
	}

	private ArrayList<Object> getParameters( ThreadInfo ti, Instruction insnToExecute, MethodInfo mi ) {
		ArrayList<Object> parameters = new ArrayList<Object>();

		int parameterSize = ti.getStackFrameExecuting( insnToExecute, 0 ).getLocalVariableCount();
		for ( int i = 0; i < parameterSize; i++ ) {
			Object parameter = ti.getStackFrameExecuting( insnToExecute, 0 ).getLocalOrFieldValue( mi.getLocalVar( i, ti.getPC( ).getPosition( ) ).getName() );
			parameters.add( parameter );
		}

		return parameters;
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
			 metric.add( metric );
	}

	/**
	 * must always return a !null string
	 * @param object a generic object returned by getLocalOrFieldValue
	 * @return a string representation of the object
	 */
	private String DEIToString( Object object ) {
		DynamicElementInfo DEI = ( DynamicElementInfo ) object;
		String result = "";

		char[] stringChars = DEI.getStringChars( );
		for( char nextChar : stringChars )
			result += nextChar;

		return result;
	}

}