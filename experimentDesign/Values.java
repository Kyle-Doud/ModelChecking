package experimentDesign;

public class Values {
	
	//public enum Types {Function, FactoLevel, integer, character, DoubleType, BooleanType}
	String valueType;
	
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	String function;
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
		setValueType("String");
	}

	public String[] getFactorLevel() {
		return factorLevel;
	}

	public void setFactorLevel(String[] factorLevel) {
		this.factorLevel = factorLevel;
		setValueType("Array");
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
		setValueType("Integer");
	}

	public Double getF() {
		return f;
	}

	public void setF(Double f) {
		this.f = f;
		setValueType("Double");
	}

	public char getS() {
		return s;
	}

	public void setS(char s) {
		this.s = s;
		setValueType("Character");
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
		setValueType("Boolean");
	}

	String factorLevel[];
	int i;
	double f;
	char s;
	boolean b;
	
	public Values(String f){
		this.function = f;
		setValueType("String");
	}
	
	public Values(String factorlevel[]){
		this.factorLevel = factorlevel;
		setValueType("Array");
	}
	
	public Values(int i){
		this.i = i;
		setValueType("Integer");
	}
	
	public Values(double f){
		this.f = f;
		setValueType("Double");
	}
	
	public Values(boolean b){
		this.b = b;
		setValueType("Boolean");
	}
	
	public Values(char s){
		this.s = s;
		setValueType("Character");
	}

}
