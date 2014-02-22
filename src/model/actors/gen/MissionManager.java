package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class MissionManager extends Actor {
public enum DATA_MM_MM_COMM{
	MM_START_LISTEN_TO_PS_MM,
	MM_STOP_LISTEN_TO_PS_MM__MM_START_TRANSMIT_AOI_MM__MM_START_SEARCH_MM,
	MM_STOP_TRANSMIT_AOI_MM,
	MM_START_LISTEN_TO_OP_MM,
	MM_STOP_LISTEN_TO_OP_MM__MM_START_TRANSMIT_SEARCH_COMPLETE_MM,
	MM_STOP_TRANSMIT_SEARCH_COMPLETE_MM__MM_STOP_SEARCH_MM,
}
public enum AUDIO_MM_PS_COMM{
	MM_ACK_PS,
	MM_POKE_PS,
	MM_SEARCH_COMPLETE_PS,
}
public enum AUDIO_MM_OP_COMM{
	MM_POKE_OP,
	MM_NEW_SEARCH_AOI_OP,
	MM_ACK_OP,
}
public MissionManager(ComChannelList inputs, ComChannelList outputs) {
	setName("MissionManager");
	State END_PS = new State("END_PS");
	State TX_PS = new State("TX_PS");
	State POKE_PS = new State("POKE_PS");
	State RX_OP = new State("RX_OP");
	State END_OP = new State("END_OP");
	State TX_OP = new State("TX_OP");
	State POKE_OP = new State("POKE_OP");
	State RX_PS = new State("RX_PS");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeRX_PS(inputs, outputs, RX_PS, IDLE);
	initializeIDLE(inputs, outputs, POKE_PS, RX_OP, POKE_OP, IDLE, RX_PS);
	initializePOKE_PS(inputs, outputs, POKE_PS, TX_PS);
	initializeTX_PS(inputs, outputs, TX_PS, END_PS);
	initializeRX_OP(inputs, outputs, RX_OP, IDLE);
	initializeEND_OP(inputs, outputs, END_OP, IDLE);
	initializePOKE_OP(inputs, outputs, POKE_OP, TX_OP);
	initializeEND_PS(inputs, outputs, END_PS, IDLE);
	initializeTX_OP(inputs, outputs, TX_OP, END_OP);
	startState(IDLE);
}
 public void initializeRX_PS(ComChannelList inputs, ComChannelList outputs, State RX_PS, State IDLE) {
	// (RX_PS,[A=PS_NEW_SEARCH_AOI_MM],[],1,NEXT,1.0)x(IDLE,[D=MM_STOP_LISTEN_TO_PS_MM__MM_START_TRANSMIT_AOI_MM__MM_START_SEARCH_MM],[AREA_OF_INTEREST=NEW])
	RX_PS.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!ParentSearch.AUDIO_PS_MM_COMM.PS_NEW_SEARCH_AOI_MM.equals(_inputs.get(Channels.AUDIO_PS_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_LISTEN_TO_PS_MM__MM_START_TRANSMIT_AOI_MM__MM_START_SEARCH_MM);
			setTempInternalVar("AREA_OF_INTEREST", "NEW");
			return true;
		}
	}); // in comments
	add(RX_PS);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State POKE_PS, State RX_OP, State POKE_OP, State IDLE, State RX_PS) {
	// (IDLE,[A=PS_POKE_MM],[],1,NEXT,1.0)x(RX_PS,[A=MM_ACK_PS,D=MM_START_LISTEN_TO_PS_MM],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, RX_PS, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!ParentSearch.AUDIO_PS_MM_COMM.PS_POKE_MM.equals(_inputs.get(Channels.AUDIO_PS_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_PS_COMM.name(), MissionManager.AUDIO_MM_PS_COMM.MM_ACK_PS);
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_START_LISTEN_TO_PS_MM);
			return true;
		}
	}); // in comments
	// (IDLE,[],[AREA_OF_INTEREST=NEW],1,NEXT,1.0)x(POKE_OP,[A=MM_POKE_OP],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, POKE_OP, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!"NEW".equals(_internal_vars.getVariable ("AREA_OF_INTEREST"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_OP_COMM.name(), MissionManager.AUDIO_MM_OP_COMM.MM_POKE_OP);
			return true;
		}
	}); // in comments
	// (IDLE,[A=OP_POKE_MM],[],1,NEXT,1.0)x(RX_OP,[A=MM_ACK_OP,D=MM_START_LISTEN_TO_OP_MM],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, RX_OP, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.AUDIO_OP_MM_COMM.OP_POKE_MM.equals(_inputs.get(Channels.AUDIO_OP_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_OP_COMM.name(), MissionManager.AUDIO_MM_OP_COMM.MM_ACK_OP);
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_START_LISTEN_TO_OP_MM);
			return true;
		}
	}); // in comments
	// (IDLE,[],[SEARCH_COMPLETE=TRUE],1,NEXT,1.0)x(POKE_PS,[A=MM_POKE_PS],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, POKE_PS, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!new Boolean(true).equals(_internal_vars.getVariable ("SEARCH_COMPLETE"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_PS_COMM.name(), MissionManager.AUDIO_MM_PS_COMM.MM_POKE_PS);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializePOKE_PS(ComChannelList inputs, ComChannelList outputs, State POKE_PS, State TX_PS) {
	// (POKE_PS,[A=PS_ACK_MM],[],1,NEXT,1.0)x(TX_PS,[],[])
	POKE_PS.add(new Transition(_internal_vars, inputs, outputs, TX_PS, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!ParentSearch.AUDIO_PS_MM_COMM.PS_ACK_MM.equals(_inputs.get(Channels.AUDIO_PS_MM_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(POKE_PS);
}
 public void initializeTX_PS(ComChannelList inputs, ComChannelList outputs, State TX_PS, State END_PS) {
	// (TX_PS,[],[SEARCH_COMPLETE=TRUE],1,MM_TX_PS,1.0)x(END_PS,[A=MM_SEARCH_COMPLETE_PS,D=MM_STOP_TRANSMIT_SEARCH_COMPLETE_MM__MM_STOP_SEARCH_MM],[SEARCH_COMPLETE=FALSE])
	TX_PS.add(new Transition(_internal_vars, inputs, outputs, END_PS, Duration.MM_TX_PS.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!new Boolean(true).equals(_internal_vars.getVariable ("SEARCH_COMPLETE"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_PS_COMM.name(), MissionManager.AUDIO_MM_PS_COMM.MM_SEARCH_COMPLETE_PS);
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_TRANSMIT_SEARCH_COMPLETE_MM__MM_STOP_SEARCH_MM);
			setTempInternalVar("SEARCH_COMPLETE", false);
			return true;
		}
	}); // in comments
	add(TX_PS);
}
 public void initializeRX_OP(ComChannelList inputs, ComChannelList outputs, State RX_OP, State IDLE) {
	// (RX_OP,[A=OP_SEARCH_COMPLETE_MM],[],1,NEXT,1.0)x(IDLE,[D=MM_STOP_LISTEN_TO_OP_MM__MM_START_TRANSMIT_SEARCH_COMPLETE_MM],[SEARCH_COMPLETE=TRUE])
	RX_OP.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.AUDIO_OP_MM_COMM.OP_SEARCH_COMPLETE_MM.equals(_inputs.get(Channels.AUDIO_OP_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_LISTEN_TO_OP_MM__MM_START_TRANSMIT_SEARCH_COMPLETE_MM);
			setTempInternalVar("SEARCH_COMPLETE", true);
			return true;
		}
	}); // in comments
	add(RX_OP);
}
 public void initializeEND_OP(ComChannelList inputs, ComChannelList outputs, State END_OP, State IDLE) {
	// (END_OP,[],[],1,NEXT,1.0)x(IDLE,[],[])
	END_OP.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(END_OP);
}
 public void initializePOKE_OP(ComChannelList inputs, ComChannelList outputs, State POKE_OP, State TX_OP) {
	// (POKE_OP,[A=OP_ACK_MM],[],1,NEXT,1.0)x(TX_OP,[],[])
	POKE_OP.add(new Transition(_internal_vars, inputs, outputs, TX_OP, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.AUDIO_OP_MM_COMM.OP_ACK_MM.equals(_inputs.get(Channels.AUDIO_OP_MM_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(POKE_OP);
}
 public void initializeEND_PS(ComChannelList inputs, ComChannelList outputs, State END_PS, State IDLE) {
	// (END_PS,[],[],1,NEXT,1.0)x(IDLE,[],[])
	END_PS.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(END_PS);
}
 public void initializeTX_OP(ComChannelList inputs, ComChannelList outputs, State TX_OP, State END_OP) {
	// (TX_OP,[],[AREA_OF_INTEREST=NEW],1,MM_TX_OP,1.0)x(END_OP,[A=MM_NEW_SEARCH_AOI_OP,D=MM_STOP_TRANSMIT_AOI_MM],[AREA_OF_INTEREST=CURRENT])
	TX_OP.add(new Transition(_internal_vars, inputs, outputs, END_OP, Duration.MM_TX_OP.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!"NEW".equals(_internal_vars.getVariable ("AREA_OF_INTEREST"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_OP_COMM.name(), MissionManager.AUDIO_MM_OP_COMM.MM_NEW_SEARCH_AOI_OP);
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_TRANSMIT_AOI_MM);
			setTempInternalVar("AREA_OF_INTEREST", "CURRENT");
			return true;
		}
	}); // in comments
	add(TX_OP);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("AREA_OF_INTEREST", null);
	_internal_vars.addVariable("SEARCH_COMPLETE", false);
}
}