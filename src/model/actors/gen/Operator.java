package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class Operator extends Actor {
public enum AUDIO_OP_MM_COMM{
	OP_ACK_MM,
	OP_POKE_MM,
	OP_SEARCH_COMPLETE_MM,
}
public enum DATA_OP_UAV_COMM{
	OP_TAKE_OFF_UAV,
}
public enum DATA_OP_OP_COMM{
	OP_START_LISTEN_TO_MM_OP,
	OP_STOP_LISTEN_TO_MM_OP__OP_START_SET_AOI_OP,
	OP_START_LAUNCH_OP,
	OP_STOP_LAUNCH_OP,
	OP_STOP_SET_AOI_OP__OP_START_SEARCH_OP__OP_START_SEARCH_AREA_OP,
	OP_STOP_SEARCH_OP__OP_START_TRANSMIT_SEARCH_COMPLETE_OP__OP_STOP_SEARCH_AREA_OP,
	OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP,
}
public enum DATA_OP_OGUI_COMM{
	OP_NEW_SEARCH_AOI_OGUI,
}
public Operator(ComChannelList inputs, ComChannelList outputs) {
	setName("Operator");
	State END_MM = new State("END_MM");
	State TX_MM = new State("TX_MM");
	State POKE_MM = new State("POKE_MM");
	State END_OGUI = new State("END_OGUI");
	State TX_OGUI = new State("TX_OGUI");
	State POKE_OGUI = new State("POKE_OGUI");
	State OBSERVE_GUI = new State("OBSERVE_GUI");
	State LAUNCH_UAV = new State("LAUNCH_UAV");
	State RX_MM = new State("RX_MM");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeIDLE(inputs, outputs, LAUNCH_UAV, IDLE, RX_MM);
	initializeEND_OGUI(inputs, outputs, END_OGUI, OBSERVE_GUI);
	initializeLAUNCH_UAV(inputs, outputs, LAUNCH_UAV, OBSERVE_GUI);
	initializeTX_MM(inputs, outputs, TX_MM, END_MM);
	initializePOKE_OGUI(inputs, outputs, POKE_OGUI, TX_OGUI);
	initializeTX_OGUI(inputs, outputs, TX_OGUI, END_OGUI);
	initializeEND_MM(inputs, outputs, END_MM, IDLE);
	initializeOBSERVE_GUI(inputs, outputs, POKE_MM, OBSERVE_GUI, POKE_OGUI);
	initializeRX_MM(inputs, outputs, RX_MM, IDLE);
	startState(IDLE);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State LAUNCH_UAV, State IDLE, State RX_MM) {
	// (IDLE,[A=MM_POKE_OP],[],2,NEXT,1.0)x(RX_MM,[A=OP_ACK_MM,D=OP_START_LISTEN_TO_MM_OP],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, RX_MM, Duration.NEXT.getRange(), 2, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_OP_COMM.MM_POKE_OP.equals(_inputs.get(Channels.AUDIO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_ACK_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_START_LISTEN_TO_MM_OP);
			return true;
		}
	}); // in comments
	// (IDLE,[V!=UAV_FLYING_NORMAL_OP],[NEW_SEARCH_AOI>0],1,NEXT,1.0)x(LAUNCH_UAV,[D=OP_TAKE_OFF_UAV,D=OP_START_LAUNCH_OP],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, LAUNCH_UAV, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_NORMAL_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			if(_internal_vars.getVariable("NEW_SEARCH_AOI") instanceof Integer && new Integer(0) >= (Integer) _internal_vars.getVariable ("NEW_SEARCH_AOI")) {
				return false;
			}
			setTempOutput(Channels.DATA_OP_UAV_COMM.name(), Operator.DATA_OP_UAV_COMM.OP_TAKE_OFF_UAV);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_START_LAUNCH_OP);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeEND_OGUI(ComChannelList inputs, ComChannelList outputs, State END_OGUI, State OBSERVE_GUI) {
	// (END_OGUI,[],[],1,NEXT,1.0)x(OBSERVE_GUI,[],[])
	END_OGUI.add(new Transition(_internal_vars, inputs, outputs, OBSERVE_GUI, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(END_OGUI);
}
 public void initializeLAUNCH_UAV(ComChannelList inputs, ComChannelList outputs, State LAUNCH_UAV, State OBSERVE_GUI) {
	// (LAUNCH_UAV,[V=UAV_FLYING_NORMAL_OP],[],1,NEXT,1.0)x(OBSERVE_GUI,[D=OP_STOP_LAUNCH_OP],[TAKE_OFF=FALSE])
	LAUNCH_UAV.add(new Transition(_internal_vars, inputs, outputs, OBSERVE_GUI, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_NORMAL_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_LAUNCH_OP);
			setTempInternalVar("TAKE_OFF", false);
			return true;
		}
	}); // in comments
	add(LAUNCH_UAV);
}
 public void initializeTX_MM(ComChannelList inputs, ComChannelList outputs, State TX_MM, State END_MM) {
	// (TX_MM,[],[SEARCH_COMPLETE=TRUE],1,OP_TX_MM,1.0)x(END_MM,[A=OP_SEARCH_COMPLETE_MM,D=OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP],[])
	TX_MM.add(new Transition(_internal_vars, inputs, outputs, END_MM, Duration.OP_TX_MM.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!new Boolean(true).equals(_internal_vars.getVariable ("SEARCH_COMPLETE"))) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_SEARCH_COMPLETE_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_TRANSMIT_SEARCH_COMPLETE_OP);
			return true;
		}
	}); // in comments
	add(TX_MM);
}
 public void initializePOKE_OGUI(ComChannelList inputs, ComChannelList outputs, State POKE_OGUI, State TX_OGUI) {
	// (POKE_OGUI,[],[],0,NEXT,1.0)X(TX_OGUI,[],[])
	POKE_OGUI.add(new Transition(_internal_vars, inputs, outputs, TX_OGUI, Duration.NEXT.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			return true;
		}
	}); // in comments
	add(POKE_OGUI);
}
 public void initializeTX_OGUI(ComChannelList inputs, ComChannelList outputs, State TX_OGUI, State END_OGUI) {
	// (TX_OGUI,[],[NEW_SEARCH_AOI>0],1,OP_TX_OGUI,1.0)x(END_OGUI,[D=OP_NEW_SEARCH_AOI_OGUI,D=OP_STOP_SET_AOI_OP__OP_START_SEARCH_OP__OP_START_SEARCH_AREA_OP],[NEW_SEARCH_AOI=--])
	TX_OGUI.add(new Transition(_internal_vars, inputs, outputs, END_OGUI, Duration.OP_TX_OGUI.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(_internal_vars.getVariable("NEW_SEARCH_AOI") instanceof Integer && new Integer(0) >= (Integer) _internal_vars.getVariable ("NEW_SEARCH_AOI")) {
				return false;
			}
			setTempOutput(Channels.DATA_OP_OGUI_COMM.name(), Operator.DATA_OP_OGUI_COMM.OP_NEW_SEARCH_AOI_OGUI);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_SET_AOI_OP__OP_START_SEARCH_OP__OP_START_SEARCH_AREA_OP);
			setTempInternalVar("NEW_SEARCH_AOI", (Integer)_internal_vars.getVariable("NEW_SEARCH_AOI") - 1);
			return true;
		}
	}); // in comments
	add(TX_OGUI);
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
 public void initializeOBSERVE_GUI(ComChannelList inputs, ComChannelList outputs, State POKE_MM, State OBSERVE_GUI, State POKE_OGUI) {
	// (OBSERVE_GUI,[],[NEW_SEARCH_AOI>0],1,NEXT,1.0)x(POKE_OGUI,[],[])
	OBSERVE_GUI.add(new Transition(_internal_vars, inputs, outputs, POKE_OGUI, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(_internal_vars.getVariable("NEW_SEARCH_AOI") instanceof Integer && new Integer(0) >= (Integer) _internal_vars.getVariable ("NEW_SEARCH_AOI")) {
				return false;
			}
			return true;
		}
	}); // in comments
	// (OBSERVE_GUI,[V=OGUI_PATH_COMPLETE_OP],[],1,NEXT,1.0)X(POKE_MM,[A=OP_POKE_MM,D=OP_STOP_SEARCH_OP__OP_START_TRANSMIT_SEARCH_COMPLETE_OP__OP_STOP_SEARCH_AREA_OP],[SEARCH_COMPLETE=TRUE,SEARCH_AOI_COMPLETE=TRUE])
	OBSERVE_GUI.add(new Transition(_internal_vars, inputs, outputs, POKE_MM, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_PATH_COMPLETE_OP.equals(_inputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_POKE_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_SEARCH_OP__OP_START_TRANSMIT_SEARCH_COMPLETE_OP__OP_STOP_SEARCH_AREA_OP);
			setTempInternalVar("SEARCH_COMPLETE", true);
			setTempInternalVar("SEARCH_AOI_COMPLETE", true);
			return true;
		}
	}); // in comments
	add(OBSERVE_GUI);
}
 public void initializeRX_MM(ComChannelList inputs, ComChannelList outputs, State RX_MM, State IDLE) {
	// (RX_MM,[A=MM_NEW_SEARCH_AOI_OP],[],1,NEXT,1.0)x(IDLE,[D=OP_STOP_LISTEN_TO_MM_OP__OP_START_SET_AOI_OP],[NEW_SEARCH_AOI=++])
	RX_MM.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_OP_COMM.MM_NEW_SEARCH_AOI_OP.equals(_inputs.get(Channels.AUDIO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_LISTEN_TO_MM_OP__OP_START_SET_AOI_OP);
			setTempInternalVar("NEW_SEARCH_AOI", (Integer)_internal_vars.getVariable("NEW_SEARCH_AOI") + 1);
			return true;
		}
	}); // in comments
	add(RX_MM);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("NEW_SEARCH_AOI", 0);
	_internal_vars.addVariable("TAKE_OFF", false);
	_internal_vars.addVariable("SEARCH_COMPLETE", false);
	_internal_vars.addVariable("SEARCH_AOI_COMPLETE", false);
}
}