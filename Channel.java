
public class Channel {
	String cName;
	int channelValue;
	MessageType messageRef;
	
	public Channel(String name, int val, MessageType m){
		this.cName = name;
		this.channelValue = val;
		this.messageRef =m;	
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getChannelValue() {
		return channelValue;
	}

	public void setChannelValue(int channelValue) {
		this.channelValue = channelValue;
	}

	public MessageType getMessageRef() {
		return messageRef;
	}

	public void setMessageRef(MessageType messageRef) {
		this.messageRef = messageRef;
	}

}
