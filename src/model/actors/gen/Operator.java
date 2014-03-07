package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class Operator extends Actor {
public enum AUDIO_OP_MM_COMM{
	OP_POKE_MM,
	OP_TX_MM,
	OP_SEARCH_COMPLETE_MM,
}
public enum DATA_OP_OP_COMM{
	OP_START_TRANSMIT_SEARCH_COMPLETE_OP,
	OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP,
}
public Operator(ComChannelList inputs, ComChannelList outputs) {
	setName("Operator");
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
	// (IDLE,[],[COMM!=SENT],1,NEXT,1.0)X(POKE_MM,[A=OP_POKE_MM,D=OP_START_TRANSMIT_SEARCH_COMPLETE_OP],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, POKE_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if("SENT".equals(_internal_vars.getVariable ("COMM"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_POKE_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_START_TRANSMIT_SEARCH_COMPLETE_OP);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeTX_MM(ComChannelList inputs, ComChannelList outputs, State TX_MM, State END_MM) {
	// (TX_MM,[],[],0,TX_DATA_DUR,1.0)X(END_MM,[A=OP_SEARCH_COMPLETE_MM,D=OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP],[COMM=SENT])
	TX_MM.add(new Transition(_internal_vars, inputs, outputs, END_MM, Duration.TX_DATA_DUR.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_SEARCH_COMPLETE_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP);
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
	// (POKE_MM,[A=MM_ACK_OP],[],0,NEXT,1.0)X(TX_MM,[A=OP_TX_MM],[])
	POKE_MM.add(new Transition(_internal_vars, inputs, outputs, TX_MM, Duration.NEXT.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_OP_COMM.MM_ACK_OP.equals(_inputs.get(Channels.AUDIO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_TX_MM);
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