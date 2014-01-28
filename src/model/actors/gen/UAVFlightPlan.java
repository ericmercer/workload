package model.actors.gen;

import model.actors.*;
import model.team.*;
import simulator.*;

public class UAVFlightPlan extends Actor {
public enum DATA_UAVFP_UAV_COMM{
	UAVFP_PAUSED_UAV,
	UAVFP_COMPLETE_UAV,
	UAVFP_NO_PATH_UAV,
	UAVFP_YES_PATH_UAV,
}
public enum DATA_UAVFP_OGUI_COMM{
	UAVFP_PAUSED_OGUI,
	UAVFP_COMPLETE_OGUI,
	UAVFP_NO_PATH_OGUI,
	UAVFP_YES_PATH_OGUI,
}
public UAVFlightPlan(ComChannelList inputs, ComChannelList outputs) {
	setName("UAVFlightPlan");
	State RESUME_AFTER_LAUNCH = new State("RESUME_AFTER_LAUNCH");
	State COMPLETE = new State("COMPLETE");
	State PAUSED = new State("PAUSED");
	State YES_PATH = new State("YES_PATH");
	State NO_PATH = new State("NO_PATH");
	initializeInternalVariables();
	initializeNO_PATH(inputs, outputs, PAUSED, NO_PATH, YES_PATH);
	initializeYES_PATH(inputs, outputs, PAUSED, YES_PATH, COMPLETE);
	initializePAUSED(inputs, outputs, PAUSED, RESUME_AFTER_LAUNCH);
	initializeCOMPLETE(inputs, outputs, YES_PATH, COMPLETE, NO_PATH);
	initializeRESUME_AFTER_LAUNCH(inputs, outputs, RESUME_AFTER_LAUNCH, YES_PATH);
	startState(NO_PATH);
}
 public void initializeNO_PATH(ComChannelList inputs, ComChannelList outputs, State PAUSED, State NO_PATH, State YES_PATH) {
	// (NO_PATH,[D=OGUI_NEW_FP_UAV,V=UAV_FLYING_OP],[],1,NEXT,1.0)X(YES_PATH,[D=UAVFP_PAUSED_OGUI,D=UAVFP_PAUSED_UAV],[])
	NO_PATH.add(new Transition(_internal_vars, inputs, outputs, YES_PATH, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!OperatorGui.DATA_OGUI_UAV_COMM.OGUI_NEW_FP_UAV.equals(_inputs.get(Channels.DATA_OGUI_UAV_COMM.name()).getValue())) {
				return false;
			}
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_PAUSED_UAV);
			return true;
		}
	}); // in comments
	// (NO_PATH,[D=OGUI_NEW_FP_UAV,V!=UAV_FLYING_OP],[],1,NEXT,1.0)X(PAUSED,[D=UAVFP_PAUSED_OGUI,D=UAVFP_PAUSED_UAV],[])
	NO_PATH.add(new Transition(_internal_vars, inputs, outputs, PAUSED, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!OperatorGui.DATA_OGUI_UAV_COMM.OGUI_NEW_FP_UAV.equals(_inputs.get(Channels.DATA_OGUI_UAV_COMM.name()).getValue())) {
				return false;
			}
			if(UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_PAUSED_UAV);
			return true;
		}
	}); // in comments
	add(NO_PATH);
}
 public void initializeYES_PATH(ComChannelList inputs, ComChannelList outputs, State PAUSED, State YES_PATH, State COMPLETE) {
	// (YES_PATH,[],[],0,UAV_PATH_DUR-p,1.0)X(COMPLETE,[D=UAVFP_COMPLETE_OGUI,D=UAVFP_COMPLETE_UAV],[])
	YES_PATH.add(new Transition(_internal_vars, inputs, outputs, COMPLETE, Duration.UAV_PATH_DUR.getRange(), 0, 1.0, true) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_COMPLETE_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_COMPLETE_UAV);
			if((Integer)_internal_vars.getVariable("DURATION") <= 0 )
				_internal_vars.setVariable("DURATION", Simulator.getSim().getDuration(this.getDurationRange()));
			return true;
		}
	}); // in comments
	// (YES_PATH,[V!=UAV_FLYING_OP],[],2,NEXT,1.0)X(PAUSED,[D=UAVFP_PAUSED_OGUI,D=UAVFP_PAUSED_UAV],[PAUSE_TIME=-1])
	YES_PATH.add(new Transition(_internal_vars, inputs, outputs, PAUSED, Duration.NEXT.getRange(), 2, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_PAUSED_UAV);
			setTempInternalVar("PAUSE_TIME", -1);
			return true;
		}
	}); // in comments
	add(YES_PATH);
}
 public void initializePAUSED(ComChannelList inputs, ComChannelList outputs, State PAUSED, State RESUME_AFTER_LAUNCH) {
	// (PAUSED,[D=OP_TAKE_OFF_UAV],[],1,NEXT,1.0)X(RESUME_AFTER_LAUNCH,[D=UAVFP_PAUSED_OGUI,D=UAVFP_PAUSED_UAV],[])
	PAUSED.add(new Transition(_internal_vars, inputs, outputs, RESUME_AFTER_LAUNCH, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!Operator.DATA_OP_UAV_COMM.OP_TAKE_OFF_UAV.equals(_inputs.get(Channels.DATA_OP_UAV_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_PAUSED_UAV);
			return true;
		}
	}); // in comments
	add(PAUSED);
}
 public void initializeCOMPLETE(ComChannelList inputs, ComChannelList outputs, State YES_PATH, State COMPLETE, State NO_PATH) {
	// (COMPLETE,[],[],0,NEXT,1.0)X(NO_PATH,[D=UAVFP_NO_PATH_OGUI,D=UAVFP_NO_PATH_UAV],[])
	COMPLETE.add(new Transition(_internal_vars, inputs, outputs, NO_PATH, Duration.NEXT.getRange(), 0, 1.0) {
		@Override
		public boolean isEnabled() { 
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_NO_PATH_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_NO_PATH_UAV);
			return true;
		}
	}); // in comments
	// (COMPLETE,[D=OGUI_NEW_FP_UAV],[],1,NEXT,1.0)X(YES_PATH,[D=UAVFP_YES_PATH_OGUI,D=UAVFP_YES_PATH_UAV],[])
	COMPLETE.add(new Transition(_internal_vars, inputs, outputs, YES_PATH, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!OperatorGui.DATA_OGUI_UAV_COMM.OGUI_NEW_FP_UAV.equals(_inputs.get(Channels.DATA_OGUI_UAV_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_YES_PATH_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_YES_PATH_UAV);
			return true;
		}
	}); // in comments
	add(COMPLETE);
}
 public void initializeRESUME_AFTER_LAUNCH(ComChannelList inputs, ComChannelList outputs, State RESUME_AFTER_LAUNCH, State YES_PATH) {
	// (RESUME_AFTER_LAUNCH,[V=UAV_FLYING_OP],[],1,NEXT,1.0)X(YES_PATH,[D=UAVFP_YES_PATH_OGUI,D=UAVFP_YES_PATH_UAV],[])
	RESUME_AFTER_LAUNCH.add(new Transition(_internal_vars, inputs, outputs, YES_PATH, Duration.NEXT.getRange(), 1, 1.0) {
		@Override
		public boolean isEnabled() { 
			if(!UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(_inputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())) {
				return false;
			}
			setTempOutput(Channels.DATA_UAVFP_OGUI_COMM.name(), UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_YES_PATH_OGUI);
			setTempOutput(Channels.DATA_UAVFP_UAV_COMM.name(), UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_YES_PATH_UAV);
			return true;
		}
	}); // in comments
	add(RESUME_AFTER_LAUNCH);
}
@Override
protected void initializeInternalVariables() {
	_internal_vars.addVariable("START_TIME", -1);
	_internal_vars.addVariable("DURATION", -1);
	_internal_vars.addVariable("PAUSE_TIME", 0);
}
}