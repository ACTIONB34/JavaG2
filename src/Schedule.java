import java.sql.Timestamp;

public class Schedule {
	private int schedID;
	private Timestamp startTime;
	private Timestamp endTime;
	private String dateShowing;
	private Seat seat;
	private Movie movie;
	private Cinema cinema;	
	
	@SuppressWarnings("deprecation")
	public void computeEndTime(int movieDuration) {
		int min = (this.startTime.getMinutes() + movieDuration)%60;
		int hour = this.startTime.getHours() + (this.startTime.getMinutes() + movieDuration)/60;
		this.endTime = (Timestamp) getStartTime().clone();
		this.endTime.setHours(hour);
		this.endTime.setMinutes(min);
	}
	
	public int getSchedID() {
		return schedID;
	}
	public void setSchedID(int schedID) {
		this.schedID = schedID;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
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
