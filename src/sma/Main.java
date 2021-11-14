package sma;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
	
	public static void main(String[] args) {
		Inbox inbox = new Inbox();
	
		
		ArrayList<Provider> providers = new ArrayList<Provider>();
		Provider provider1 = new Provider("Air France", 100, inbox);
		Provider provider2 = new Provider("Swiss Air", 200, inbox);
		Provider provider3 = new Provider("Brussel Airlines", 300, inbox);
		Provider provider4 = new Provider("Easyjet", 400, inbox);
		Provider provider5 = new Provider("Emirates", 500, inbox);
		
		providers.add(provider1);
		providers.add(provider2);
		providers.add(provider3);
		providers.add(provider4);
		providers.add(provider5);
		
		Calendar calendar = new GregorianCalendar(2022,4,1);
		Client client1 = new Client("M. Olivier", 350, inbox);
		Negotiation negotiation1 = new Negotiation("Paris", "Los Angeles", calendar.getTime());
		negotiation1.setProvider(provider1);
		negotiation1.setClient(client1);
		
		Negotiation negotiation2 = new Negotiation("Paris", "Los Angeles", calendar.getTime());
		negotiation2.setProvider(provider2);
		negotiation2.setClient(client1);
		
		Thread thread1 = new Thread(negotiation1);
		thread1.start();
		
//		Thread thread2 = new Thread(negotiation2);
//		thread2.start();
	}
}
