package FSACreation;
import java.util.ArrayList;

import DataRecording.DataType;
import DataRecording.DataTypeString;

public class State {

	private int index;
	private DataType[] values;
	private String[] identifiers;
	private ArrayList<Transition> transitions;

	/**
	 * Create a new State object
	 * @param newIndex Index this state is stored at in the FSA's states array
	 * @param newDataValues Values representing the condition values
	 * 		for each variable in this state.
	 */
	public State(int newIndex, String[] newDataValueIdentifiers, DataType[] newDataValues) 
	{ 
		this.index = newIndex;
		this.values = newDataValues;
		this.identifiers = newDataValueIdentifiers;
		transitions = new ArrayList<Transition>();
	}

	/**
	 * Adds a transition between this state and the state found at the provided
	 * index in the FSA's states array, if this transition does not already
	 * exist in the current FSA.
	 * @param nextStateIndex The index of the next state
	 */
	public void addTransition(Transition t) 
	{
		if (!this.transitions.contains(t))
		{
			this.transitions.add(t);
		}
		else
		{
			this.transitions.get(this.transitions.indexOf(t)).addOccurance();
		}
	}

	/**
	 * @return true if there is a transition between this state and the state
	 * at the provided index
	 */
	public boolean isTransitionPresent(int nextStateIndex) {
		return this.transitions.contains(nextStateIndex);
	}
	
	public void calculateTransitionWeights() 
	{
		int totalOccurances = 0;
		for(Transition t : transitions)
		{
			totalOccurances += t.getOccurances();
		}
		for(Transition t : transitions)
		{
			t.setWeight((double) t.getOccurances() / (double) totalOccurances);
		}
	}

	/** Generate a String representation of this state */
	@Override
	public String toString() 
	{
		String output = "Values:\n";
		for(int i = 0; i < identifiers.length; i++)
		{
			output += identifiers[i] + " = " + values[i] + "\n";
		}
		output += "\n\t\tTransitions: ";
		for (int i = 0; i < transitions.size(); i++) 
		{
			output += transitions.get(i).getToState().getIndex();
			if (i != transitions.size() - 1)
				output += ", ";
		}
		return output;
	}

	/**
	 * @return the index this state is stored at in the array of states for
	 * the current FSA
	 */
	public int getIndex() 
	{
		return index;
	}

	public String[] getIdentifiers() {
		return identifiers;
	}

	public DataType[] getValues() {
		return values;
	}
	
	public DataType getValue(String identifier)
	{
		for(int i = 0; i < identifiers.length; i++)
		{
			if(identifier.compareTo(identifiers[i]) == 0)
			{
				return values[i];
			}
		}
		return new DataTypeString("error");
	}

	public void setValues(DataType[] values) {
		this.values = values;
	}

	public ArrayList<Transition> getTransitions() 
	{
		return transitions;
	}
	
	/**
	 * Checks if state indices match
	 * 
	 */
    @Override
    public boolean equals(Object o){
        if(o instanceof State){
            State toCompare = (State) o;
            boolean ind = this.index == toCompare.getIndex();
            return ind;
        }
        return false;
    }

}
