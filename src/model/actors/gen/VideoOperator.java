package model.actors.gen;

import java.util.HashMap;

import simulator.*;

public class VideoOperator extends Actor {

	public enum AUDIO_VO_MM_COMM {
		VO_POKE_MM,
		VO_ACK_MM,
		VO_END_MM,
		VO_TARGET_SIGHTED_F_MM,
		VO_TARGET_SIGHTED_T_MM
	}

	public enum VISUAL_VO_VGUI_COMM {
		VO_POKE_VGUI,
		VO_ACK_VGUI,
		VO_END_VGUI,
		VO_FLYBY_END_FAILED_VGUI,
		VO_FLYBY_END_SUCCESS_VGUI,
		VO_FLYBY_REQ_F_VGUI,
		VO_FLYBY_REQ_T_VGUI,
		VO_POSSIBLE_ANOMALY_DETECTED_F_VGUI,
		VO_POSSIBLE_ANOMALY_DETECTED_T_VGUI
	}

	public enum AUDIO_VO_OP_COMM {
		VO_POKE_OP,
		VO_ACK_OP,
		VO_END_OP,
		VO_BAD_STREAM_OP
	}

	@Override
	protected void initializeInternalVariables() {
		// TODO Auto-generated method stub
		
	}

	
}