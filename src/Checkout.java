import java.util.ArrayList;

public class Checkout {
	private int ticketQuantity;
	private double totalAmount;
	private ArrayList<String> ticketTypes;
	private ArrayList<Integer> ticketCounts;
	
	public Checkout() {
		ticketTypes = new ArrayList<String>();
		ticketCounts = new ArrayList<Integer>();
		
		ticketQuantity = 0;
		totalAmount = 0;
	}
	
	public void addTicket(String ticketType, int ticketCount) {
		ticketTypes.add(ticketType);
		ticketCounts.add(ticketCount);
		
		ticketQuantity += ticketCount;
		//100 is to be modified depending if kid,
		totalAmount += ticketCount * 100;
	}
	
	public void removeTicket(String ticketType, int ticketCount) {
		for(int i = 0 ; i < ticketType.length() ; i++)
			if(ticketType.equals(ticketTypes.get(i)))
					ticketCounts.set(i , ticketCounts.get(i) - ticketCount);
	}
	
	public int getTicketQuantity() {
		return ticketQuantity;
	}
	
	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public ArrayList<String> getTicketTypes() {
		return ticketTypes;
	}
}
