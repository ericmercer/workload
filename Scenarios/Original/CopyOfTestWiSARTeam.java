package model.team;

import model.actors.gen.*;
import model.events.*;
import simulator.*;

public class CopyOfTestWiSARTeam extends Team {

	public CopyOfTestWiSARTeam() {

		_com_channels = new ComChannelList();
		
		//add EVENT channels
		_com_channels.add( new ComChannel<Boolean>(Channels.NEW_SEARCH_EVENT.name(), false, ComChannel.Type.AUDIO) );
		_com_channels.add( new ComChannel<Boolean>(Channels.TERMINATE_SEARCH_EVENT.name(), false, ComChannel.Type.AUDIO) );
		_com_channels.add(new ComChannel<Boolean>(Channels.NEW_SEARCH_AREA_EVENT.name(),false,ComChannel.Type.AUDIO));
		_com_channels.add(new ComChannel<Boolean>(Channels.TARGET_DESCRIPTION_EVENT.name(),false,ComChannel.Type.AUDIO));
		
		_com_channels.add( new ComChannel<ParentSearch.DATA_PS_PS_COMM>(Channels.DATA_PS_PS_COMM.name(), ComChannel.Type.DATA, "PS", "PS") );
		_com_channels.add( new ComChannel<ParentSearch.AUDIO_PS_MM_COMM>(Channels.AUDIO_PS_MM_COMM.name(), ComChannel.Type.AUDIO, "MM", "PS") );
		_com_channels.add( new ComChannel<UAVBattery.DATA_UAVBAT_UAV_COMM>(Channels.DATA_UAVBAT_UAV_COMM.name(), ComChannel.Type.DATA, "UAV", "UAVBAT") );
		_com_channels.add( new ComChannel<VideoOperator.AUDIO_VO_OP_COMM>(Channels.AUDIO_VO_OP_COMM.name(), ComChannel.Type.AUDIO, "OP", "VO") );
		_com_channels.add( new ComChannel<VideoOperator.AUDIO_VO_GUI_COMM>(Channels.AUDIO_VO_GUI_COMM.name(), ComChannel.Type.AUDIO, "GUI", "VO") );
		_com_channels.add( new ComChannel<VideoOperator.DATA_VO_GUI_COMM>(Channels.DATA_VO_GUI_COMM.name(), ComChannel.Type.DATA, "GUI", "VO") );
		_com_channels.add( new ComChannel<VideoOperator.AUDIO_VO_MM_COMM>(Channels.AUDIO_VO_MM_COMM.name(), ComChannel.Type.AUDIO, "MM", "VO") );
		_com_channels.add( new ComChannel<MissionManager.DATA_MM_MM_COMM>(Channels.DATA_MM_MM_COMM.name(), ComChannel.Type.DATA, "MM", "MM") );
		_com_channels.add( new ComChannel<MissionManager.AUDIO_MM_PS_COMM>(Channels.AUDIO_MM_PS_COMM.name(), ComChannel.Type.AUDIO, "PS", "MM") );
		_com_channels.add( new ComChannel<MissionManager.DATA_MM_VGUI_COMM>(Channels.DATA_MM_VGUI_COMM.name(), ComChannel.Type.DATA, "VGUI", "MM") );
		_com_channels.add( new ComChannel<MissionManager.AUDIO_MM_VO_COMM>(Channels.AUDIO_MM_VO_COMM.name(), ComChannel.Type.AUDIO, "VO", "MM") );
		_com_channels.add( new ComChannel<MissionManager.AUDIO_MM_OP_COMM>(Channels.AUDIO_MM_OP_COMM.name(), ComChannel.Type.AUDIO, "OP", "MM") );
		_com_channels.add( new ComChannel<UAV.DATA_UAV_OGUI_COMM>(Channels.DATA_UAV_OGUI_COMM.name(), ComChannel.Type.DATA, "OGUI", "UAV") );
		_com_channels.add( new ComChannel<UAV.VISUAL_UAV_OP_COMM>(Channels.VISUAL_UAV_OP_COMM.name(), ComChannel.Type.VISUAL, "OP", "UAV") );
		_com_channels.add( new ComChannel<UAV.DATA_UAV_UAVVF_COMM>(Channels.DATA_UAV_UAVVF_COMM.name(), ComChannel.Type.DATA, "UAVVF", "UAV") );
		_com_channels.add( new ComChannel<UAV.DATA_UAV_UAVHAG_COMM>(Channels.DATA_UAV_UAVHAG_COMM.name(), ComChannel.Type.DATA, "UAVHAG", "UAV") );
		_com_channels.add( new ComChannel<UAVFlightPlan.DATA_UAVFP_UAV_COMM>(Channels.DATA_UAVFP_UAV_COMM.name(), ComChannel.Type.DATA, "UAV", "UAVFP") );
		_com_channels.add( new ComChannel<UAVFlightPlan.DATA_UAVFP_OGUI_COMM>(Channels.DATA_UAVFP_OGUI_COMM.name(), ComChannel.Type.DATA, "OGUI", "UAVFP") );
		_com_channels.add( new ComChannel<VideoOperatorGui.VISUAL_VGUI_VO_COMM>(Channels.VISUAL_VGUI_VO_COMM.name(), ComChannel.Type.VISUAL, "VO", "VGUI") );
		_com_channels.add( new ComChannel<Operator.AUDIO_OP_MM_COMM>(Channels.AUDIO_OP_MM_COMM.name(), ComChannel.Type.AUDIO, "MM", "OP") );
		_com_channels.add( new ComChannel<Operator.DATA_OP_UAV_COMM>(Channels.DATA_OP_UAV_COMM.name(), ComChannel.Type.DATA, "UAV", "OP") );
		_com_channels.add( new ComChannel<Operator.DATA_OP_OP_COMM>(Channels.DATA_OP_OP_COMM.name(), ComChannel.Type.DATA, "OP", "OP") );
		_com_channels.add( new ComChannel<Operator.DATA_OP_OGUI_COMM>(Channels.DATA_OP_OGUI_COMM.name(), ComChannel.Type.DATA, "OGUI", "OP") );
		_com_channels.add( new ComChannel<UAVHeightAboveGround.DATA_UAVHAG_UAV_COMM>(Channels.DATA_UAVHAG_UAV_COMM.name(), ComChannel.Type.DATA, "UAV", "UAVHAG") );

		ComChannelList inputs = new ComChannelList();
		ComChannelList outputs = new ComChannelList();
		
		//add new search event
		inputs.clear();
		inputs.add(_com_channels.get(Channels.NEW_SEARCH_EVENT.name()));
		outputs.clear();
		outputs.add(_com_channels.get(Channels.NEW_SEARCH_EVENT.name()));
		this.addEvent(new NewSearchEvent(inputs, outputs), 1);

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.NEW_SEARCH_EVENT.name()));
		inputs.add(_com_channels.get(Channels.TERMINATE_SEARCH_EVENT.name()));
		inputs.add(_com_channels.get(Channels.NEW_SEARCH_AREA_EVENT.name()));
		inputs.add(_com_channels.get(Channels.TARGET_DESCRIPTION_EVENT.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_MM_PS_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_PS_PS_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_PS_PS_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_PS_MM_COMM.name()));
		this.addActor(new ParentSearch(inputs, outputs));

		inputs.clear();
		outputs.clear();
		outputs.add(_com_channels.get(Channels.DATA_UAVBAT_UAV_COMM.name()));
		this.addActor(new UAVBattery(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.AUDIO_MM_VO_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_VO_OP_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_VO_GUI_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_VO_GUI_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_VO_MM_COMM.name()));
		inputs.add(_com_channels.get(Channels.VISUAL_VGUI_VO_COMM.name()));
		this.addActor(new VideoOperator(inputs, outputs));

		inputs.clear();
		outputs.clear();
		outputs.add(_com_channels.get(Channels.DATA_MM_MM_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_MM_MM_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_MM_PS_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_MM_VGUI_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_MM_VO_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_MM_OP_COMM.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_OP_MM_COMM.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_PS_MM_COMM.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_VO_MM_COMM.name()));
		this.addActor(new MissionManager(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.DATA_OP_UAV_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_UAV_OGUI_COMM.name()));
		outputs.add(_com_channels.get(Channels.VISUAL_UAV_OP_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_UAV_UAVVF_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_UAV_UAVHAG_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_UAVBAT_UAV_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_UAVFP_UAV_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_UAVHAG_UAV_COMM.name()));
		this.addActor(new UAV(inputs, outputs));

		inputs.clear();
		outputs.clear();
		outputs.add(_com_channels.get(Channels.DATA_UAVFP_UAV_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_UAVFP_OGUI_COMM.name()));
		this.addActor(new UAVFlightPlan(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.DATA_MM_VGUI_COMM.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_VO_GUI_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_VO_GUI_COMM.name()));
		outputs.add(_com_channels.get(Channels.VISUAL_VGUI_VO_COMM.name()));
		this.addActor(new VideoOperatorGui(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.DATA_OP_OGUI_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_UAV_OGUI_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_UAVFP_OGUI_COMM.name()));
		this.addActor(new OperatorGui(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.AUDIO_MM_OP_COMM.name()));
		outputs.add(_com_channels.get(Channels.AUDIO_OP_MM_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_OP_UAV_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_OP_OP_COMM.name()));
		inputs.add(_com_channels.get(Channels.DATA_OP_OP_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_OP_OGUI_COMM.name()));
		inputs.add(_com_channels.get(Channels.VISUAL_UAV_OP_COMM.name()));
		inputs.add(_com_channels.get(Channels.AUDIO_VO_OP_COMM.name()));
		this.addActor(new Operator(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.DATA_UAV_UAVHAG_COMM.name()));
		outputs.add(_com_channels.get(Channels.DATA_UAVHAG_UAV_COMM.name()));
		this.addActor(new UAVHeightAboveGround(inputs, outputs));

		inputs.clear();
		outputs.clear();
		inputs.add(_com_channels.get(Channels.DATA_UAV_UAVVF_COMM.name()));
		this.addActor(new UAVVideoFeed(inputs, outputs));

	}

}
