package simulator;

import gov.nasa.jpf.vm.Verify;

import java.util.*;
import java.util.Map.Entry;

public class Simulator {
	
	public enum DebugMode {
		DEBUG,
		PROD
	}
	
	public enum DurationMode {
		MIN,
		MAX,
		MEAN,
		MIN_MAX,
		MIN_MAX_MEAN
	}
	
	private ITeam _team;
	private IDeltaClock _clock;
	private HashMap<IActor, ITransition> _ready_transitions = new HashMap<IActor, ITransition>();
	private ArrayList<IActor> _active_events = new ArrayList<IActor>();
	private DebugMode _debugMode;
	private DurationMode _duration;
	private Random _random;
	
	//Singleton variables
	private boolean _setup = false;
	private static Simulator _instance = null;
	private Date _date = null;
	
	/**
	 * Get simulator singleton
	 * @return
	 */
	public static synchronized Simulator getSim() {
        if (_instance == null) {
            _instance = new Simulator();
        }
        return _instance;
	}
	
	private Simulator() {
		_clock = new DeltaClock();
		_date = new Date();
	}
	
	public void setup(ITeam team, DebugMode mode, DurationMode duration)
	{
		_setup = false;
		_clock = new DeltaClock();
		
		_team = team;
		_debugMode = mode;
		_duration = duration;
		
		_random = new Random();
		_random.setSeed(0);
		_setup = true;
	}

	/**
	 * Main Simulation method.
	 */
	public String run()
	{
		assert _setup : "Simulator not setup correctly";
		
		StringBuilder debugged_output = new StringBuilder();
		
		do {
			if(_clock.getElapsedTime() >= 205)
				System.out.print("");
			updateTransitions();
			
			getEnabledTransitions();
			
			_clock.advanceTime();
			
			processReadyTransitions(debugged_output);
		} while (!_ready_transitions.isEmpty());
		
		MetricManager.getInstance().endSimulation();
		if(_debugMode == DebugMode.DEBUG)
			return debugged_output.toString();
		else
			return null;
	}

	//safety check run
	public String runSafe(String checkSafe) {

		assert _setup : "Simulator not setup correctly";
		
		StringBuilder debugged_output = new StringBuilder();
		
		do {
			updateTransitions();
			
			getEnabledTransitions();
			
			_clock.advanceTime();
			
			checkSafe = processReadyTransitionsWithChecking(debugged_output, checkSafe);
		} while (!_ready_transitions.isEmpty() && checkSafe != null);
		if(checkSafe == null || checkSafe.length() > 0){
			int ending_index = 0;
			for(int i = 0; i < 5; i++){
				int temp = checkSafe.indexOf("\n\n", ending_index+1);
				if(temp > 0)
					ending_index = temp;
			}
			System.out.println("lost lines:\n" + checkSafe.substring(0, ending_index));
			System.out.println("failed");
		}
		MetricManager.getInstance().endSimulation();
		if(_debugMode == DebugMode.DEBUG)
			return debugged_output.toString();
		else
			return null;
	}
	//
	//	HELPER METHODS
	//
	
	private String processReadyTransitionsWithChecking(
			StringBuilder debugged_output, String checkSafe) {
		_ready_transitions.clear();
		_ready_transitions.putAll(_clock.getReadyTransitions());
		for(Entry<IActor, ITransition> readyTransition : _ready_transitions.entrySet()){
			String new_line = _clock.getElapsedTime() + "\t" + readyTransition.toString();
			String check = checkSafe.substring(0, checkSafe.indexOf("\n\n"));
			debugged_output.append(new_line + "\n\n");
			if(new_line.equals(check)){
				System.out.println(new_line);
				checkSafe = checkSafe.substring(checkSafe.indexOf("\n\n")+2);
			} else{
				int ending_index = 0;
				for(int i = 0; i < 5; i++){
					int temp = checkSafe.indexOf("\n\n", ending_index+1);
					if(temp > 0)
						ending_index = temp;
				}
				System.out.println("old lines:\n" + checkSafe.substring(0, ending_index));
				System.out.println("failed line: " + new_line);
				return null;
			}ITransition transition = (ITransition) readyTransition.getValue();
			transition.fire();
		}
		return checkSafe;
	}

	private void updateTransitions() {
		_team.updateTransitions();
	}
	
	private void getEnabledTransitions()
	{
		//Get transitions from the events
		for(IEvent e : _team.getEvents() ) {
			
			ITransition t = e.getEnabledTransition();
			if ( _clock.getActorTransition((IActor) e) == null ) {
				if ( t != null && !e.isFinished() ) {
					_clock.addTransition((IActor) e, t, random(t.getDurationRange().min(),t.getDurationRange().max()));
					e.decrementCount();
				}
			} else {
				if ( t == null ) {
					_clock.removeTransition((IActor) e);
				}
			}
		}
		
		//Get transitions from the actors
		HashMap<IActor, ITransition> transitions = _team.getEnabledTransitions();
		for(Map.Entry<IActor, ITransition> transitionEntry : transitions.entrySet() ) {
			IActor actor = transitionEntry.getKey();
			ITransition transition = transitionEntry.getValue();
			int duration = getDuration(transition.getDurationRange());
			_clock.addTransition(actor, transition, duration);
			
			//Store enabled transition data
			MetricManager.getInstance().setEnabledTransition(_clock.getElapsedTime(), actor.getName(), actor.getCurrentState().getName(), transition.getIndex());
			//Store transition duration data
			MetricManager.getInstance().setTransitionDuration(_clock.getElapsedTime(), actor.getName(), actor.getCurrentState().getName(), duration);
			//Store active input data
			for(ComChannel<?> input : actor.getCurrentState().getActiveInputs()) {
				MetricManager.getInstance().setActiveInput(_clock.getElapsedTime(), actor.getName(), actor.getCurrentState().getName(), input.getName());
			}
		}
		
		//Deactivate outputs from events after one cycle
		for(IEvent e : _team.getEvents() ) {
			ITransition t = e.getEnabledTransition();
			if(t == null){
				e.deactivate();
			}
		}
	}

	private void processReadyTransitions(StringBuilder debugged_output) {
		_ready_transitions.clear();
		_ready_transitions.putAll(_clock.getReadyTransitions());
		for(Entry<IActor, ITransition> readyTransition : _ready_transitions.entrySet()){
			if(_debugMode == DebugMode.DEBUG){
				System.out.println(_clock.getElapsedTime() + "\t" + readyTransition.toString());
				debugged_output.append(_clock.getElapsedTime() + "\t" + readyTransition.toString() + "\n\n");
			}
			ITransition transition = (ITransition) readyTransition.getValue();
			transition.fire();
		}
	}
	
	//
	//	public access
	//
	
	public int getDuration(Range range)
	{
		switch(_duration) {
			case MIN:
				return range.min();
			case MAX:
				return range.max();
			case MEAN:
				return range.mean();
			case MIN_MAX:
				if ( random(1) == 0 )
					return range.min();
				else
					return range.max();
			case MIN_MAX_MEAN:
				int val = random(2);
				if ( val == 0 )
					return range.min();
				else if (val == 2)
					return range.max();
				else 
					return range.mean();
			default:
				return 1;
		}
	}
	
	public int random(Range range)
	{
		return random(range.min(), range.max());
	}
	
	public int random(int val)
	{
		return random(0, val);
	}
	
	public int random(int min, int max)
	{
		return Verify.getInt(min, max);
	}
	
	public Integer getClockTime() {
		return _clock.getElapsedTime();
	}
}
