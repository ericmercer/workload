package model.team;

import java.util.*;
import simulator.*;

/**
 * 
 * @author rob.ivie
 *
 * list all of the actors in this method
 * we may be able to use another class instead of this method
 */
public class WiSARTeam extends Team {
	
	/**
	 * initialize all of the actors that will be used during the simulation
	 * assigns the actors the inputs and outputs they will be using (can this be moved inside the actor? -rob)
	 * @param outputs
	 */
	public WiSARTeam() {
		//initialize the list of communication channels
		_com_channels = new ComChannelList();
		
		//add EVENT channels
//		_com_channels.add( new ComChannel<Boolean>(Channels.EVENT.name(), false, ComChannel.Type.AUDIO) );
		
		//add Actor channels
//		_com_channels.add( new ComChannel<SOURCE_ACTOR.CHANNEL>(Channels.CHANNEL.name(), ComChannel.Type.AUDIO, "SOURCE_ACTOR", "TARGET_ACTOR") );
		
		//initialize inputs and outputs (temporary lists)
		ComChannelList inputs = new ComChannelList();
		ComChannelList outputs = new ComChannelList();
		
		//add new search event
//		inputs.clear();
//		inputs.add(_com_channels.get(Channels.INPUT_CHANNEL.name()));
//		outputs.clear();
//		outputs.add(_com_channels.get(Channels.OUTPUT_CHANNEL.name()));
//		this.addEvent(new EVENT(inputs, outputs), 1);
		
		//add the parent search
//		inputs.clear();
//		inputs.add(_com_channels.get(Channels.INPUT_CHANNEL.name()));
//		outputs.clear();
//		outputs.add(_com_channels.get(Channels.OUTPUT_CHANNEL.name()));
//		this.addActor(new ACTOR(inputs, outputs));
	}

}
