package FSACreation;

import DataRecording.DataType;

/**
 * This class represents a single condition of a state within an FSA. 
 * 
 */
public class Condition {

	private DataType value;
	private String identifier;
	private String operator;

	/**
	 * Instantiates a Condition object that represents a single value
	 * @param singleConditionValue A single value
	 */
	public Condition(String lhs, String op, DataType rhs) 
	{

		this.identifier = lhs;
		this.value = rhs;
		this.operator = op;
	}

	/**
	 * This condition is satisfied when the values are in accord with 
	 * the conditional restrictions.
	 * @param namesIn Names are needed to match the operation (<, <=, etc)
	 * to the values
	 * @return whether the provided value 'satisfies' this condition
	 */
	boolean isConditionSatisfiedBy(DataType[] valueIn, String[] namesIn) 
	{
		for(int i = 0; i < namesIn.length; i++)
		{
			if(namesIn[i].compareTo(this.identifier) == 0)
			{
				switch(operator)
				{
				case "=":
					if(valueIn[i].compareTo(value) == 0)
						return true;
					break;
				case "!=":
					if(valueIn[i].compareTo(value) != 0)
						return true;
					break;
				case "<":
					if(valueIn[i].compareTo(value) < 0)
						return true;
					break;
				case "<=":
					if(valueIn[i].compareTo(value) <= 0)
						return true;
					break;
				case ">":
					if(valueIn[i].compareTo(value) > 0)
						return true;
					break;
				case ">=":
					if(valueIn[i].compareTo(value) >= 0)
						return true;
					break;
				}
			}
		}
		return false;
	}


	/**
	 * Determines if this condition object is equal to a condition with the 
	 * specified values. This method is used when manually defining states from
	 * the DSL and 
	 * @param val
	 * @return
	 */
	boolean isConditionEqual(DataType val) 
	{
		if (!val.isInitialized())
			return false;

		// Evaluate condition values for equality
		return (value.compareTo(val) == 0);
	}

	public DataType getValue() {
		return value;
	}

	public void setValue(DataType value) {
		this.value = value;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Generates a String representation of this condition.
	 * Has the form: "(singleValue)" or "(lowValue, highValue)"
	 */
	@Override
	public String toString() 
	{
		return identifier + " " + operator + " " + value;
	}
}
