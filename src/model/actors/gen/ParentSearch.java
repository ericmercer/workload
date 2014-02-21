package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class ParentSearch extends Actor {
public enum DATA_PS_PS_COMM{
	PS_START_TRANSMIT_NEW_SEARCH_AOI_PS,
	PS_STOP_TRANSMIT_NEW_SEARCH_AOI_PS,
}
public enum AUDIO_PS_MM_COMM{
	PS_POKE_MM,
	PS_NEW_SEARCH_AOI_MM,
}
public ParentSearch(ComChannelList inputs, ComChannelList outputs) {
	setName("ParentSearch");
	State END_MM = new State("END_MM");
	State TX_MM = new State("TX_MM");
	State POKE_MM = new State("POKE_MM");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeIDLE(inputs, outputs, IDLE, POKE_MM);
	initializeTX_MM(inputs, outputs, TX_MM, END_MM);
	initializeEND_MM(inputs, outputs, END_MM, IDLE);
	initializePOKE_MM(inputs, outputs, POKE_MM, TX_MM);
	startState(IDLE);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State IDLE, State POKE_MM) {
	// (IDLE,[],[COMM!=SENT],1,NEXT,1.0)X(POKE_MM,[A=PS_POKE_MM,D=PS_START_TRANSMIT_NEW_SEARCH_AOI_PS],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, POKE_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if("SENT".equals(_internal_vars.getVariable ("COMM"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_PS_MM_COMM.name(), ParentSearch.AUDIO_PS_MM_COMM.PS_POKE_MM);
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_START_TRANSMIT_NEW_SEARCH_AOI_PS);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeTX_MM(ComChannelList inputs, ComChannelList outputs, State TX_MM, State END_MM) {
	// (TX_MM,[],[],0,TX_DATA_DUR,1.0)X(END_MM,[A=PS_NEW_SEARCH_AOI_MM,D=PS_STOP_TRANSMIT_NEW_SEARCH_AOI_PS],[COMM=SENT])
	TX_MM.add(new Transition(_internal_vars, inputs, outputs, END_MM, Duration.TX_DATA_DUR.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.AUDIO_PS_MM_COMM.name(), ParentSearch.AUDIO_PS_MM_COMM.PS_NEW_SEARCH_AOI_MM);
			setTempOutput(Channels.DATA_PS_PS_COMM.name(), ParentSearch.DATA_PS_PS_COMM.PS_STOP_TRANSMIT_NEW_SEARCH_AOI_PS);
			setTempInternalVar("COMM", "SENT");
			return true;
		}
	}); // in comments
	add(TX_MM);
}
 public void initializeEND_MM(ComChannelList inputs, ComChannelList outputs, State END_MM, State IDLE) {
	// (END_MM,[],[],0,NEXT,1.0)X(IDLE,[],[])
	END_MM.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(END_MM);
}
 public void initializePOKE_MM(ComChannelList inputs, ComChannelList outputs, State POKE_MM, State TX_MM) {
	// (POKE_MM,[A=MM_ACK_PS],[],0,NEXT,1.0)X(TX_MM,[],[])
	POKE_MM.add(new Transition(_internal_vars, inputs, outputs, TX_MM, Duration.NEXT.getRange(), 0, 1.0) {
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
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("COMM", "");
}
}