package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class ParentSearch extends Actor {
public enum DATA_PS_PS_COMM{
	PS_START_TRANSMIT_AOI_PS,
	PS_STOP_TRANSMIT_AOI_PS,
	PS_START_LISTEN_TO_MM_PS,
	PS_STOP_LISTEN_TO_MM_PS,
}
public enum AUDIO_PS_MM_COMM{
	PS_POKE_MM,
	PS_NEW_SEARCH_AOI_MM,
	PS_ACK_MM,
}
public ParentSearch(ComChannelList inputs, ComChannelList outputs) {
	setName("ParentSearch");
	State RX_MM = new State("RX_MM");
	State END_MM = new State("END_MM");
	State TX_MM = new State("TX_MM");
	State POKE_MM = new State("POKE_MM");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeIDLE(inputs, outputs, RX_MM, IDLE, POKE_MM);
	initializeTX_MM(inputs, outputs, TX_MM, END_MM);
	initializeEND_MM(inputs, outputs, END_MM, IDLE);
	initializePOKE_MM(inputs, outputs, POKE_MM, TX_MM);
	initializeRX_MM(inputs, outputs, RX_MM, IDLE);
	startState(IDLE);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State RX_MM, State IDLE, State POKE_MM) {
	// (IDLE,[E=NEW_SEARCH_EVENT],[],1,NEXT,1.0)x(POKE_MM,[A=PS_POKE_MM,D=PS_START_TRANSMIT_AOI_PS],[SEARCH_ACTIVE=TRUE,NEW_SEARCH_AOI=++])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, POKE_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(_inputs.get(Channels.NEW_SEARCH_EVENT.name()).getValue() == null || !(Boolean)_inputs.get(Channels.NEW_SEARCH_EVENT.name()).getValue()) {
				return false;
			}
			setTempOutput(Channels.AUDIO_PS_MM_COMM.name(), ParentSearch.AUDIO_PS_MM_COMM.PS_POKE_MM);
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_START_TRANSMIT_AOI_PS);
			setTempInternalVar("SEARCH_ACTIVE", true);
			setTempInternalVar("NEW_SEARCH_AOI", (Integer)_internal_vars.getVariable("NEW_SEARCH_AOI") + 1);
			return true;
		}
	}); // in comments
	// (IDLE,[A=MM_POKE_PS],[],1,NEXT,1.0)x(RX_MM,[A=PS_ACK_MM,D=PS_START_LISTEN_TO_MM_PS],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, RX_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_PS_COMM.MM_POKE_PS.equals(_inputs.get(Channels.AUDIO_MM_PS_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_PS_MM_COMM.name(), ParentSearch.AUDIO_PS_MM_COMM.PS_ACK_MM);
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_START_LISTEN_TO_MM_PS);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeTX_MM(ComChannelList inputs, ComChannelList outputs, State TX_MM, State END_MM) {
	// (TX_MM,[],[NEW_SEARCH_AOI>0],1,PS_TX_MM,1.0)x(END_MM,[A=PS_NEW_SEARCH_AOI_MM,D=PS_STOP_TRANSMIT_AOI_PS],[NEW_SEARCH_AOI=--])
	TX_MM.add(new Transition(_internal_vars, inputs, outputs, END_MM, Duration.PS_TX_MM.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(_internal_vars.getVariable("NEW_SEARCH_AOI") instanceof Integer && new Integer(0) >= (Integer) _internal_vars.getVariable ("NEW_SEARCH_AOI")) {
				return false;
			}
			setTempOutput(Channels.AUDIO_PS_MM_COMM.name(), ParentSearch.AUDIO_PS_MM_COMM.PS_NEW_SEARCH_AOI_MM);
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_STOP_TRANSMIT_AOI_PS);
			setTempInternalVar("NEW_SEARCH_AOI", (Integer)_internal_vars.getVariable("NEW_SEARCH_AOI") - 1);
			return true;
		}
	}); // in comments
	add(TX_MM);
}
 public void initializeEND_MM(ComChannelList inputs, ComChannelList outputs, State END_MM, State IDLE) {
	// (END_MM,[],[],1,NEXT,1.0)x(IDLE,[],[])
	END_MM.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(END_MM);
}
 public void initializePOKE_MM(ComChannelList inputs, ComChannelList outputs, State POKE_MM, State TX_MM) {
	// (POKE_MM,[A=MM_ACK_PS],[],1,NEXT,1.0)x(TX_MM,[],[])
	POKE_MM.add(new Transition(_internal_vars, inputs, outputs, TX_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_PS_COMM.MM_ACK_PS.equals(_inputs.get(Channels.AUDIO_MM_PS_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(POKE_MM);
}
 public void initializeRX_MM(ComChannelList inputs, ComChannelList outputs, State RX_MM, State IDLE) {
	// (RX_MM,[A=MM_SEARCH_COMPLETE_PS],[],1,NEXT,1.0)x(IDLE,[D=PS_STOP_LISTEN_TO_MM_PS],[SEARCH_COMPLETE=TRUE,SEARCH_ACTIVE=FALSE])
	RX_MM.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_PS_COMM.MM_SEARCH_COMPLETE_PS.equals(_inputs.get(Channels.AUDIO_MM_PS_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_STOP_LISTEN_TO_MM_PS);
			setTempInternalVar("SEARCH_COMPLETE", true);
			setTempInternalVar("SEARCH_ACTIVE", false);
			return true;
		}
	}); // in comments
	add(RX_MM);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("SEARCH_ACTIVE", false);
	_internal_vars.addVariable("NEW_SEARCH_AOI", 0);
	_internal_vars.addVariable("SEARCH_COMPLETE", false);
}
}