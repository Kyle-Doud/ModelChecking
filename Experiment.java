
public class Experiment {
	String gName, gType, description;
	Objective o;
	
	public Experiment(String name, String type)
	{
		gName = name;
		gType = type;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getgType() {
		return gType;
	}

	public void setgType(String gType) {
		this.gType = gType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Objective getO() {
		return o;
	}

	public void setO(Objective o) {
		this.o = o;
	}

}
