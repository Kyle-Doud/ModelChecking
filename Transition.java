
public class Transition {
	
	String transitionName;
	State s;
	public State getS() {
		return s;
	}
	public void setS(State s) {
		this.s = s;
	}
	public Transition(String name){
		this.transitionName = name;
		}
	public String getTransitionName() {
		return transitionName;
	}
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	

}
