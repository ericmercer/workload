package simulator.copy;

import java.util.ArrayList;
import java.util.List;

public interface IState {

	/**
	 * Returns a list of enabled transitions.
	 * @return
	 */
	ArrayList<ITransition> getEnabledTransitions();
	
	String getName();

	List<ITransition> getTransitions();

	List<ComChannel<?>> getActiveInputs();

	List<ComChannel<?>> getActiveOutputs();
}
