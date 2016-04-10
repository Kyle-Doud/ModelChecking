package FSACreation;

public class Transition {
	State fromState;
	State toState;
	double weight = 0;
	int occurances = 0;
	
	public Transition(State toStateIn, State fromStateIn)
	{
		this.toState = toStateIn;
		this.fromState = fromStateIn;
		this.occurances = 1;
	}
	
	public void addOccurance()
	{
		occurances++;
	}
	
	public int getOccurances() {
		return occurances;
	}

	public void setOccurances(int occurances) {
		this.occurances = occurances;
	}

	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public State getFromState() {
		return fromState;
	}
	public void setFromState(State fromState) {
		this.fromState = fromState;
	}
	public State getToState() {
		return toState;
	}
	public void setToState(State toState) {
		this.toState = toState;
	}
	
    @Override
    public boolean equals(Object o){
        if(o instanceof Transition){
            Transition toCompare = (Transition) o;
            boolean to = this.toState.equals(toCompare.getToState());
            boolean from = this.fromState.equals(toCompare.getFromState());
            boolean weigh = this.weight == toCompare.getWeight();
            return to && from && weigh;
        }
        return false;
    }

}
