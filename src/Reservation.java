import java.sql.Timestamp;

public class Reservation {
	private int reservationId;
	private Guest guest;
	private Seat seat;
	private Schedule schedule;
	
	public Reservation() {
		
	}
	
	public Reservation(int initId, Guest initGuest, Seat initSeat, Schedule initSched) {
		this.reservationId = initId;
		this.guest = initGuest;
		this.seat = initSeat;
		this.schedule = initSched;
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

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	
}
