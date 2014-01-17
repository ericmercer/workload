package model.actors.gen;

import simulator.*;

public class UAV extends Actor {

	public enum DATA_UAV_VGUI {
		CRASHED

	}

	public enum DATA_UAV_OGUI_COMM {
		UAV_BATTERY_GOOD_OGUI,
		UAV_BATTERY_LOW_OGUI,
		UAV_FP_PAUSED_OGUI,
		UAV_FP_COMPLETE_OGUI,
		UAV_FP_NO_PATH_OGUI,
		UAV_FP_YES_PATH_OGUI

	}

	public enum VIDEO_UAV_OP_COMM {
		UAV_LANDED_OP,
		UAV_FLYING_OP,
		UAV_CRASHED_OP

	}

	public enum DATA_UAV_COMM {
		UAV_LANDED_UAVHAG, UAVHAG_INACTIVE_UAV, UAVHAG_LOW_UAV, UAVHAG_CRASHED_UAV, UAVHAG_GOOD_UAV
		
	}

	public UAV(ComChannelList inputs, ComChannelList outputs) {
		//initialize name
		setName("UAV");
		
		/* UAV Battery */
	}

	@Override
	protected void initializeInternalVariables() {
		
	}
}
