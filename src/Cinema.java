
public class Cinema {
	private int cinemaId;
	private int cinemaNum;
	private int capacity;
	
	public Cinema(int cinemaId, int cinemaNum, int capacity){
		setCinemaId(cinemaId);
		setCinemaNum(cinemaNum);
		setCapacity(capacity);
	}

	public int getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCinemaNum() {
		return cinemaNum;
	}

	public void setCinemaNum(int cinemaNum) {
		this.cinemaNum = cinemaNum;
	}
}
