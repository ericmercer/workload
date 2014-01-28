package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class UAVBattery extends Actor {
public enum DATA_UAVBAT_UAV_COMM{
	UAVBAT_GOOD_UAV,
	UAVBAT_LOW_UAV,
	UAVBAT_DEAD_UAV,
}
public UAVBattery(ComChannelList inputs, ComChannelList outputs) {
	setName("UAVBattery");
	State DEAD = new State("DEAD");
	State LOW = new State("LOW");
	State GOOD = new State("GOOD");
	State IDLE = new State("IDLE");
	initializeInternalVariables();
	initializeIDLE(inputs, outputs, IDLE, GOOD);
	initializeGOOD(inputs, outputs, IDLE, GOOD, LOW);
	initializeDEAD(inputs, outputs, DEAD, IDLE);
	initializeLOW(inputs, outputs, IDLE, LOW, DEAD);
	startState(IDLE);
}
 public void initializeIDLE(ComChannelList inputs, ComChannelList outputs, State IDLE, State GOOD) {
	// (IDLE,[D=OP_TAKE_OFF_UAV],[],1,NEXT,1.0)X(GOOD,[D=UAVBAT_GOOD_UAV,D=UAV_BATTERY_GOOD_OGUI],[])
	IDLE.add(new Transition(_internal_vars, inputs, outputs, GOOD, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.DATA_OP_UAV_COMM.OP_TAKE_OFF_UAV.equals(_inputs.get(Channels.DATA_OP_UAV_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVBAT_UAV_COMM.name(), UAVBattery.DATA_UAVBAT_UAV_COMM.UAVBAT_GOOD_UAV);
			setTempOutput(Channels.DATA_UAV_OGUI_COMM.name(), UAV.DATA_UAV_OGUI_COMM.UAV_BATTERY_GOOD_OGUI);
			return true;
		}
	}); // in comments
	add(IDLE);
}
 public void initializeGOOD(ComChannelList inputs, ComChannelList outputs, State IDLE, State GOOD, State LOW) {
	// (GOOD,[],[],1,UAVBAT_ACTIVE_TO_LOW,1.0)X(LOW,[D=UAVBAT_LOW_UAV,D=UAV_BATTERY_LOW_OGUI],[])
	GOOD.add(new Transition(_internal_vars, inputs, outputs, LOW, Duration.UAVBAT_ACTIVE_TO_LOW.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.DATA_UAVBAT_UAV_COMM.name(), UAVBattery.DATA_UAVBAT_UAV_COMM.UAVBAT_LOW_UAV);
			setTempOutput(Channels.DATA_UAV_OGUI_COMM.name(), UAV.DATA_UAV_OGUI_COMM.UAV_BATTERY_LOW_OGUI);
			return true;
		}
	}); // in comments
	// (GOOD,[V=UAV_LANDED_OP],[],2,NEXT,1.0)X(IDLE,[],[])
	GOOD.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(GOOD);
}
 public void initializeDEAD(ComChannelList inputs, ComChannelList outputs, State DEAD, State IDLE) {
	// (DEAD,[V=UAV_LANDED_OP],[],2,NEXT,1.0)X(IDLE,[],[])
	DEAD.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(DEAD);
}
 public void initializeLOW(ComChannelList inputs, ComChannelList outputs, State IDLE, State LOW, State DEAD) {
	// (LOW,[],[],1,UAVBAT_LOW_TO_DEAD,1.0)X(DEAD,[D=UAVBAT_DEAD_UAV],[])
	LOW.add(new Transition(_internal_vars, inputs, outputs, DEAD, Duration.UAVBAT_LOW_TO_DEAD.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.DATA_UAVBAT_UAV_COMM.name(), UAVBattery.DATA_UAVBAT_UAV_COMM.UAVBAT_DEAD_UAV);
			return true;
		}
	}); // in comments
	// (LOW,[V=UAV_LANDED_OP],[],2,NEXT,1.0)X(IDLE,[],[])
	LOW.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			return true;
		}
	}); // in comments
	add(LOW);
}
@Override
protected void initializeInternalVariables() {
}
}