import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBConnection{
	final private String INSERT_CINEMA = "INSERT INTO cinema(cinema_num, capacity) VALUES (?, ?)";
	final private String INSERT_MOVIES = "INSERT INTO movies(title, description, rating, length) VALUES (?,?,?,?)";
	final private String INSERT_SCHEDULES = "INSERT INTO schedules(date, movie_id, cinema_id) VALUES (?,?,?)";
	final private String INSERT_RESERVATIONS = "INSERT INTO reservations(reserv_date, seat, guest_name, guest_type, sched_id) VALUES (?,?,?,?,?)";
	final private String GET_SCHED = "SELECT s.sched_id AS 'Sched#', c.cinema_num AS 'Cinema', CAST(s.sched_date AS TIME) AS 'Time Showing', m.title AS 'Movie' " + 
									 "FROM schedules AS s INNER JOIN cinemas AS c ON s.cinema_id = c.cinema_id " + 
									 "INNER JOIN movies AS m ON s.movie_id = m.movie_id " + 
									 "WHERE CAST(s.sched_date AS DATE) BETWEEN ? AND ?";
	final private String GET_SEATS = "SELECT r.seat AS 'Seat' FROM reservations AS r WHERE r.sched_id = ?";
	
	
	private Connection conn;
	private Statement smt;
	private PreparedStatement psmt_cinema;
	private PreparedStatement psmt_movie;
	private PreparedStatement psmt_sched;
	private PreparedStatement psmt_reserv;
	private ResultSet rs;
	
	
	public DBConnection() {

		this.conn = null;
		this.smt = null;
		this.psmt_cinema = null;
		this.psmt_movie = null;
		this.psmt_sched = null;
		this.psmt_reserv = null;
		this.rs = null;
		
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/mrsdb?useUnicode=true&"
		    		+ "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
		    		   "root", "awsys+123");
		    
		    System.out.println("Connection established...\n\n");
		    
			this.psmt_cinema = conn.prepareStatement(INSERT_CINEMA);
			this.psmt_movie = conn.prepareStatement(INSERT_MOVIES);
			this.psmt_sched = conn.prepareStatement(INSERT_SCHEDULES);
			this.psmt_reserv = conn.prepareStatement(INSERT_RESERVATIONS);
		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public ResultSet excecuteAndPrint(String query, String lengthyColumn, int maxLength, String... columns) {
		try {
			this.smt = this.conn.createStatement();
		    this.rs = smt.executeQuery(query);
		    for(String column: columns) {
		    	if(column.equals(lengthyColumn)) {
	    			String temp = column;
	    			int numOfTabs = getNumOfTabs(temp, maxLength);
			    	System.out.print(column.toUpperCase());
			    	System.out.print("\t");
			    	for(int i = 0; i < numOfTabs; i++) {
				    	System.out.print("\t");
			    	}
	    		}else {
			    	System.out.print(column.toUpperCase()+ "\t");
	    		}
		    }
	    	System.out.println();
		    //rs.beforeFirst();
		    while(rs.next()) {
		    	for(String column: columns) {
		    		if(column.equals(lengthyColumn)) {
		    			String temp = rs.getString(column);
		    			int numOfTabs = getNumOfTabs(temp, maxLength);
				    	System.out.print(rs.getString(column));
				    	System.out.print("\t");
				    	for(int i = 0; i < numOfTabs; i++) {
					    	System.out.print("\t");
				    	}
		    		}else {
		    			if((rs.getString(column)).length() > 64) {
		    				System.out.print(rs.getString(column).substring(0, 60) + "...\t");
		    			}else {
		    				System.out.print(rs.getString(column) + "\t");
		    			}
		    		}
		    	}
		    	System.out.println();
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return this.rs;
	}
	
	public int insertToDB(Cinema cinema) {
		int returnValue = -1;
		
		try {
			this.psmt_cinema.setInt(1, cinema.getCinemaNum());
			this.psmt_cinema.setInt(2, cinema.getCapacity());
			
			returnValue = this.psmt_cinema.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	public int insertToDB(Movie movie) {
		int returnValue = -1;
		
		try {
			this.psmt_movie.setString(1, movie.getMovieTitle());
			this.psmt_movie.setString(2, movie.getMovieDescription());
			this.psmt_movie.setString(3, movie.getMovieRating());
			this.psmt_movie.setInt(4, movie.getMovieLength());
			
			returnValue = this.psmt_movie.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	public int insertToDB(Schedule schedule) {
		int returnValue = -1;
		
		try {
			this.psmt_sched.setDate(1, schedule.getStartTime());
			this.psmt_sched.setInt(2, schedule.getMovie().getMovieId());
			this.psmt_sched.setInt(2, schedule.getCinema().getCinemaId());
			
			returnValue = this.psmt_sched.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	public int insertToDB(Reservation reservation, int schedId) {
		int returnValue = -1;
		
		try {
			this.psmt_reserv.setTimestamp(1, reservation.getCurrentTimestamp());
			this.psmt_reserv.setString(2, reservation.getSeat().getSeatNum());
			this.psmt_reserv.setString(3, reservation.getGuest().getName());
			this.psmt_reserv.setInt(4, reservation.getGuest().getType());
			this.psmt_reserv.setInt(5, schedId);
			
			returnValue = this.psmt_reserv.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
	
	private int getNumOfTabs(String string, int max) {
		
		return (int) Math.floor(Math.floor((max)/8.0) - Math.floor(string.length()/8.0));
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ResultSet viewSched(Date date, Date date2, String... columns) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(GET_SCHED);
			ps.setDate(1, date);
			ps.setDate(2, new Date(2020-1900, 4-1, 2));
			rs = ps.executeQuery();
		    for(String column: columns) {
			    System.out.print(column.toUpperCase()+ "\t");
		    }
	    	System.out.println();
		    //rs.beforeFirst();
		    while(rs.next()) {
		    	for(String column: columns) {
				    System.out.print(rs.getString(column) + "\t");
		    	}
		    	System.out.println();
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    return this.rs;
	}
	
	public ArrayList<String> getSeats(int id, String column) {
		ArrayList<String> reservedSeats = new ArrayList<String>();
		try {
			PreparedStatement ps = this.conn.prepareStatement(GET_SEATS);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		    while(rs.next()) {
//		    	System.out.println(rs.getString(column));
		    	reservedSeats.add(rs.getString(column));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return reservedSeats;
	}
	
}
