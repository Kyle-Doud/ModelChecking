
public class State {
	String stateName;
	boolean StateVal;
	public State(String name, boolean v){
		this.stateName = name;
		this.StateVal = v;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public boolean isStateVal() {
		return StateVal;
	}
	public void setStateVal(boolean stateVal) {
		StateVal = stateVal;
	}

}
