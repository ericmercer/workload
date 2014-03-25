package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class Operator extends Actor {
public enum VIDEO_OP_MM_COMM{
	OP_WAVE_MM,
}
public enum AUDIO_OP_MM_COMM{
	OP_HELLO_MM,
}
public enum DATA_OP_OP_COMM{
	OP_START_WAVE_OP__OP_STOP_FLATTERED_OP,
	OP_START_FLATTERED_OP__OP_STOP_FLATTERED_OP,
	OP_STOP_WAVE_OP,
}
public Operator(ComChannelList inputs, ComChannelList outputs) {
	setName("Operator");
	State WAVE = new State("WAVE");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeIDLE(inputs, outputs, IDLE, WAVE);
	initializeWAVE(inputs, outputs, WAVE, IDLE);
	startState(IDLE);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State IDLE, State WAVE) {
	// (IDLE,[V=MM_WAVE_OP],[APATHY<5],0,[2-5],1.0)X(WAVE,[V=OP_WAVE_MM,D=OP_START_WAVE_OP__OP_STOP_FLATTERED_OP],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, WAVE, new Range(2, 5), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.VIDEO_MM_OP_COMM.MM_WAVE_OP.equals(_inputs.get(Channels.VIDEO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			if(_internal_vars.getVariable("APATHY") instanceof Integer && new Integer(5) <= (Integer) _internal_vars.getVariable ("APATHY")) {
				return false;
			}
			setTempOutput(Channels.VIDEO_OP_MM_COMM.name(), Operator.VIDEO_OP_MM_COMM.OP_WAVE_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_START_WAVE_OP__OP_STOP_FLATTERED_OP);
			return true;
		}
	}); // in comments
	// (IDLE,[V=MM_WAVE_OP],[APATHY>=5],0,[2-5],1.0)X(IDLE,[D=OP_START_FLATTERED_OP__OP_STOP_FLATTERED_OP],[APATHY=--])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, new Range(2, 5), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.VIDEO_MM_OP_COMM.MM_WAVE_OP.equals(_inputs.get(Channels.VIDEO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			if(_internal_vars.getVariable("APATHY") instanceof Integer && new Integer(5) > (Integer) _internal_vars.getVariable ("APATHY")) {
				return false;
			}
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_START_FLATTERED_OP__OP_STOP_FLATTERED_OP);
			setTempInternalVar("APATHY", (Integer)_internal_vars.getVariable("APATHY") - 1);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeWAVE(ComChannelList inputs, ComChannelList outputs, State WAVE, State IDLE) {
	// (WAVE,[A=MM_HELLO_OP],[],0,[2-5],1.0)X(IDLE,[A=OP_HELLO_MM,D=OP_STOP_WAVE_OP],[APATHY=++])
	WAVE.add(new Transition(_internal_vars, inputs, outputs, IDLE, new Range(2, 5), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!MissionManager.AUDIO_MM_OP_COMM.MM_HELLO_OP.equals(_inputs.get(Channels.AUDIO_MM_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.AUDIO_OP_MM_COMM.name(), Operator.AUDIO_OP_MM_COMM.OP_HELLO_MM);
			setTempOutput(Channels.DATA_OP_OP_COMM.name(), Operator.DATA_OP_OP_COMM.OP_STOP_WAVE_OP);
			setTempInternalVar("APATHY", (Integer)_internal_vars.getVariable("APATHY") + 1);
			return true;
		}
	}); // in comments
	add(WAVE);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("APATHY", 0);
}
}