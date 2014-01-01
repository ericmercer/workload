package simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents a the state of an actor (state machine)
 * @author tjr team
 * 
 */
public class State implements IState {
	
	/**
	 * this is the name of the state
	 */
	private String _name;
	private ArrayList<ITransition> _transitions;
	/**
	 * this constructor is used for creating new states
	 * @param name
	 */
	public State(String name) {
		_name = name;
		_transitions = new ArrayList<ITransition>();
	}
	
	public State add(ITransition new_transition)
	{
		if(_transitions.contains(new_transition)){
			return this;
		}
		
		//The Actor will decide how to handle the transition and how to sort them.
		_transitions.add(new_transition);
		new_transition.setIndex(_transitions.indexOf(new_transition));
		
		return this;
	}
	
	@Override
	public ArrayList<ITransition> getEnabledTransitions() {
		ArrayList<ITransition> enabledTransitions = new ArrayList<ITransition>();
		for (ITransition transition : _transitions) {
			if ( transition.isEnabled() ) {
				enabledTransitions.add((ITransition) new Transition((Transition)transition));
			}
		}
		return enabledTransitions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof String && _name.equals(obj))
			return true;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}
	
	@Override
	public String getName()
	{
		return _name;
	}

	/**
	 * this method works like a normal toString method
	 * @return return the string representation of this state
	 */
	public String toString() {
		return _name;
	}

	@Override
	public List<ITransition> getTransitions() {
		return _transitions;
	}

	@Override
	public List<ComChannel<?>> getActiveInputs() {
		List<ComChannel<?>> activeInputs = new ArrayList<ComChannel<?>>();
		for(ITransition transition : _transitions) {
			transition.isEnabled();
			activeInputs.addAll(transition.getActiveInputs());
		}
		return activeInputs;
	}

	public void updateTransitions() {
		for (ITransition transition : _transitions) {
			transition.updateTransition();
		}
	}

}
