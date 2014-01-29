package model.events;

//UNTESTED

import java.util.Map.Entry;

import model.actors.gen.UAV;
import model.team.Channels;
import simulator.ActorVariableWrapper;
import simulator.ComChannel;
import simulator.ComChannelList;
import simulator.Event;
import simulator.IState;
import simulator.ITransition;
import simulator.Range;
import simulator.State;
import simulator.Transition;

public class TrueTargetSightingEvent extends Event {
	
	public TrueTargetSightingEvent(final ComChannelList inputs, final ComChannelList outputs)
	{
		//Define internal variables
		_name = "NewSearchEvent";
		
		//Define the event states
		State state = this.getState();
		
		_outputs = new ComChannelList();
		_outputs.putAll(outputs);
		
		//Define the Event transitions
		//(IDLE,[E!=TARGET_SIGHTED_T_EVENT,V=UAV_FLYING_OP],[],1,NEXT,1.0)X(IDLE,[E=TARGET_SIGHTED_T_EVENT],[])
		_transition = new Transition(getInternalVars(), inputs, outputs, state) {
			@SuppressWarnings("unchecked")
			@Override 
			public boolean isEnabled() {
				
				//TODO Check the simulator to see if we have a transition already
				Boolean target_sighted_event = (Boolean) _inputs.get(Channels.TARGET_SIGHTED_T_EVENT.name()).getValue();
				UAV.VIDEO_UAV_OP_COMM uav_state = (UAV.VIDEO_UAV_OP_COMM) _inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue();
				if ( UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(uav_state) && target_sighted_event != null && !target_sighted_event ) {
					this.setTempOutput(Channels.TARGET_SIGHTED_T_EVENT.name(), true);
					return true;
				} else
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
		return this.getState();
	}

	@Override
	public void updateTransitions() {
		// TODO Auto-generated method stub
		
	}

}
