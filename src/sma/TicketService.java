package sma;
import java.util.Date;

public class TicketService {

	private String departurePlace;
	private String arrivalPlace;
	private Date departureDate;
	private double price;
	
	public TicketService(String departurePlace, String arrivalPlace, Date departureDate, double price) {
		this.setDeparturePlace(departurePlace);
		this.setArrivalPlace(arrivalPlace);
		this.setDepartureDate(departureDate);
		this.setPrice(price);
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
	
	public Date getDepartureDate() {
		return departureDate;
	}
	
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
}
