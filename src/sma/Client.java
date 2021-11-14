package sma;
import java.util.Random;

public class Client extends Agent{
	private double maximumBudget;
	private static double HIGH_PROBABILITY = 2;
	private static double AVERAGE_PROBABILITY = 1.5;
	private static double REGULATOR = 15;
	
	public double getMaximumBudget() {
		return maximumBudget;
	}

	public void setMaximumBudget(double maximumBudget) {
		this.maximumBudget = maximumBudget;
	}
	
	public Client(String name, double maximumBudget, Inbox inbox) {
		super(name, inbox);
		this.maximumBudget = maximumBudget;
	}
	
	public void makeOffer(Negotiation negotiation) {
		double price = 0d;
		// First offer, no ticket given
		if(negotiation.getTicketList().isEmpty()) {
			price = negotiation.getRandomPrice();
		} else {
			int laps = negotiation.getTicketList().size(); 
			TicketService lastTicket = negotiation.getTicketList().get(negotiation.getTicketList().size() - 1);
			
			// Initialize price with last ticket price
			price = lastTicket.getPrice();
			// Based on the last ticket price
			if(laps > 3) {
				// Less than 10% of the price
				if((1/laps) * REGULATOR * price < price * 0.1) {
					price *= 1.05;
				} else {
					price = (1/laps) * REGULATOR * price; 
				}
			} else {
				price *= 0.8;
			}
		}
		
		Message message = new Message(this, negotiation.getProvider(), negotiation, price, MessageType.OFFER); 
		System.out.println("---------------- \nLe client " + this.getName() + " fait une proposition avec le prix "  + Math.round(message.getPrice())  + " à " + negotiation.getProvider().getName());
		this.getInbox().send(message);
	}
	
	public boolean acceptOffer(double price) {
		boolean accept = false;
		
		if(price < maximumBudget) {
			Random r = new Random();
			int nbLuck = 1 + r.nextInt(100 - 1);
			double probabilityPrice = maximumBudget / price;
			
			if(probabilityPrice > HIGH_PROBABILITY) {
				if(nbLuck < 5) {
					accept = true;
				}
			} else if (probabilityPrice > AVERAGE_PROBABILITY) {
				if(nbLuck < 30){
					accept = true;
				}
			} else {
				if(nbLuck < 5) {
					accept = true;
				}
			}
		}
		
		return accept;
	}
}
