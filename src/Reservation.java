import java.sql.Timestamp;

public class Reservation {
	private int reservationId;
	private Timestamp timestamp;
	private Guest guest;
	private Seat seat;
	
	public Reservation() {
		this.timestamp = getCurrentTimestamp();
	}
	
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
	
	public Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
