package sma;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Agent {

	private String name;
	private Inbox inbox;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Inbox getInbox() {
		return inbox;
	}

	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}
	
	public CopyOnWriteArrayList<Message> getMessages() {
		return this.inbox.getMessages(this);
	}
	
	public Agent(String name, Inbox inbox) {
		this.name = name;
		this.inbox = inbox;
		this.inbox.addAgent(this);
	}
}
