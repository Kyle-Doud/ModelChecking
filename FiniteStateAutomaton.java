
public class FiniteStateAutomaton {
	
	MessageType mType;
	Channel chan;
	Transition t;
	
	public FiniteStateAutomaton(MessageType m, Channel c, Transition t){
		this.mType =m;
		this.chan = c;
		this.t = t;
	}
	
	public MessageType getmType() {
		return mType;
	}
	public void setmType(MessageType mType) {
		this.mType = mType;
	}
	public Channel getChan() {
		return chan;
	}
	public void setChan(Channel chan) {
		this.chan = chan;
	}
	public Transition getT() {
		return t;
	}
	public void setT(Transition t) {
		this.t = t;
	}
	

}
