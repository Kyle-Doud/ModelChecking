
public class Response {
	String responseName;
	public enum RTypes {SIMPLE,COMPOSITE};
	RTypes responseType;
	
	public Response(String name, RTypes type){
		this.responseName = name;
		this.responseType = type;
	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public RTypes getResponseType() {
		return responseType;
	}

	public void setResponseType(RTypes responseType) {
		this.responseType = responseType;
	}
}
