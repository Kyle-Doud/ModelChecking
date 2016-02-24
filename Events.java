import java.util.ArrayList;


public class Events {
	
	ArrayList<String> events;
	public Events()
	{
		events = new ArrayList<String>();
	}
	public ArrayList<String> getEvents() {
		return events;
	}
	public void setEvents(ArrayList<String> eventsIn) {
		this.events = eventsIn;
	}
	public void addEvent(String s)
	{
		events.add(s);
	}

}
