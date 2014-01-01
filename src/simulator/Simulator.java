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
	private DebugMode _mode;
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
		_mode = mode;
		_duration = duration;
		
		initializeRandom();
		_setup = true;
	}
	
	/**
	 * Main Simulation method.
	 */
	public void run()
	{
		assert _setup : "Simulator not setup correctly";
	
		do {
			updateTransitions();
			
			getEnabledTransitions();
			
			_clock.advanceTime();
			
			storeMetrics();
			
			//Process Ready Transitions
			_ready_transitions.clear();
			_ready_transitions.putAll(_clock.getReadyTransitions());
			for(Entry<IActor, ITransition> readyTransition : _ready_transitions.entrySet()){
				//System.out.println(_clock.getElapsedTime() + "\n\t" + readyTransition.toString());
				//Fire Transition
				ITransition transition = (ITransition) readyTransition.getValue();
				transition.fire();
			}
		} while (!_ready_transitions.isEmpty());
		
		MetricManager.getInstance().endSimulation();
	}

	/**
	 * HELPER METHODS
	 */
	
	private void updateTransitions() {
		_team.updateTransitions();
	}
	
	private void getEnabledTransitions()
	{
		//Get Transitions from the Events
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
		
		//Get Transitions from the Actor
		HashMap<IActor, ITransition> transitions = _team.getEnabledTransitions();
		for(Map.Entry<IActor, ITransition> entry : transitions.entrySet() ) {
			ITransition t = entry.getValue();
			_clock.addTransition(entry.getKey(), t, duration(t.getDurationRange()));
		}
		
		//deactivate outputs from events after one cycle
		for(IEvent e : _team.getEvents() ) {
			ITransition t = e.getEnabledTransition();
			if(t == null){
				e.deactivate();
			}
		}
	}

	private void storeMetrics() {
		int time = _clock.getElapsedTime();
		List<IActor> actors = _team.getActors();
		for(IActor actor : actors) {
			String actorName = actor.getName();
			IState state = actor.getCurrentState();
			String stateName = state.getName();
			state.getEnabledTransitions();
			List<ComChannel<?>> activeInputs = state.getActiveInputs();
			MetricManager.getInstance().setActiveInputs(time, actorName, stateName, activeInputs.size());
		}
	}
	
	private void initializeRandom() {
		_random = new Random();
		_random.setSeed(0); //Always use the same seed
	}
	
	public int duration(Range range)
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
