
public class Seat {
	final static public int ROW = 5;
	final static public int COL = 8;
	
	final static public Seat[] seats = {
			new Seat("A1"), new Seat("A2"), new Seat("A3"), new Seat("A4"), new Seat("A5"), new Seat("A6"), new Seat("A7"), new Seat("A8"),
			new Seat("B1"), new Seat("B2"), new Seat("B3"), new Seat("B4"), new Seat("B5"), new Seat("B6"), new Seat("B7"), new Seat("B8"),
			new Seat("C1"), new Seat("C2"), new Seat("C3"), new Seat("C4"), new Seat("C5"), new Seat("C6"), new Seat("C7"), new Seat("C8"),
			new Seat("D1"), new Seat("D2"), new Seat("D3"), new Seat("D4"), new Seat("D5"), new Seat("D6"), new Seat("D7"), new Seat("D8"),
			new Seat("E1"), new Seat("E2"), new Seat("E3"), new Seat("E4"), new Seat("E5"), new Seat("E6"), new Seat("E7"), new Seat("E8")
		};
	
	private String seatCode;
	
	public Seat(String seatCode) {
		this.seatCode = seatCode;
	}
	
	public String getSeatNum() {
		return this.seatCode;
	}
	
	public void setSeatNum(String seatNum) {
		this.seatCode = seatNum;
	}
}
