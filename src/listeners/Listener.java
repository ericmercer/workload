package listeners;

import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.DynamicElementInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import simulator.Metric;
import simulator.MetricKey;

public class Listener extends ListenerAdapter {

	/**
	 * stores the metrics
	 */
	Path _rootPath = new Path( null, 0, 0, 0, new TreeMap<MetricKey, Metric>(), new TreeMap<MetricKey, Metric>(), new TreeMap<MetricKey, Metric>() );
	Path _currentPath = _rootPath;
	
	/**
	 * acts whenever a choice generator is set (at the first point of non-determinism).
	 * at this point we start a new child path.
	 */
	@Override
	public void choiceGeneratorSet ( VM vm, ChoiceGenerator<?> newCG ) {
		makeChoice( newCG.getInsn( ).getMethodInfo( ) );
	}

	/**
	 * acts whenever a choice generator is advanced (at the following points of non-determinism).
	 * at this point we return to the parent path and start a new path.
	 */
	@Override
	public void choiceGeneratorAdvanced ( VM vm, ChoiceGenerator<?> currentCG ) {
		makeChoice( currentCG.getInsn( ).getMethodInfo( ) );
	}
	
	private void makeChoice( MethodInfo mi ) {
		String methodName = mi.getName( );
		if( methodName.equals( "duration" ) )
			advancePath( );
	}
	
	private void advancePath( ) {
		System.out.println( "point of non-determinism" );
		Path newPath = new Path( _currentPath,
				_currentPath._cumulativeDecisionWorkload,
				_currentPath._cumulativeResourceWorkload,
				_currentPath._cumulativeTemporalWorkload,
				_currentPath._cumulativeDecisionMetrics,
				_currentPath._cumulativeResourceMetrics,
				_currentPath._cumulativeTemporalMetrics );
		newPath._parentPath = _currentPath;
		_currentPath._childPaths.add( newPath );
		_currentPath = newPath;
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
		if ( fullMethodName.contains( "setEnabledTransitions" ) )
			storeEnabledTransitions( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "setActiveInputs" ) )
			storeActiveInputs( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "setActiveOutputs" ) )
			storeActiveOutputs( ti, insnToExecute, mi );
		else if ( fullMethodName.contains( "endSimulation" ) )
			Printer.getInstance();//.compareAndPrintCumlativeWorkload( _currentPath );
	}

	private void storeEnabledTransitions(ThreadInfo ti,
			Instruction insnToExecute, MethodInfo mi) {
		// TODO Auto-generated method stub
		
	}

	private void storeTransitionDuration(ThreadInfo ti,
			Instruction insnToExecute, MethodInfo mi) {
		ArrayList<Object> parameters = getParameters(ti, insnToExecute, mi);
//		int time = getIntParameter( 1, ti, insnToExecute, mi );
//		String actorName = getStringParameter( 2, ti, insnToExecute, mi );
//		String stateName = getStringParameter( 3, ti, insnToExecute, mi );
//		int availableTransitions = getIntParameter( 4, ti, insnToExecute, mi );
//		
//		//don't measure mock (watered down) model objects
//		if( isMock( actorName ) )
//			return;
//		
//		//form metrics and keys
//		MetricKey currentKey = new MetricKey( time, actorName, stateName );
//		Metric currentMetric = new Metric( Metric.TypeEnum.setDecisionWorkload, availableTransitions );
//		
//		//store metric
//		Metric metric = _currentPath._cumulativeDecisionMetrics.get( currentKey );
//		if ( metric == null )
//			_currentPath._cumulativeDecisionMetrics.put( currentKey, currentMetric );
//		else
//			 metric.add( availableTransitions );
//		_currentPath._cumulativeDecisionWorkload += availableTransitions;
	}

	private void storeActiveInputs(ThreadInfo ti, Instruction insnToExecute,
			MethodInfo mi) {
		// TODO Auto-generated method stub
		
	}

	private void storeActiveOutputs(ThreadInfo ti, Instruction insnToExecute,
			MethodInfo mi) {
		// TODO Auto-generated method stub
		
	}
	
	private ArrayList<Object> getParameters( ThreadInfo ti, Instruction insnToExecute, MethodInfo mi ) {
		ArrayList<Object> parameters = new ArrayList<Object>();
		
		int parameterSize = ti.getStackFrameExecuting( insnToExecute, 0 ).getLocalVariableCount();
		for ( int i = 0; i < parameterSize; i++ ) {
			parameters.add( ti.getStackFrameExecuting( insnToExecute, 0 ).getLocalOrFieldValue( mi.getLocalVar( i, ti.getPC( ).getPosition( ) ).getName() ) );
		}
		
		return parameters;
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
