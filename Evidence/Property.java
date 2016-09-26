package Evidence;

import java.util.ArrayList;


public class Property {

	private ArrayList<String> events = new ArrayList<String>();
	private String query;
	private String patternType;
	private boolean hasPostfix = false;
	private String postfix = "Globally";
	private String postfixIdentifier;

	public final String[] patternIdentifier = {"absent", "eventually exists", "always exists", "precedes", "leads to"};
	public final String[] patterns = {"Absence", "Existence", "Universality", "Precedence", "Response"};

	public final String[] postfixIdentifiers = {"before", "until", "between", "after"};
	public final String[] postfixes = {"Before", "AfterUntil", "Between", "After"};

	/**
	 * Creates a new query object.
	 * 
	 * @param queryIn The query from the DSL as a string. For example, "LookingForNewSite = true is absent"
	 */
	public Property(String queryIn)
	{
		this.query = queryIn;
		detectEvents();
	}

	/**
	 * Helper function that detects the type of pattern the query is by looking for keywords.
	 */
	private void detectPattern()
	{
		for(int i = 0; i < patternIdentifier.length; i++)
		{
			if(query.contains(patternIdentifier[i]))
			{
				patternType = patterns[i];
				return;
			}
		}
	}

	/**
	 * Helper function that detects if the pattern has a postfix by looking for keywords.
	 */
	private void detectPostfix()
	{
		for(int i = 0; i < postfixIdentifiers.length; i++)
		{
			if(query.contains(postfixIdentifiers[i]))
			{
				postfix = postfixes[i];
				postfixIdentifier = postfixIdentifiers[i];
				hasPostfix = true;
				break;
			}
		}
	}

	/**
	 * Helper function that detects the events in the query by searching before and after the pattern keywords
	 * Calls detectPattern and detectPostfix because they are preconditions.
	 */
	private void detectEvents()
	{
		detectPattern();
		detectPostfix();
		switch(patternType)
		{
		case "Absence":
			String[] primaryEvents = {query.substring(0, query.indexOf("is") - 1)};
			addPostfix(primaryEvents);
			break;
		case "Existence":
			String[] primaryEvents1 = {query.substring(0, query.indexOf("eventually") - 1)};
			addPostfix(primaryEvents1);
			break;
		case "Universality":
			String[] primaryEvents2 = {query.substring(0, query.indexOf("always") - 1)};
			addPostfix(primaryEvents2);
			break;
		case "Precedence":
			String firstEvent = query.substring(0, query.indexOf("precedes") - 1);
			String secondEvent;
			if(hasPostfix)
			{
				secondEvent = query.substring(query.indexOf("precedes") + 9, query.indexOf(postfixIdentifier) - 1);
			}
			else
			{
				secondEvent = query.substring(query.indexOf("precedes") + 9);
			}
			String[] primaryEvents3 = {firstEvent, secondEvent};//untested, fear of nulls
			addPostfix(primaryEvents3);
			break;
		case "Response":
			String firstEvent1 = query.substring(0, query.indexOf("leads") - 1);
			String secondEvent1;
			if(hasPostfix)
			{
				secondEvent1 = query.substring(query.indexOf("to") + 3, query.indexOf(postfixIdentifier) - 1);
			}
			else
			{
				secondEvent1 = query.substring(query.indexOf("to") + 3);
			}
			String[] primaryEvents4 = {firstEvent1, secondEvent1};//untested, fear of nulls
			addPostfix(primaryEvents4);
			break;
		default:
			break;

		}
	}

	private void addPostfix(String[] primaryEvents)
	{
		switch(postfix)
		{
		case "Before":
			addPrimaryEvents(primaryEvents);
			events.add(query.substring(query.indexOf("before")));
			break;
		case "AfterUntil":
			addPrimaryEvents(primaryEvents);
			events.add(query.substring(query.indexOf("after") + 6, query.indexOf("until") - 1));
			events.add(query.substring(query.indexOf("until") + 6));
			break;
		case "Between":
			addPrimaryEvents(primaryEvents);
			events.add(query.substring(query.indexOf("between") + 8, query.indexOf("and") - 1));
			events.add(query.substring(query.indexOf("and") + 4));
			break;
		case "After":
			addPrimaryEvents(primaryEvents);
			events.add(query.substring(query.indexOf("after") + 6));
			break;
		default:
			addPrimaryEvents(primaryEvents);
			break;
		}
	}

	private void addPrimaryEvents(String[] eventsIn)
	{
		for(int i = 0; i < eventsIn.length; i++)
		{
			events.add(eventsIn[i]);
		}
	}

	public boolean hasPostfix() {
		return hasPostfix;
	}

	public void setHasPostfix(boolean hasPostfix) {
		this.hasPostfix = hasPostfix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * Gets the pattern type
	 * 
	 * @return patternType The name of the specification pattern used in the query
	 */
	public String getPatternType() {
		return patternType;
	}

	/**
	 * Sets the patterns type
	 * 
	 * @param patternType The name of the new specification pattern to be used in the query
	 */
	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	/**
	 * Gets the list of events
	 * 
	 * @return patternType The name of the specification pattern used in the query
	 */
	public ArrayList<String> getEvents() {
		return events;
	}

	/**
	 * Sets the list of events
	 * 
	 * @param e the new list of events
	 */
	public void setEvents(ArrayList<String> e) {
		this.events = e;
	}
}
