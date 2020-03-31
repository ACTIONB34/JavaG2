import java.sql.Timestamp;

public class Reservation {
	private int reservationId;
	private int timestamp;
	private Guest guest;
	private Seat seat;
	
	public int getReservationId() {
		return reservationId;
	}
	
	public void setReservation_id(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}	
	
	public Guest getGuest() {
		return guest;
	}
	
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}	
	
	public Timestamp getCurrentTimestamp() {
		Timestamp currTimestamp = new Timestamp(System.currentTimeMillis());
		
		return currTimestamp;
	}
}
