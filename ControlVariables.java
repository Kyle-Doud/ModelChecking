
public class ControlVariables {
	String controlName;
	Factor.VariableTypes controlType;
	Values controlValues;
	
	public ControlVariables(String name, Factor.VariableTypes vt, Values v){
	this.controlName = name;
	this.controlType = vt;
	this.controlValues = v;
	
	}


	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public Factor.VariableTypes getControlType() {
		return controlType;
	}

	public void setControlType(Factor.VariableTypes controlType) {
		this.controlType = controlType;
	}

	public Values getControlValues() {
		return controlValues;
	}

	public void setControlValues(Values controlValues) {
		this.controlValues = controlValues;
	}
}