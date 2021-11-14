package sma;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Negotiation implements Runnable {
	private static int MAX_EXCHANGES_NUMBER = 5;
	private static float MAX_NEGOTATION_TIME = 20f;
	private static Random R = new Random();
	private ArrayList<TicketService> ticketList;
	private Provider provider;
	private Client client;
	private Date desiredDate;
	private String departurePlace;
	private String arrivalPlace;
	private double maximumBudget;
	private State state;
		
	public Negotiation(String departurePlace, String arrivalPlace, Date desiredDate) {
		this.desiredDate = desiredDate;
		this.departurePlace = departurePlace;
		this.arrivalPlace = arrivalPlace;
		this.ticketList = new ArrayList<TicketService>();
		this.state = State.OPENED;
	}
	
	@Override
	public void run() {
		int exchanges = 0;
		// Get current time
		long start = System.currentTimeMillis();
		long elapsedTimeMillis;
		float elapsedTimeSec = 0f;
		
		System.out.println("---------- La négociation commence entre " + this.client.getName() + " et "  + this.provider.getName() +" ----------");
		// While the negotiation is opened , max number of exchanges is not reached and time is not out
		

		Boolean findTicket = false;
		
		ArrayList<TicketService> maListe = this.provider.getTICKETS_LIST();
		for (TicketService ticketService : maListe) {
			  int result = this.desiredDate.compareTo(ticketService.getDepartureDate());
			if(this.arrivalPlace.equals(ticketService.getArrivalPlace())
					&& this.departurePlace.equals(ticketService.getDeparturePlace())
				&&	result < 0 ) {
//				System.out.println("date de depart desiré : " +this.desiredDate + "date depart ticket : " +ticketService.getDepartureDate());
//				System.out.println("lieu arrivé desiré : " +this.arrivalPlace + "date depart ticket : " +ticketService.getArrivalPlace());
//				System.out.println("lieu de depart desiré : " +this.departurePlace + "date depart ticket : " +ticketService.getDeparturePlace());

				findTicket = true;
				break;
			}
		}
		
		// While the negotiation is opened , max number of exchanges is not reached and time is not out
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (findTicket) {
		
		while(state.equals(State.OPENED) && 
				exchanges < MAX_EXCHANGES_NUMBER && 
					elapsedTimeSec <= MAX_NEGOTATION_TIME) {
			// Get elapsed time in milliseconds
			elapsedTimeMillis = System.currentTimeMillis() - start;
			// Get elapsed time in seconds
			elapsedTimeSec = elapsedTimeMillis/1000F;
			
			processingProvider();
			processingClient();
			exchanges++;
		}
		}else {
			System.out.println("Il n'y a pas vol disponible. ");
		}
		if(exchanges >= MAX_EXCHANGES_NUMBER) {
			System.out.println("Beaucoup trop d'échanges !");
		}
		
		if(elapsedTimeSec >= MAX_NEGOTATION_TIME) {
			System.out.println("Time out");
		}
		
		System.out.println("Fin de la négociation entre " + this.client.getName() + " et " + this.provider.getName());
	}
	
	public void addTicket(TicketService ticket) {
		this.ticketList.add(ticket);
	}
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public ArrayList<TicketService> getTicketList() {
		return ticketList;
	}

	public void setTicketList(ArrayList<TicketService> ticketList) {
		this.ticketList = ticketList;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}

	public String getArrivalPlace() {
		return arrivalPlace;
	}

	public void setArrivalPlace(String arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}

	public double getMaximumBudget() {
		return maximumBudget;
	}

	public void setMaximumBudget(double maximumBudget) {
		this.maximumBudget = maximumBudget;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public double getRandomPrice() {
		return 10 + (this.client.getMaximumBudget() - 10) * R.nextDouble();
	}
	
	public Date getDesiredDate() {
		return desiredDate;
	}
	
	public void setDesiredDate(Date desiredDate) {
		this.desiredDate = desiredDate;
	}
	
	private void processingProvider() {
		for(Message message : this.provider.getMessages()) {
			if(message.getEmitter().equals(this.client)) {
				if(!this.provider.acceptOffer(message.getPrice()) && message.getType().equals(MessageType.OFFER)) {
					System.out.println("Le fournisseur " + this.provider.getName() + " a refué l'offre de " + this.client.getName() + " avec un prix de "  + Math.round(message.getPrice()));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!this.getTicketList().isEmpty()) {
						// Set minimum price up to 20% of the last ticket price
						this.provider.setMinimumPrice(this.ticketList.get(this.getTicketList().size() - 1).getPrice() * 0.9);
					}
					// Provider makes an offer
					this.provider.makeOffer(this);
				} else {
					System.out.println("Le fournisseur " + this.provider.getName() + " a accepté l'offre");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.state = State.CLOSED;
				}
				
				// Removes the message
				this.provider.getMessages().remove(message);
			}
		}
	}
	
	private void processingClient() {
		if(this.client.getMessages().isEmpty() && this.ticketList.isEmpty()) {
			// First offer, negotiation starts here
			System.out.println("Le client fait la première proposition : ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.client.makeOffer(this);
		} 
		
		for(Message message : this.client.getMessages()) {
			if(message.getEmitter().equals(this.provider)) {
				if(!this.client.acceptOffer(message.getPrice()) && message.getType().equals(MessageType.OFFER)) {
					System.out.println("Le client " + this.client.getName() + " a refusé l'offre de " + this.provider.getName() + " avec un prix de "  + Math.round(message.getPrice()));
					
					// Client makes an offer
					this.client.makeOffer(this);
				} else {
					System.out.println("Le client " + this.client.getName() + " a accepté l'offre");
					this.state = State.CLOSED;
				}
				
				this.client.getMessages().remove(message);
			}
		}
	}
}
