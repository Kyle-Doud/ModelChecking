
public class PerformanceMeasure {

	String name, description;
	Response expectedResults;
	Values expectedValue;
	
	public PerformanceMeasure(String name, Response r, Values v){
		this.expectedResults = r;
		this.expectedValue = v;
		this.name =name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Response getExpectedResults() {
		return expectedResults;
	}

	public void setExpectedResults(Response expectedResults) {
		this.expectedResults = expectedResults;
	}

	public Values getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(Values expectedValue) {
		this.expectedValue = expectedValue;
	}
}
