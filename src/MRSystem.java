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
	private int totalAmount;
	private String customerName;
	private Date dateToReserve;
	private Seat[] currentSeats;
	private String confirmChoice;
	
	public MRSystem() {
		init();
	}
	
	public void start(){
		main: while(true) {		
			selectMovie: while(true) {
				displayMovies();
				if(chooseMovie()){		break main;	}
				selectDate: while(true) {
					if(chooseDate()){		break main;	}
					int choice = displaySchedule();
					switch(choice) {
					case 0:
						System.out.println("Exiting...");
						break main;
					case 1:
						continue selectDate;
					case 2:
						continue selectMovie;
					case 3:
						break selectDate;
					default:
						System.out.println("ERROR: Invalid input. Exiting...");
						break main;
					}
				}
				if(chooseSchedule()){	break main;	}
				displaySeats();
				if(getNumberOfSeats()){	break main;	}
				if(chooseSeats()) {		break main;	}
				if(specifyGuests()) {	break main;	}
				saveToDB();
				if(confirmTransaction()) {	break main;	}
			}
		}
	}
	
	private boolean confirmTransaction() {
		/*===========+===========+===========+===========+===========+===========*/
		/*						    CONFIRM TRANSACTION	?						 */
		/*===========+===========+===========+===========+===========+===========*/
		int tries = 0;
		
		while(true) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.print("Proceed with this transaction? (Y/N): ");
			confirmChoice = scan.nextLine();
			System.out.println("--------------------------------------------------------------------------------");
			
			if(confirmChoice.equalsIgnoreCase("y")) {
				System.out.println("--------------------------------------------------------------------------------");
				System.out.print("Name of Customer: ");
				customerName = scan.nextLine();
				
				for(int i = 0; i < guests.size(); i++) {
					Guest guest = guests.get(i);
					guest.setName(customerName);
					reservation.setSeat(seatChoices.get(i));
					reservation.setGuest(guest);
					dbc.insertToDB(reservation);
				}
				System.out.println("DONE\n\n\n");
				return false;
			}else if(confirmChoice.equalsIgnoreCase("n")) {
				System.out.println("Canceling transaction...");
				return false;
			}else {
				System.out.println("Invalid...");
				tries++;
				if(tries >= 3) {
					System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return true;
				}
			}
		}
	}

	private void saveToDB() {
		for(Guest guest: guests) {
			totalAmount += guest.getRate();
		}
		
		System.out.println("===========+===========+===========+===========\n" + 
						   "\t\tTRANSACTION SUMMARY\n" + 
						   "===========+===========+===========+===========");
		System.out.println("Category\tRate\tQuantity\tAmount");
		if(0 < numOfRegulars) {
			System.out.println("Regular\t\t" + Guest.REGULAR_RATE + "\t"+ numOfRegulars +"\t\t"+ Guest.REGULAR_RATE*numOfRegulars);
		}
		if(0 < numOfKids) {
			System.out.println("Kid\t\t" + Guest.KID_RATE + "\t"+ numOfKids +"\t\t"+ Guest.KID_RATE*numOfKids);
		}
		if(0 < numOfSeniors) {
			System.out.println("Senior\t\t" + Guest.SENIOR_RATE + "\t"+ numOfSeniors +"\t\t"+ Guest.SENIOR_RATE*numOfSeniors);
		}
		System.out.println("Total:\t\t\t\t\t"+ totalAmount);
	}

	private boolean specifyGuests() {
		int seatsRemaining = numberOfSeatsToReserve;
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Specify no. guest(s) for each type");
		System.out.println("--------------------------------------------------------------------------------");
		
		int tries = 0;
		while(true) {
			numOfKids = getNumGuests("Kid", seatsRemaining);
			if (numOfKids == -1){
				return true;
			}else {
				seatsRemaining -= numOfKids;
			}
			
			numOfRegulars = getNumGuests("Regular", seatsRemaining);
			if (numOfRegulars == -1){
				return true;
			}else {
				seatsRemaining -= numOfRegulars;
			}
			
			numOfSeniors = getNumGuests("Senior", seatsRemaining);
			if (numOfSeniors == -1){
				System.out.println("ERROR: You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
			}else {
				seatsRemaining -= numOfSeniors;
			}
			
			if(seatsRemaining > 0) {
				if(tries >= 3) {
					System.out.println("ERROR: You have enumerated tried this multiple times already. Exiting...\n");
					return true;
				}else {
					tries++;
					System.out.println("ERROR: You have enumerated " + (numberOfSeatsToReserve - seatsRemaining) + " guests only. Reverting...\n");
				}
				continue;
			}else {
				numberOfSeats -= numOfKids + numOfRegulars + numOfSeniors;
				
				for(int m = 0; m < numOfKids; m++) {
					guests.add(new Kid(customerName));
				}
				for(int n = 0; n < numOfRegulars; n++) {
					guests.add(new Guest(customerName));
				}
				for(int o = 0; o < numOfSeniors; o++) {
					guests.add(new Senior(customerName));
				}
				break;
			}
		}
		return false;
	}
	
	private int getNumGuests(String guestType, int seatsRemaining) { 
		int guestCount;
		boolean askingCount = true;
		int tries = 0;
		
		while(askingCount) {
			try {
				if(tries >= 3) {
					System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return -1;
				}
				
				System.out.print("No. of " + guestType + "(s):\t");
				guestCount = Integer.parseInt(scan.nextLine());
				if(guestCount < 0) {
					System.out.println(guestType + " count given was a negative number. Try again!");
					tries++;
					continue;
				}else if(guestCount > seatsRemaining) {
					System.out.println(guestType + " count given was greater than given Reserved Seats. Try again!");
					tries++;
					continue;
				}
				return guestCount;
			}catch(Exception ex) {
				System.out.println("Invalid Format for "+ guestType+ " count. Try again!");
				tries++;
			}
		}
		return -1;
	}

	private boolean chooseSeats() {
		int tries = 0;
		boolean choosingSeats = true;
		int seatCount = 0;
		
		while(choosingSeats) {
			try {
				if(tries >= 3) {
					System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
					return true;
				}
				
				System.out.print("Seat #" + (seatCount + 1) + ": ");
				String tempSeat = scan.nextLine().toUpperCase();
				if(!tempSeat.matches("[A-E][1-8]")) {
					System.out.println("ERROR: Invalid Seat Location");
					tries++;
					continue;
				}else if(reservedSeats.contains(tempSeat)) {
					System.out.println("ERROR: Seat is already taken");
					tries++;
					continue;
				}else if(findSeat(tempSeat)) {
					System.out.println("ERROR: You already inputted this seat.");
					tries++;
					continue;
				}
				
				seatChoices.add(new Seat(tempSeat));
				seatCount++;
				tries = 0;
				if(seatCount >= numberOfSeatsToReserve) {
					choosingSeats = false;
				}
			}catch(Exception ex) {
				System.out.println("ERROR: Invalid no. of seats");
				tries++;
			}
		}
		return false;
	}
	
	private boolean findSeat(String tempSeat) {
		for(Seat s: seatChoices) {
			if(s.getSeatNum().equals(tempSeat)) {
				return true;
			}
		}
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
		

		System.out.println("--------------------------------------------------------------------------------" + reservedSeats.size());
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

	private int displaySchedule() {
		System.out.println("SELECT A SCHEDULE");
		System.out.println("--------------------------------------------------------------------------------");
		rs = dbc.viewSched(dateToReserve, movieChoice, "Sched#", "Cinema", "Time Showing", "Movie");
		System.out.println("--------------------------------------------------------------------------------");
		int choice = 3;
		try {
			if(!rs.next()) {
				System.out.print("Would you like to select a different schedule:\n"
						+ "1 - Yes. Take me back to the previous menu.\n"
						+ "2 - No. I want to select another movie.\n"
						+ "0 - EXIT\n"
						+ "CHOICE: ");				
				try {
					choice = Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {
					choice = -1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return choice; 
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
    			dateTime = dateTime.plusDays(1);
    			
    			if(dateTime.isBefore(now)){
    				System.out.println("ERROR: You cannot reserve seat(s) on past dates. Enter a valid date...");
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
		totalAmount = 0;
		customerName = "";
		dateToReserve = new Date(Date.UTC(0, 0, 0, 0, 0, 0));
		currentSeats = Seat.seats;
		confirmChoice = null;
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
