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
	
	public void addTicket(String ticketType, int ticketCount, int rate) {
		if(ticketTypes.contains(ticketType)) {								//if ticketType already exists
			int index = ticketTypes.indexOf(ticketType);
			ticketCounts.set(index, ticketCounts.get(index) + ticketCount);
		}else {																//else, add a new ticketType
			ticketTypes.add(ticketType);
			ticketCounts.add(ticketCount);
		}
		
		ticketQuantity += ticketCount;
		totalAmount += ticketCount * rate;
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
