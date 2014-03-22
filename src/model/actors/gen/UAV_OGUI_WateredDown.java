package model.actors.gen;

import model.team.Channels;
import model.team.Duration;
import simulator.ComChannelList;
import simulator.State;
import simulator.Transition;

public class UAV_OGUI_WateredDown extends simulator.Actor {

	public UAV_OGUI_WateredDown(ComChannelList inputs, ComChannelList outputs) {
		//initialize name
		setName("UAV_OGUI_WateredDown");

		//initialize states
		State IDLE = new State("IDLE");
		State CRASHED = new State("CRASHED");

		//initialize memory
		initializeInternalVariables();

		//DO NOTHING IF CRASHED
		IDLE.add(new Transition(_internal_vars,inputs,outputs,CRASHED,Duration.NEXT.getRange(),10,1.0){
			@Override
			public boolean isEnabled(){
				Object DATA_UAVBAT_UAV_COMM = _inputs.get(Channels.DATA_UAVBAT_UAV_COMM.name()).getValue();
				if(UAVBattery.DATA_UAVBAT_UAV_COMM.UAVBAT_DEAD_UAV.equals(DATA_UAVBAT_UAV_COMM) && !"CRASHED".equals(_internal_vars.getVariable("UAV_STATE"))){
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_CRASHED_OP);
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_CRASHED_VGUI);
					this.setTempInternalVar("UAV_STATE", "CRASHED");
					return true;
					
				}
				if("CRASHED".equals(_internal_vars.getVariable("UAV_STATE"))){
					return true;
				}
				return false;
			}
		});
		
		//report landing
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_OGUI_COMM = _inputs.get(Channels.DATA_OP_OGUI_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_LANDED_VGUI);
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}

				if(UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);

				}
				if(Operator.DATA_OP_OGUI_COMM.OP_LAND_OGUI.equals(DATA_OP_OGUI_COMM)){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_LANDED_OP);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
					this.setTempInternalVar("UAV_STATE", "LANDED");
					return true;
				}
				return false;
			}
		});

		//report a low battery
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 8, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_UAVBAT_UAV_COMM = _inputs.get(Channels.DATA_UAVBAT_UAV_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_LANDED_VGUI);
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}

				if(UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);

				}
				if(UAVBattery.DATA_UAVBAT_UAV_COMM.UAVBAT_LOW_UAV.equals(DATA_UAVBAT_UAV_COMM)
						&& !OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_BATTERY_LOW_OP.equals(this._outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_BATTERY_LOW_OP);
					return true;
				}
				
				return false;
			}
		});
	
		//report path completion
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 7, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_OGUI_COMM = _inputs.get(Channels.DATA_OP_OGUI_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}

				if(UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_COMPLETE_OGUI.equals(DATA_UAVFP_OGUI_COMM)
						&& !OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_PATH_COMPLETE_OP.equals(_outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())
						&& !OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_BATTERY_LOW_OP.equals(_outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_PATH_COMPLETE_OP);
					return true;
				}
				return false;
			}
		});
		
		//report a new search area
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_OGUI_COMM = _inputs.get(Channels.DATA_OP_OGUI_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}

				if(Operator.DATA_OP_OGUI_COMM.OP_NEW_SEARCH_AOI_OGUI.equals((DATA_OP_OGUI_COMM))){
					this.setTempOutput(Channels.DATA_OGUI_UAV_COMM.name(), OperatorGui.DATA_OGUI_UAV_COMM.OGUI_NEW_FP_UAV);
					return true;
				}
				return false;
			}
		});
		
		//if the flight plan is paused, report it
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 2, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();

				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}

				if(UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)
						&& !OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP.equals(_outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					return true;
				}
				return false;
			}
		});

		//if the uav takes off report it
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.UAV_TAKE_OFF.getRange(), 4, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_UAV_COMM = _inputs.get(Channels.DATA_OP_UAV_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}
				
				if(Operator.DATA_OP_UAV_COMM.OP_TAKE_OFF_UAV.equals(DATA_OP_UAV_COMM)){
					this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FLYING_NORMAL_OP);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
					this.setTempInternalVar("UAV_STATE", "FLYING");
					return true;
				}
				return false;
			
			}
		});

		//if the uav landed report it
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 4, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_UAV_COMM = _inputs.get(Channels.DATA_OP_UAV_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}
				
				if("LANDED".equals(UAV_STATE) && !UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP.equals(_outputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
					return true;
				} 
				return false;
			}
		});
		
		//if the uav is flying report it
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 4, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_OP_UAV_COMM = _inputs.get(Channels.DATA_OP_UAV_COMM.name()).getValue();
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					if(UAV.DATA_UAV_OGUI_COMM.UAV_FP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
					}
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
				}else{
					this.setTempOutput(Channels.DATA_UAV_VGUI_COMM.name(), UAV.DATA_UAV_VGUI.UAV_FLYING_VGUI);
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
				}
				
				if("FLYING".equals(UAV_STATE) && !UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP.equals(_outputs.get(Channels.VIDEO_UAV_OP_COMM.name()).getValue())){
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_FLYING_OP);
					return true;
				}
				return false;
			}
		});
	
		//if the uav lands and the flight plan is paused report it
		IDLE.add(new Transition(_internal_vars, inputs, outputs, IDLE, Duration.NEXT.getRange(), 3, 1.0){
			@Override
			public boolean isEnabled(){
				//default outputs
				Object UAV_STATE = _internal_vars.getVariable("UAV_STATE");
				Object DATA_UAVFP_OGUI_COMM = _inputs.get(Channels.DATA_UAVFP_OGUI_COMM.name()).getValue();
				Object DATA_UAVFP_UAV_COMM = _inputs.get(Channels.DATA_UAVFP_UAV_COMM.name()).getValue();
				
				if("LANDED".equals(UAV_STATE)){
					this.setTempOutput(Channels.VIDEO_UAV_OP_COMM.name(), UAV.VIDEO_UAV_OP_COMM.UAV_LANDED_OP);
					if(!OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP.equals(_outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())
							&& UAVFlightPlan.DATA_UAVFP_OGUI_COMM.UAVFP_PAUSED_OGUI.equals(DATA_UAVFP_OGUI_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
						return true;
					}
					if(!OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP.equals(_outputs.get(Channels.VIDEO_OGUI_OP_COMM.name()).getValue())
							&& UAVFlightPlan.DATA_UAVFP_UAV_COMM.UAVFP_PAUSED_UAV.equals(DATA_UAVFP_UAV_COMM)){
						this.setTempOutput(Channels.VIDEO_OGUI_OP_COMM.name(), OperatorGui.VIDEO_OGUI_OP_COMM.OGUI_FP_PAUSED_OP);
						return true;
					}
				}

				return false;
			}
		});
		this.add(IDLE);
		
		startState(IDLE);
	}
	
	@Override
	protected void initializeInternalVariables() {
		_internal_vars.addVariable("UAV_STATE", "LANDED");
		
	}


}
