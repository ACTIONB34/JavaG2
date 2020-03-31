
public class Seat {
	private int seatId;
	private String seatNum;
	
	public Seat(int id, String seatNum) {
		this.seatId = id;
		this.seatNum = seatNum;
	}
	
	public int getSeatId() {
		return seatId;
	}
	
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	
	public String getSeatNum() {
		return seatNum;
	}
	
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
}
