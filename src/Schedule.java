import java.sql.Date;

public class Schedule {
	private int schedID;
	private Seat seat;
	private Movie movie;
	private Cinema cinema;
	private Date schedDate;
	private Date startTime;
	private Date endTime;
	
	
	@SuppressWarnings("deprecation")
	public void computeEndTime(int movieDuration) {
		int min = (this.startTime.getMinutes() + movieDuration)%60;
		int hour = this.startTime.getHours() + (this.startTime.getMinutes() + movieDuration)/60;
		this.endTime = (Date) getStartTime().clone();
		this.endTime.setHours(hour);
		this.endTime.setMinutes(min);
	}


	public int getSchedID() {
		return schedID;
	}


	public void setSchedID(int schedID) {
		this.schedID = schedID;
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


	public Date getSchedDate() {
		return schedDate;
	}


	public void setSchedDate(Date schedDate) {
		this.schedDate = schedDate;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
