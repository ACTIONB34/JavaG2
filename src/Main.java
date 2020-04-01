import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	
	private static void printSeats(String[][] seats) {
		for(int i = 0; i < Seat.ROW; i++) {
			for(int j= 0; j < Seat.COL; j++) {
				System.out.print(seats[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		ResultSet rs;
		
		Scanner scan = new Scanner(System.in);
		
		Movie movie;
		Cinema cinema;
		Seat seat;
		Schedule schedule;
		Reservation reservation;
		
		
		int schedNumber;
		ArrayList<String> reservedSeats = new ArrayList<String>();
		int movieChoice;
		String tempDate;
		Date dateToReserve;
		int day;
		Date nextDay;
		String[][] currentSeats = Seat.seats;
		
		int scheduleChoice;
		int numberOfSeats;
		
		ArrayList<String> seatChoice = new ArrayList<String>();
		String guestName;
		String confirmChoice;
		String dateChoice;
		
//		Guest guest = new Guest("Chrissia",Guest.REGULAR,150);
//		Seat seat = new Seat(-1, "C2");
//		Reservation res = new Reservation();
//		res.setGuest(guest);
//		res.setSeat(seat);
//		
//		dbc.insertToDB(res, 56007);

		try {
			System.out.println("--------------------------------------------------------------------------------");
			rs = dbc.excecuteAndPrint("select movie_id as id, title, length, description, rating from movies","title",
					50, "id", "title", "length", "description");
			System.out.println("--------------------------------------------------------------------------------");
			System.out.print("Choose Movie ID: ");
			movieChoice = Integer.parseInt(scan.nextLine());
			if(rs.next()) {
				movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getInt("length"), rs.getString("rating"));
			}
			
			System.out.println("Date(yyyy-mm-dd): ");
			tempDate = scan.nextLine();
			String[] dateArr = tempDate.split("-");
		
			dateToReserve = new Date(Integer.parseInt(dateArr[0]) - 1900, Integer.parseInt(dateArr[1]) -1, Integer.parseInt(dateArr[1]) - 1);
			//System.out.println(dateToReserve.toString());
			nextDay = new Date(Integer.parseInt(dateArr[0]) - 1900, Integer.parseInt(dateArr[1]) -1, Integer.parseInt(dateArr[1]));
			//System.out.println(nextDay);
			
			System.out.println("--------------------------------------------------------------------------------");
			dbc.viewSched(dateToReserve, nextDay, movieChoice, "Sched#", "Cinema", "Time Showing", "Movie");
			System.out.println("--------------------------------------------------------------------------------");
			System.out.print("Choose Sched#: ");
			schedNumber = scan.nextInt();
			
			
			reservedSeats = dbc.getSeats(schedNumber, "Seat");
			for (String str : reservedSeats){
				System.out.println(str);	
		    }
			
			printSeats(currentSeats);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
//		System.out.println("--------------------------------------------------------------------------------");
//		System.out.println("**input 0 to go back to last page");
//		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//		System.out.println("Director:\tMovieDirector");							//Needs conversion to actual class
//		System.out.println("Schedule #\t\tCinema #\t\tDate & Time Slot");
//		for(int i = 1; i <= 3; i++) {		//Dapat mu end sa number of items
//			System.out.print(i + "\t\t\tCinema " + i + "\t\tDate & Time Slot" + i + "\n");	//Needs conversion to actual class
//		}
//		System.out.println("--------------------------------------------------------------------------------");
//		System.out.println("Choose Schedule #: ");
//		scheduleChoice = myObj.nextInt();
//		
//		System.out.println("--------------------------------------------------------------------------------");
//		System.out.println("# of seats");
//		System.out.println("\tKid(s): ");
//		numberOfSeats = myObj.nextInt();
//		System.out.println("\tRegular(s): ");
//		numberOfSeats += myObj.nextInt();
//		System.out.println("\tSenior(s): ");
//		numberOfSeats += myObj.nextInt();
//		myObj.nextLine();
//		System.out.println("--------------------------------------------------------------------------------");
//		
//		System.out.println("--------------------------------------------------------------------------------");
//		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//		System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
//		System.out.println("Seats");
//		for(int i = 0; i <= 5; i++) {											//Prints the seating table
//			for(int j = 0; j <= 8; j++) {										//No reserved seats yet
//				if(i == 0)
//					System.out.print(j + "\t");
//				else {
//					System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
//					if(j == 7)
//						break;
//				}
//			}
//			if(i != 5)
//				System.out.print("\n" + (char)('A' + i));
//		}
//		System.out.println("\n--------------------------------------------------------------------------------");
//		for(int i = 0; i < numberOfSeats; i++) {
//			System.out.println("Seat " + (i + 1) + ": ");
//			seatChoice.add(myObj.nextLine());
//		}
//		
//		System.out.println("----------------------------------------------------------------------------------");
//		System.out.println("**input 0 to go back to last page");
//		System.out.println("Title\tMovieTitle");								//Needs conversion to actual class
//		System.out.println("Date & Time Slot#");								//Needs conversion to actual class
//		System.out.println("Cinema#");											//Needs conversion to actual class
//		System.out.println("Seat #: ");
//		for(String i : seatChoice) {
//			System.out.print(i + ", ");
//		}
//		System.out.println("\nGuest name: ");
//		guestName = myObj.nextLine();											//Gets name, dummy variable
//		System.out.println("Confirm (Y/N): ");
//		confirmChoice = myObj.nextLine();										//Lacking function if confirmation == N
//		System.out.println("----------------------------------------------------------------------------------");
//		
//		System.out.println("----------------------------------------------------------------------------------");
//		System.out.println("CHECKOUT");
//		System.out.println("Subtotal:\t\t# of Tickets\t\tPrice");
//		System.out.println("\tKid(s):\t\t#ofKid(s)\tx\tEachPrice");				//Needs conversion to actual class
//		System.out.println("\tRegular(s):\t#ofRegular(s)\tx\tEachPrice");		//Needs conversion to actual class
//		System.out.println("\tSenior(s):\t#ofSenior(s)\tx\tEachPrice");			//Needs conversion to actual class
//		System.out.println("Total: TotalPrice");								//Needs conversion to actual class
//		System.out.println("----------------------------------------------------------------------------------");
//		
//		System.out.println("----------------------------------------------------------------------------------");
//		System.out.println("Seat # ");
//		for(String i : seatChoice) {
//			System.out.print(i);
//			if(i != null)
//				System.out.print(", ");
//		}
//		System.out.println("\n----------------------------------------------------------------------------------");
//		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//		System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
//		System.out.println("Seats");
//		for(int i = 0; i <= 5; i++) {											//Prints the seating table
//			for(int j = 0; j <= 8; j++) {										//No reserved seats yet
//				if(i == 0)
//					System.out.print(j + "\t");
//				else {
//					System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
//					if(j == 7)
//						break;
//				}
//			}
//			if(i != 5)
//				System.out.print("\n" + (char)('A' + i));
//		}
//		System.out.println("\n----------------------------------------------------------------------------------");
//		
//		System.out.println("End");
	}
}