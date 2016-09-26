package experimentDesign;

public class Factor {
	String factorName;
	public enum VariableTypes {QUALITATIVE, QUANTITATIVE, CONTINOUS, DISCRETE , BINARY ,NONBINARY};
	Values factorValues;
	VariableTypes factorType;
	
	public Factor(){
		
	}
	
	public Factor(String name, VariableTypes vt, Values v){
		this.factorName = name;
		this.factorType = vt;
		this.factorValues =v;
		
	}

	public String getFactorName() {
		return factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	public Values getFactorValues() {
		return factorValues;
	}

	public void setFactorValues(Values factorValues) {
		this.factorValues = factorValues;
	}

	public VariableTypes getFactorType() {
		return factorType;
	}

	public void setFactorType(VariableTypes factorType) {
		this.factorType = factorType;
	}

}
