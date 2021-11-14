/**
 * 
 */
package sma;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Inbox {
	private ConcurrentHashMap<Agent, CopyOnWriteArrayList<Message>> messages;
	
	public Inbox() {
		this.messages = new ConcurrentHashMap<Agent, CopyOnWriteArrayList<Message>>();
	}
	
	/**
	 * @return the messages
	 */
	CopyOnWriteArrayList<Message> getMessages(Agent agent) {
		return this.messages.get(agent);
	}
	
	/**
	 * 
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		this.messages.put(agent, new CopyOnWriteArrayList<Message>());
	}
	
	/**
	 * 
	 * @param message
	 */
	public void send(Message message) {
		CopyOnWriteArrayList<Message> recipientMessages = this.messages.get(message.getRecipient());
		
		if(recipientMessages == null) {
			recipientMessages = new CopyOnWriteArrayList<Message>();
		}
		
		recipientMessages.add(message);
		this.messages.put(message.getRecipient(), recipientMessages);
	}
}
