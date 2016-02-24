
public class MessageType {
	String MName;
	String var[];
	
	public MessageType(String name, String var[]){
		this.MName = name;
		this.var = var;
	}

	public String getMName() {
		return MName;
	}

	public void setMName(String mName) {
		MName = mName;
	}

	public String[] getVar() {
		return var;
	}

	public void setVar(String[] var) {
		this.var = var;
	}

}
