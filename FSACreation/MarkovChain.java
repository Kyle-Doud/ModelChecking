package FSACreation;
import java.util.*;

import DataRecording.DataType;
import DataRecording.DataTypeBoolean;
import DataRecording.DataTypeDouble;
import DataRecording.DataTypeInt;
import DataRecording.DataTypeLong;
import DataRecording.DataTypeString;

public class MarkovChain {
	
	/* Number of methods that are of interest in the model, and are recorded.
	 * Number of variables are used as conditions for the States in this FSA.
	 */
	private int numVariables;
	private ArrayList<State> states;
	private String[] varNames;
	private Condition[] conditions;
	
	/** 
	 * Constructor for a new, empty FSA.
	 * @param numVariables The number of variables recorded from the model
	 * 		and also the number of variables considered when evaluating states
	 * @param varNames the names of the variables in the evidence
	 */
	public MarkovChain(int numVariables, String[] varNames, Condition[] conditionsIn) 
	{
		this.numVariables = numVariables;
		this.varNames = varNames;
		this.conditions = conditionsIn;
		states = new ArrayList<State>();
	}
	
	/**
	 * Parse a matrix of data obtained from the execution of the model, and add
	 * to the FSA, any state or transition represented by this data sequence
	 * that is not already present in the FSA.
	 * @param allData The matrix obtained from parsing the data file.
	 */
	public void developFSAFromData(ArrayList<DataType[]> allData) {
		// Obtain the first state, which should be all uninitialized values
		DataType[] firstDataSet = allData.get(0);
		int firstStateIndex = checkStateList(firstDataSet);
		State currentState = states.get(firstStateIndex);

		// Analyze the rest of the data, and add new states to the FSA if needed
		for (int numAnalyzed = 1; numAnalyzed < allData.size(); numAnalyzed++) 
		{
		 	DataType[] nextDataSet = allData.get(numAnalyzed);
		 	Transition t;
			if (isSameState(nextDataSet, currentState))//self loop
			{
				t = new Transition(currentState, currentState);// add self loop
				currentState.addTransition(t);//increments occurances if present
				currentState.calculateTransitionWeights();// balance transition probabilities
			} 
			else 
			{
				int nextStateIndex = checkStateList(nextDataSet);
				State nextState = states.get(nextStateIndex);//next index created by checkStateList..
				t = new Transition(nextState, currentState);
				currentState.addTransition(t);//increments occurances if present
				currentState.calculateTransitionWeights();
				currentState = nextState;
			}
		}
	}
	
	/**
	 * Determines whether a set of values fit the definition of this state,
	 * taking into consideration dsl-defined conditions.
	 * @param dataValues Values of variables at a specific point of time
	 * @param stateIn the state we are comparing to
	 * @return true if these values satisfy every condition of this state
	 */
	private boolean isSameState(DataType[] dataValues, State stateIn) 
	{
		boolean returnValue = true;
		for(int i = 0; i < dataValues.length; i++)//check if each of the values is literally the same
		{
			if(dataValues[i].compareTo(stateIn.getValues()[i]) != 0)
			{
				returnValue = false;
			}
		}
		ArrayList<Integer> matchedConditionsOld = classifyStateByConditions(dataValues);
		ArrayList<Integer> matchedConditionsNew = classifyStateByConditions(stateIn);
		if(matchedConditionsNew.size() != matchedConditionsOld.size())//make sure they're the same size before making the next check
		{
			return false;
		}
		for(int j = 0; j < matchedConditionsNew.size(); j++)//check if they actually meet the same conditions
		{
			if(!(matchedConditionsNew.get(j) == matchedConditionsOld.get(j)))
			{
				returnValue =  false;
			}
		}
		return returnValue;
	}
	
	/**
	 * Searches the existing states in the FSA for one that is satisfied by the
	 * specified values. If no matching state is found, a new state is created
	 * and added to the FSA.
	 * @param dataValues the target values of each variable in this state
	 * @return the index in the states array of the state these values satisfy
	 */
	private int checkStateList(DataType[] dataValues) 
	{
		for (int i = 0; i < states.size(); i++) 
		{
			if (isSameState(dataValues, states.get(i)))
				return i;
		}
		int newIndex = states.size();
		states.add(new State(newIndex, varNames, dataValues));
		return newIndex;
	}
	
	public ArrayList<Integer> classifyStateByConditions(DataType[] dataValues)
	{
		ArrayList<Integer> matchedConditions = new ArrayList<Integer>();
		for (int i = 0; i < this.conditions.length; i++)//check if the data values match any conditions
		{
			Condition stateCondition = this.conditions[i];
			if (stateCondition.isConditionSatisfiedBy(dataValues, varNames))
			{
				matchedConditions.add(i);
			}
		}
		return matchedConditions;
	}
	
	public ArrayList<Integer> classifyStateByConditions(State s)
	{
		DataType[] dataValues = s.getValues();
		ArrayList<Integer> matchedConditions = new ArrayList<Integer>();
		for (int i = 0; i < this.conditions.length; i++)//check if the data values match any conditions
		{
			Condition stateCondition = this.conditions[i];
			if (stateCondition.isConditionSatisfiedBy(dataValues, varNames))
			{
				matchedConditions.add(i);
			}
		}
		return matchedConditions;
	}
	
	
	/**
	 * Create a String representation of this FSA.
	 */
	@Override
	public String toString() 
	{
		String output = "Number of states in the FSA: " + states.size() + "\n";
		for (int i = 0; i < states.size(); i++) 
		{
			State s = states.get(i);
			output += "\nState " + s.getIndex() + ":";
			output += s.toString();
		}
		return output;
	}
	
	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

	public ArrayList<State> getStates() 
	{
		return states;
	}
	
	/** @return the number of states that have been defined for this FSA */
	public int getNumStates() 
	{
		return this.states.size();
	}
	
	public String[] getVarNames() 
	{
		return varNames;
	}

	public void setVarNames(String[] varNames) 
	{
		this.varNames = varNames;
	}


	/**
	 * This class is used to define states 'manually' from information provided
	 * by the DSL. 
	 */
	public class StateDefinitionBuilder {
		
		HashMap<String, Integer> variableNameIndexMap;
		
		
		DataType[] dataValues;
		
		
		/**
		 * Constructor for a new StateDefinitionManager. This object will allow
		 * you to define a new state for the FSA manually (as opposed to
		 * defining new states from the data recorded). 
		 * @param varNameIndices TODO: comment
		 */
		public StateDefinitionBuilder(HashMap<String, Integer> varNameIndices) {
			variableNameIndexMap = varNameIndices;
			dataValues = new DataType[numVariables * 2];
		}

		
		/**
		 * Creates a new state from the information that has been provided to
		 * this StateDefinitionManager object. 
		 * @return index of the new state that has been created. Returns -1 if 
		 *    there is one or more variables that do not have a condition defined
		 */
		public int createState() {
			// verify all info has been added
			for (int i = 0; i < dataValues.length; i++) {
				if (dataValues[i] == null) {
					// Cannot define a state without defining state values for all variables.
					return -1;
				}
			}
			int newIndex = states.size();
			states.add(new State(newIndex, varNames, dataValues));
			return newIndex;
		}
		
		
		/**
		 * Define a new condition for this state. Will override any previous
		 * value(s) assigned for this variable during this state definition.
		 * @param variable Name of the variable to define a condition for
		 * @param valueLow The low end of a range value, or a single value
		 * @param valueHigh The high end of a range value, or a single value
		 */
		private boolean defineStateCondition(String variable,
				DataType valueLow, DataType valueHigh) {
			int index = variableNameIndexMap.get(variable);
			int lowIndex = index * 2;
			int highIndex = index * 2 + 1;
			dataValues[lowIndex] = valueLow;
			dataValues[highIndex] = valueHigh;
			return true;
		}
		
		
		public void defineStateCondition(String variable, boolean value) {
			defineStateCondition(variable, new DataTypeBoolean(value), new DataTypeBoolean(value));
		}
		
		public void defineStateCondition(String variable, boolean valueLow, boolean valueHigh) {
			if (valueLow != valueHigh)
				throw new IllegalArgumentException("Invalid condition values. "
					+ "Cannot define a range for boolean values");
				
			defineStateCondition(variable, new DataTypeBoolean(valueLow), new DataTypeBoolean(valueHigh));
		}
		
		public void defineStateCondition(String variable, double value) {
			defineStateCondition(variable, new DataTypeDouble(value), new DataTypeDouble(value));
		}
		
		public void defineStateCondition(String variable, double valueLow, double valueHigh) {
			if (valueLow > valueHigh)
				throw new IllegalArgumentException("Invalid condition values. "
						+ valueLow + " should not be greater than " + valueHigh);
			defineStateCondition(variable, new DataTypeDouble(valueLow), new DataTypeDouble(valueHigh));
		}
		
		public void defineStateCondition(String variable, int value) {
			defineStateCondition(variable, new DataTypeInt(value), new DataTypeInt(value));
		}
		
		public void defineStateCondition(String variable, int valueLow, int valueHigh) {
			if (valueLow > valueHigh)
				throw new IllegalArgumentException("Invalid condition values. "
						+ valueLow + " should not be greater than " + valueHigh);
			defineStateCondition(variable, new DataTypeInt(valueLow), new DataTypeInt(valueHigh));
		}
		
		public void defineStateCondition(String variable, long value) {
			defineStateCondition(variable, new DataTypeLong(value), new DataTypeLong(value));
		}
		
		public void defineStateCondition(String variable, long valueLow, long valueHigh) {
			if (valueLow > valueHigh)
				throw new IllegalArgumentException("Invalid condition values. "
						+ valueLow + " should not be greater than " + valueHigh);
			defineStateCondition(variable, new DataTypeLong(valueLow), new DataTypeLong(valueHigh));
		}

		public void defineStateCondition(String variable, String value) {
			defineStateCondition(variable, new DataTypeString(value), new DataTypeString(value));
		}
		
		public void defineStateCondition(String variable, String valueLow, String valueHigh) {
			if (valueLow.compareTo(valueHigh) > 0)
				throw new IllegalArgumentException("Invalid condition values. "
						+ valueLow + " should not be greater than " + valueHigh);
			defineStateCondition(variable, new DataTypeString(valueLow), new DataTypeString(valueHigh));
		}
	}
}
