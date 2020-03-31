
public class Schedule {
	private int schedID;
	private int startTime;
	private int endTime;
	private String dateShowing;
	private Seat seat;
	private Movie movie;
	private Cinema cinema;
	
	
	
	
	public void computeEndTime(int movieDuration) {
		this.endTime = this.startTime + movieDuration;
	}
	
	public int getSchedID() {
		return schedID;
	}
	public void setSchedID(int schedID) {
		this.schedID = schedID;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public String getDateShowing() {
		return dateShowing;
	}
	public void setDateShowing(String dateShowing) {
		this.dateShowing = dateShowing;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
}
