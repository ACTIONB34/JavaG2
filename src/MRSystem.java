import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MRSystem {
	
	private DBConnection dbc;
	private ResultSet rs;
	
	private Scanner scan;
	
	private Reservation reservation;
	
	private ArrayList<Guest> guests;
	private ArrayList<Seat> seatChoices;
	private ArrayList<String> reservedSeats;
	
	private int movieChoice;
	private int scheduleChoice;
	private int numberOfSeats;
	private int numberOfSeatsToReserve;
	private int numOfKids;
	private int numOfRegulars;
	private int numOfSeniors;
	private int guestCount;
	private int totalAmount;
	private int inputSeatCount;
	private String tempDate;
	private String customerName;
	private Date dateToReserve;
	private Seat[] currentSeats;
	private String seatTemp;
	private String confirmChoice;
	private String dateChoice;
	
	public MRSystem() {
		init();
	}
	
	public void start(){
		while(true) {		
			displayMovies();
			if(chooseMovie()){		break;	}
			if(chooseDate()){		break;	}
			displaySchedule();
			if(chooseSchedule()){	break;	}
			displaySeats();
			if(getNumberOfSeats()){	break;	}
			if(chooseSeats()) {		break;	}
			
		}
	}
	private boolean chooseSeats() {
		
		return false;
	}

	private boolean getNumberOfSeats() {
		int tries = 0;
		boolean gettingNumOfSeats = true;
		
		while(gettingNumOfSeats) {
			try {
				if(tries >= 3) {
					System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return true;
				}
				
				System.out.print("No. of seats to reserve: ");						
				numberOfSeatsToReserve = Integer.parseInt(scan.nextLine());
				if(numberOfSeatsToReserve < 1) {
					System.out.println("ERROR: Input a number greater than 0.");
					tries++;
					continue;
				}else if(numberOfSeatsToReserve > numberOfSeats){
					System.out.println("ERROR: The number of seats you entered exceeds the remaining available seats. [Available Seats: " + numberOfSeats + "]");
					tries++;
					continue;
				}else {
					break;
				}
			}catch(Exception e) {
				System.out.println("ERROR: Invalid no. of seats");
				tries++;
			}	
		}
		return false;
	}

	private void displaySeats() {
		reservedSeats = dbc.getSeats(scheduleChoice, "Seat");
		updateSeats(reservedSeats, currentSeats);				

		System.out.println("--------------------------------------------------------------------------------");
		printSeats(currentSeats);
		System.out.println("--------------------------------------------------------------------------------");
		System.out.print("No. of available seats: " + (numberOfSeats - reservedSeats.size()));
		System.out.println("\n--------------------------------------------------------------------------------");
	}

	private boolean chooseSchedule() {
		int tries = 0;
		boolean askingSchedule= true;
		
		while(askingSchedule) {
			
			try {
				if(tries >= 3) {
					System.err.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return true;
				}
				
				System.out.print("Choose Sched#: ");
				scheduleChoice = Integer.parseInt(scan.nextLine());
				
				rs.beforeFirst();
				while(rs.next()) {
					if(rs.getInt("Sched#") == scheduleChoice) {
						reservation.getSchedule().setSchedID(scheduleChoice);
						reservation.getSchedule().setCinema(new Cinema());
						reservation.getSchedule().getCinema().setCinemaId(rs.getInt("cid"));
						reservation.getSchedule().getCinema().setCinemaNum(rs.getInt("Cinema"));
						
						askingSchedule = false;
						break;
					}
				}
				if(askingSchedule) {
					tries++;
					System.out.println("ERROR: Schedule ID not found");
				}
			}catch(Exception e) {
				tries++;
				System.out.println("ERROR: Invalid Schedule ID");
			}
		}
		return false;
	}

	private void displaySchedule() {
		System.out.println("SELECT A SCHEDULE");
		System.out.println("--------------------------------------------------------------------------------");
		rs = dbc.viewSched(dateToReserve, movieChoice, "Sched#", "Cinema", "Time Showing", "Movie");
		System.out.println("--------------------------------------------------------------------------------");
	}
	
	private boolean chooseDate() {
		/*===========+===========+===========+===========+===========+===========*/
		/*						    ASKS DATE FROM USER							 */
		/*===========+===========+===========+===========+===========+===========*/
		int tries = 0;
		boolean askingDate= true;
		
		LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime;
        
		System.out.println("--------------------------------------------------------------------------------");
		while(askingDate) {
			if(tries >= 3) {
				System.err.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
				return true;
			}
			System.out.print("Date(yyyy-mm-dd): ");
			String tempDate = scan.nextLine();
			try {
				if(tempDate.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))
	        		tempDate = tempDate + " 00:00";
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    			dateTime = LocalDateTime.parse(tempDate, formatter);
    			
    			if(dateTime.isBefore(now)){
    				System.out.println("ERROR: This was the past. Why go back?");
    				tries++;
    				continue;
    			}
    			dateToReserve = Date.valueOf(dateTime.toLocalDate());
    			break;
			}catch(Exception e) {
				System.out.println("ERROR: Invalid Date Format");
				tries++;
    			continue;
			}
		}
		return false;
	}
	
	private boolean chooseMovie() {
		int tries = 0;
		boolean askingId= true;
		
		while(askingId) {
			try {
				if(tries >= 3) {
					System.err.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return true;
				}
				
				System.out.print("Enter Movie ID: ");
				movieChoice = Integer.parseInt(scan.nextLine());
				
				rs.beforeFirst();
				while(rs.next()) {
					if(rs.getInt("id") == movieChoice) {
						reservation.setSchedule(new Schedule());
						reservation.getSchedule().setMovie(new Movie(rs.getInt("id"), rs.getString("title"), 
														   rs.getString("description"), rs.getInt("length"),
														   rs.getString("rating")));
						askingId = false;
						break;
					}
				}
				
				if(askingId) {
					tries++;
					System.out.println("ERROR: Movie ID not found");
				}
			}catch(Exception e) {
				System.out.println("ERROR: Invalid Movie ID");
				tries++;
			}
		}
		return false;
	}

	private void displayMovies() {
		System.out.println("===========+===========\n" + 
						   "\tWELCOME\n" + 
						   "===========+===========\n");	
		System.out.println("SELECT A MOVIE");
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		rs = dbc.excecuteAndPrint("select movie_id as id, title, length, description, rating from movies","title",
			 44, "id", "title", "length", "description");
		System.out.println("--------------------------------------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("deprecation")
	private void init() {
		scan = new Scanner(System.in);
		
		dbc = new DBConnection();
		reservation = new Reservation();
		
		guests = new ArrayList<Guest>();
		seatChoices = new ArrayList<Seat>();
		reservedSeats = new ArrayList<String>();
		
		movieChoice = -1;
		scheduleChoice = -1;
		numberOfSeats = 40;
		numberOfSeatsToReserve = 0;
		guestCount = 0;
		totalAmount = 0;
		inputSeatCount = 0;
		tempDate = null;
		customerName = "";
		dateToReserve = new Date(Date.UTC(0, 0, 0, 0, 0, 0));
		currentSeats = Seat.seats;
		seatTemp = "";
		confirmChoice = null;
		dateChoice = null;
	}
	
	private void printSeats(Seat[] seats) {
		int index = 0; 
		for(int i = 0; i < Seat.ROW; i++) {
			for(int j= 0; j < Seat.COL; j++) {
				System.out.print(seats[index].getSeatNum() + "\t");
				index++;
			}
			System.out.println();
		}
	}
	
	private void updateSeats(ArrayList<String> reservedSeats, Seat[] seats) {
		for(String curr: reservedSeats) {
			for(int i = 0; i < seats.length; i++) {
				if(seats[i].getSeatNum().equalsIgnoreCase(curr)) {
					seats[i].setSeatNum("XX");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MRSystem().start();
	}
}
