
public class Objective {
	String name, objectivetype;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectivetype() {
		return objectivetype;
	}
	public void setObjectivetype(String objectivetype) {
		this.objectivetype = objectivetype;
	}
	public Objective(String nameIn, String objectivetypeIn)
	{
		name = nameIn;
		objectivetype = objectivetypeIn;
	}

}
