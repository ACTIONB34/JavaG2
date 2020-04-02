
public class Seat {
	final static public int ROW = 5;
	final static public int COL = 8;
	
	final static public Seat[] seats = {
			new Seat("A1"), new Seat("A1"), new Seat("A1"), new Seat("A1"), new Seat("A1"), new Seat("A1"), new Seat("A1"), new Seat("A1"),
			new Seat("B1"), new Seat("B1"), new Seat("B1"), new Seat("B1"), new Seat("B1"), new Seat("A1"), new Seat("A1"), new Seat("A1"),
			new Seat("C1"), new Seat("C1"), new Seat("C1"), new Seat("C1"), new Seat("C1"), new Seat("A1"), new Seat("A1"), new Seat("A1"),
			new Seat("D1"), new Seat("D1"), new Seat("D1"), new Seat("D1"), new Seat("D1"), new Seat("A1"), new Seat("A1"), new Seat("A1"),
			new Seat("E1"), new Seat("E1"), new Seat("E1"), new Seat("E1"), new Seat("E1"), new Seat("A1"), new Seat("A1"), new Seat("A1")
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
