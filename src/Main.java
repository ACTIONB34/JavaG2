import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		Connection conn = dbc.getConnection();
		
		Scanner myObj = new Scanner(System.in);
		int movieChoice;
		int scheduleChoice;
		int numberOfSeats;
		ArrayList<String> seatChoice = new ArrayList<String>();
		String guestName;
		String confirmChoice;
		String dateChoice;
		
		System.out.println("Input Date: ");
		dateChoice = myObj.nextLine();
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Movie #\t\tTitle\t\tDirector");
		Date date = new Date(2020, 4, 1);
		dbc.viewSched(date, "Cinema", "Movie");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Choose Movie #: ");
		movieChoice = myObj.nextInt();
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("**input 0 to go back to last page");
		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
		System.out.println("Director:\tMovieDirector");							//Needs conversion to actual class
		System.out.println("Schedule #\t\tCinema #\t\tDate & Time Slot");
		for(int i = 1; i <= 3; i++) {		//Dapat mu end sa number of items
			System.out.print(i + "\t\t\tCinema " + i + "\t\tDate & Time Slot" + i + "\n");	//Needs conversion to actual class
		}
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Choose Schedule #: ");
		scheduleChoice = myObj.nextInt();
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("# of seats");
		System.out.println("\tKid(s): ");
		numberOfSeats = myObj.nextInt();
		System.out.println("\tRegular(s): ");
		numberOfSeats += myObj.nextInt();
		System.out.println("\tSenior(s): ");
		numberOfSeats += myObj.nextInt();
		myObj.nextLine();
		System.out.println("--------------------------------------------------------------------------------");
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
		System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
		System.out.println("Seats");
		for(int i = 0; i <= 5; i++) {											//Prints the seating table
			for(int j = 0; j <= 8; j++) {										//No reserved seats yet
				if(i == 0)
					System.out.print(j + "\t");
				else {
					System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
					if(j == 7)
						break;
				}
			}
			if(i != 5)
				System.out.print("\n" + (char)('A' + i));
		}
		System.out.println("\n--------------------------------------------------------------------------------");
		for(int i = 0; i < numberOfSeats; i++) {
			System.out.println("Seat " + (i + 1) + ": ");
			seatChoice.add(myObj.nextLine());
		}
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("**input 0 to go back to last page");
		System.out.println("Title\tMovieTitle");								//Needs conversion to actual class
		System.out.println("Date & Time Slot#");								//Needs conversion to actual class
		System.out.println("Cinema#");											//Needs conversion to actual class
		System.out.println("Seat #: ");
		for(String i : seatChoice) {
			System.out.print(i + ", ");
		}
		System.out.println("\nGuest name: ");
		guestName = myObj.nextLine();											//Gets name, dummy variable
		System.out.println("Confirm (Y/N): ");
		confirmChoice = myObj.nextLine();										//Lacking function if confirmation == N
		System.out.println("----------------------------------------------------------------------------------");
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("CHECKOUT");
		System.out.println("Subtotal:\t\t# of Tickets\t\tPrice");
		System.out.println("\tKid(s):\t\t#ofKid(s)\tx\tEachPrice");				//Needs conversion to actual class
		System.out.println("\tRegular(s):\t#ofRegular(s)\tx\tEachPrice");		//Needs conversion to actual class
		System.out.println("\tSenior(s):\t#ofSenior(s)\tx\tEachPrice");			//Needs conversion to actual class
		System.out.println("Total: TotalPrice");								//Needs conversion to actual class
		System.out.println("----------------------------------------------------------------------------------");
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("Seat # ");
		for(String i : seatChoice) {
			System.out.print(i);
			if(i != null)
				System.out.print(", ");
		}
		System.out.println("\n----------------------------------------------------------------------------------");
		System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
		System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
		System.out.println("Seats");
		for(int i = 0; i <= 5; i++) {											//Prints the seating table
			for(int j = 0; j <= 8; j++) {										//No reserved seats yet
				if(i == 0)
					System.out.print(j + "\t");
				else {
					System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
					if(j == 7)
						break;
				}
			}
			if(i != 5)
				System.out.print("\n" + (char)('A' + i));
		}
		System.out.println("\n----------------------------------------------------------------------------------");
		
		System.out.println("End");
	}
}