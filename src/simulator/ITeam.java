package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ITeam {
	
	/**
	 * Return a list of current actor transitions
	 * @return
	 */
	HashMap<IActor, ITransition> getEnabledTransitions();
	
	void updateTransitions();
	
	/**
	 * Return a list of current event transitions
	 * @return
	 */
	ArrayList<IEvent> getEvents();


//	HashMap<Actor, Integer> getWorkload();
	
	ComChannelList getAllChannels();


	List<IActor> getActors();

}
