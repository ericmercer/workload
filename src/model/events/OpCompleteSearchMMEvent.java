package model.events;

import model.actors.*;
import model.team.*;
import simulator.*;

public class OpCompleteSearchMMEvent extends simulator.Event {
	public OpCompleteSearchMMEvent(final ComChannelList inputs, final ComChannelList outputs)
	{
		//Define internal variables
		_name = "OpCompleteSearchEvent";
		
		//Define the event states
		State state = this.getState();
		_outputs = new ComChannelList();
		_outputs.putAll(outputs);
		//Define the Event transitions
		_transition = new Transition(getInternalVars(), inputs, outputs, state, Duration.OP_TX_MM.getRange()) {
			@Override 
			public boolean isEnabled() {
				if(MissionManager.AUDIO_MM_OP_COMM.MM_ACK_OP.equals(_inputs.get(Channels.AUDIO_MM_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_SEARCH_COMPLETE_MM);
					return true;
				}
				return false;
			};
		};
		state.add(_transition);
		
	}

	@Override
	public ITransition getEnabledTransition() {
		if(_transition.isEnabled())
			return _transition;
		return null;
	}

	@Override
	public IState getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTransitions() {
		// TODO Auto-generated method stub
		
	}
}
