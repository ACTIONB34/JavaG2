
public class Movie {
	private int movieId;
	private String movieTitle;
	private String movieDescription;
	private int movieLength;
	private String movieRating;
	
	public Movie(int movieId, String movieTitle, String movieDescription, int movieLength, String movieRating) {
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.movieDescription = movieDescription;
		this.movieLength = movieLength;
		this.movieRating = movieRating;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public int getMovieLength() {
		return movieLength;
	}

	public void setMovieLength(int movieLength) {
		this.movieLength = movieLength;
	}

	public String getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}

	
}