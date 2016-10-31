package FSACreation;

public class Event {
	
	String name, path;

	public Event(String nameIn, String pathIn){
		this.name = nameIn;
		this.path = pathIn;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
