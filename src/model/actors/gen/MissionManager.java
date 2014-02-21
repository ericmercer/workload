package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class MissionManager extends Actor {
public enum DATA_MM_MM_COMM{
	MM_START_LISTEN_TO_OP_MM,
	MM_START_LISTEN_TO_VO_MM,
	MM_START_LISTEN_TO_PS_MM,
	MM_STOP_LISTEN_TO_PS_MM,
	MM_STOP_LISTEN_TO_OP_MM,
	MM_STOP_LISTEN_TO_VO_MM,
}
public enum AUDIO_MM_PS_COMM{
	MM_ACK_PS,
}
public enum AUDIO_MM_VO_COMM{
	MM_ACK_VO,
}
public enum AUDIO_MM_OP_COMM{
	MM_ACK_OP,
}
public MissionManager(ComChannelList inputs, ComChannelList outputs) {
	setName("MissionManager");
	State RX_PS = new State("RX_PS");
	State RX_VO = new State("RX_VO");
	State RX_OP = new State("RX_OP");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeRX_PS(inputs, outputs, RX_PS, IDLE);
	initializeRX_VO(inputs, outputs, RX_VO, IDLE);
	initializeIDLE(inputs, outputs, RX_PS, RX_VO, IDLE, RX_OP);
	initializeRX_OP(inputs, outputs, RX_OP, IDLE);
	startState(IDLE);
}
 public void initializeRX_PS(ComChannelList inputs, ComChannelList outputs, State RX_PS, State IDLE) {
	// (RX_PS,[A=PS_NEW_SEARCH_AOI_MM],[],1,NEXT,1.0)x(IDLE,[D=MM_STOP_LISTEN_TO_PS_MM],[AREA_OF_INTEREST=NEW])
	RX_PS.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!ParentSearch.AUDIO_PS_MM_COMM.PS_NEW_SEARCH_AOI_MM.equals(_inputs.get(Channels.AUDIO_PS_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_LISTEN_TO_PS_MM);
			setTempInternalVar("AREA_OF_INTEREST", "NEW");
			return true;
		}
	}); // in comments
	add(RX_PS);
}
 public void initializeRX_VO(ComChannelList inputs, ComChannelList outputs, State RX_VO, State IDLE) {
	// (RX_VO,[A=VO_TARGET_SIGHTED_F_MM],[],1,NEXT,1.0)x(IDLE,[D=MM_STOP_LISTEN_TO_VO_MM],[TARGET_SIGHTED_F=TRUE])
	RX_VO.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!VideoOperator.AUDIO_VO_MM_COMM.VO_TARGET_SIGHTED_F_MM.equals(_inputs.get(Channels.AUDIO_VO_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_LISTEN_TO_VO_MM);
			setTempInternalVar("TARGET_SIGHTED_F", true);
			return true;
		}
	}); // in comments
	add(RX_VO);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State RX_PS, State RX_VO, State IDLE, State RX_OP) {
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
	// (IDLE,[A=VO_POKE_MM],[],1,NEXT,1.0)x(RX_VO,[A=MM_ACK_VO,D=MM_START_LISTEN_TO_VO_MM],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, RX_VO, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!VideoOperator.AUDIO_VO_MM_COMM.VO_POKE_MM.equals(_inputs.get(Channels.AUDIO_VO_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_MM_VO_COMM.name(), MissionManager.AUDIO_MM_VO_COMM.MM_ACK_VO);
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_START_LISTEN_TO_VO_MM);
			return true;
		}
	}); // in comments
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
	// (IDLE,[],[AREA_OF_INTEREST=NEW,SEARCH_COMPLETE=TRUE,TARGET_SIGHTED_F=TRUE,FINAL_CHECK!=DONE],0,NEXT,1.0)X(IDLE,[],[FINAL_CHECK=DONE])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!"NEW".equals(_internal_vars.getVariable ("AREA_OF_INTEREST"))) {
				return false;
			}
			if(!new Boolean(true).equals(_internal_vars.getVariable ("SEARCH_COMPLETE"))) {
				return false;
			}
			if(!new Boolean(true).equals(_internal_vars.getVariable ("TARGET_SIGHTED_F"))) {
				return false;
			}
			if("DONE".equals(_internal_vars.getVariable ("FINAL_CHECK"))) {
				return false;
			}
			setTempInternalVar("FINAL_CHECK", "DONE");
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeRX_OP(ComChannelList inputs, ComChannelList outputs, State RX_OP, State IDLE) {
	// (RX_OP,[A=OP_SEARCH_COMPLETE_MM],[],1,NEXT,1.0)x(IDLE,[D=MM_STOP_LISTEN_TO_OP_MM],[SEARCH_COMPLETE=TRUE])
	RX_OP.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.AUDIO_OP_MM_COMM.OP_SEARCH_COMPLETE_MM.equals(_inputs.get(Channels.AUDIO_OP_MM_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_MM_MM_COMM.name(), MissionManager.DATA_MM_MM_COMM.MM_STOP_LISTEN_TO_OP_MM);
			setTempInternalVar("SEARCH_COMPLETE", true);
			return true;
		}
	}); // in comments
	add(RX_OP);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("AREA_OF_INTEREST", "");
	_internal_vars.addVariable("SEARCH_COMPLETE", false);
	_internal_vars.addVariable("TARGET_SIGHTED_F", false);
	_internal_vars.addVariable("FINAL_CHECK", "");
}
}