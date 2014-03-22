package simulator.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Team implements ITeam {
	
	ArrayList<IEvent> _events = new ArrayList<IEvent>();
	ArrayList<IActor> _actors = new ArrayList<IActor>();
	public ComChannelList _com_channels;

	@Override
	public HashMap<IActor, ITransition> getEnabledTransitions() {
		HashMap<IActor, ITransition> enabledTransitions = new HashMap<IActor, ITransition>();
		for( IActor actor : _actors ) {
			HashMap<IActor, ITransition> transitions = actor.getEnabledTransitions();
			if(transitions != null)
				enabledTransitions.putAll(transitions);
		}
		return enabledTransitions;
	}
	
	@Override
	public void updateTransitions() {
		for (IActor actor : _actors ) {
			actor.updateTransitions();
		}
	}
	
	@Override
	public ArrayList<IEvent> getEvents() {
		return _events;
	}
	
	@Override
	public ComChannelList getAllChannels()
	{
		return _com_channels;
	}
	
	protected void addActor(IActor actor) 
	{
		assert !_actors.contains(actor):"Actor is already a part of the team";
		
		_actors.add(actor);
	}
	
	protected void addEvent(IEvent event, int count) 
	{
		assert !_events.contains(event):"Event is already a part of the team";
		event.setEventCount(count);
		_events.add(event);
	}
	
	protected void addComChannel(ComChannel<?> c)
	{
		assert _com_channels.containsKey(c.getName()):"Com channel already defined";
		_com_channels.add(c);
	}

	protected ComChannel<?> getComChannel(String name)
	{
		return _com_channels.get(name);
	}

	public List<IActor> getActors() {
		return _actors;
	}
}
