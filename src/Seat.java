
public class Seat {
	final static public int ROW = 5;
	final static public int COL = 8;
	
	final static public String[][] seats =  {
			{"A1","A2","A3","A4","A5","A6","A7","A8"},
			{"B1","B2","B3","B4","B5","B6","B7","B8"},
			{"C1","C2","C3","C4","C5","C6","C7","C8"},
			{"D1","D2","D3","D4","D5","D6","D7","D8"},
			{"E1","E2","E3","E4","E5","E6","E7","E8"}
		};
	
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
