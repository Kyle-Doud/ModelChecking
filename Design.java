
public class Design {
	String designName;
	public enum Types {FULLFACTORIAL, FRACTIONALFACTORIAL, OTHERS};
	Types designType;
	public Types getDesignType() {
		return designType;
	}

	public void setDesignType(Types designType) {
		this.designType = designType;
	}

	Variables variables;
	
	public Design(String name, Types d){
		designName = name;
		designType = d;
		
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public Variables getVariables() {
		return variables;
	}

	public void setVariables(Variables variables) {
		this.variables = variables;
	}
}
