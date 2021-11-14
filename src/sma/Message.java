package sma;
public class Message {
	private Agent emitter;
	private Agent recipient;
	private Negotiation negotiation;
	private double price;
	private  MessageType type;

	public Message() {
	}
	
	public Message(Agent emitter, Agent recipient, Negotiation negotiation, double price, MessageType type) {
		this.setNegotiation(negotiation);
		this.setEmitter(emitter);
		this.setRecipient(recipient);
		this.setType(type);
		this.setPrice(price);
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the emitter
	 */
	public Agent getEmitter() {
		return emitter;
	}

	/**
	 * @param emitter the emitter to set
	 */
	public void setEmitter(Agent emitter) {
		this.emitter = emitter;
	}

	/**
	 * @return the recipient
	 */
	public Agent getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(Agent recipient) {
		this.recipient = recipient;
	}
	
	public Negotiation getNegotiation() {
		return negotiation;
	}

	public void setNegotiation(Negotiation negotiation) {
		this.negotiation = negotiation;
	}
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}
