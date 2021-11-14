package sma;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class Provider extends Agent{
	private static ArrayList<TicketService> TICKETS_LIST = new ArrayList<TicketService>();
	static {
		TICKETS_LIST.add(new TicketService("Lyon", "Berlin", new GregorianCalendar(2022,7,1).getTime(), 200d));
		TICKETS_LIST.add(new TicketService("Paris", "Los Angeles", new GregorianCalendar(2022,8,1).getTime(), 350d));
		TICKETS_LIST.add(new TicketService("Lyon", "Paris", new GregorianCalendar(2022,9,1).getTime(), 400d));
		TICKETS_LIST.add(new TicketService("Lyon", "Bordeaux", new GregorianCalendar(2022,6,1).getTime(), 150d));
	}
	
	private double minimumPrice;
	private static double LOW_PROBABILITY = 1.2;
	private static double AVERAGE_PROBABILITY =  1.5;
	
	public double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	
	public Provider(String name, double minimumPrice, Inbox inbox) {
		super(name, inbox);
		this.minimumPrice = minimumPrice;
	}
	
	public void makeOffer(Negotiation negotiation) {
		double price = 0d;
		// A ticket is found for this negotiation
		if(negotiation.getTicketList().isEmpty()) {
			TicketService ticket = find(negotiation.getDeparturePlace(), negotiation.getArrivalPlace(), negotiation.getDesiredDate());
			if(ticket != null) {
				price = ticket.getPrice();
				negotiation.addTicket(ticket);
			}
		} else {
			TicketService lastTicket = negotiation.getTicketList().get(negotiation.getTicketList().size() - 1);	
			
			price = lastTicket.getPrice();
			
			// 10% cheaper the the last ticket
			if(price > this.minimumPrice) {
				price = 0.9 * lastTicket.getPrice();
			} else {
				price *= 1.1;
			}
			
			TicketService newTicket = new TicketService(negotiation.getDeparturePlace(), negotiation.getArrivalPlace(), negotiation.getDesiredDate(), price);
			negotiation.addTicket(newTicket); // Add a new ticket to the negotiation
		}
		
		Message message = new Message(this, negotiation.getClient(), negotiation, price, MessageType.OFFER); 
		System.out.println("---------------- \nLe fournisseur " + this.getName() + " fait une proposition avec le prix "  + Math.round(message.getPrice())  + " à " + negotiation.getClient().getName());
		this.getInbox().send(message);
	}
		
	public boolean acceptOffer(double price) {
		boolean accept = false;
		
		if(price > minimumPrice) {
			Random r = new Random();
			int nbLuck = 1 + r.nextInt(100 - 1);
			double probabilityPrice = price / minimumPrice;
			
			if(probabilityPrice < LOW_PROBABILITY) {
				if(nbLuck < 5) {
					accept = true;
				}
			} else if (probabilityPrice < AVERAGE_PROBABILITY) {
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
	
	public TicketService find(String departure, String arrival, Date date) {
		TicketService ticket = null;
		for(TicketService _ticket : TICKETS_LIST) {
			if(_ticket.getDeparturePlace().equals(departure) && 
					_ticket.getArrivalPlace().equals(arrival) &&
						_ticket.getDepartureDate().after(date)) {
				ticket = _ticket;
				break;
				
			}
		}
		
		return ticket;
	}

	public static ArrayList<TicketService> getTICKETS_LIST() {
		return TICKETS_LIST;
	}

	public static void setTICKETS_LIST(ArrayList<TicketService> tICKETS_LIST) {
		TICKETS_LIST = tICKETS_LIST;
	}
}
