package simulator.copy;

import java.util.HashMap;
import java.util.Map.Entry;

public class ActorVariableWrapper {

	HashMap<String, Object> _variables;
	private int _workload;
	
	ActorVariableWrapper()
	{
		_variables = new HashMap<String, Object>();
		addVariable("currentState", new State("start"));
		addVariable("name", "default actor");
	}
	
	public void addVariable(String name, Object o)
	{
		assert !_variables.containsKey(name):"Variable already exists";
		_variables.put(name, o);
	}
	
	public boolean canSetVariable(String name, Object o)
	{
		if(!_variables.containsKey(name))
			return false;
		if(_variables.get(name) != null && _variables.get(name).getClass() != o.getClass() )
			return false;
		return true;
	}
	
	public void setVariable(String name, Object o)
	{
		if(!canSetVariable(name, o))
			assert false:"variable '"+ name + "' doesn't exist in " + _variables.get("name") + ", or is of incompatible value type";
		_variables.put(name, o);
	}
	
	public boolean canGetVariable(String name)
	{
		if(!_variables.containsKey(name))
			return false;
		return true;
	}
	
	public Object getVariable(String name)
	{
		if(!canGetVariable(name))
			assert false:"variable '"+ name + "' doesn't exist in " + _variables.get("name");
		return _variables.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAllVariables()
	{
		return (HashMap<String, Object>) _variables.clone();
	}
	
	public int getWorkload(){
		_workload = 0;
//		int max = _variables.size();
		for(Entry<String, Object> i : _variables.entrySet()){
			if(i.getValue() != null){
				if(i.getValue() instanceof Boolean && !(Boolean) i.getValue()){
					continue;
				} else if(i.getValue() instanceof Integer && ((Integer)i.getValue()) == 0){
					continue;
				}
				_workload++;
			}
		}
		return _workload;
	}
}
