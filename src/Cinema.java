
public class Cinema {
	private int cinemaId;
	private int capacity;
	
	public Cinema(int cinemaId, int capacity){
		setCinemaId(cinemaId);
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
}
