import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static void printSeats(Seat[] seats) {
		int index = 0; 
		for(int i = 0; i < Seat.ROW; i++) {
			for(int j= 0; j < Seat.COL; j++) {
				System.out.print(seats[index].getSeatNum() + "\t");
				index++;
			}
			System.out.println();
		}
	}
	
	private static void updateSeats(ArrayList<String> reservedSeats, Seat[] seats) {
		for(String curr:reservedSeats) {
			for(Seat seat: seats) {
				if(seat.getSeatNum().equalsIgnoreCase(curr));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		ResultSet rs;
		
		Scanner scan = new Scanner(System.in);

		
		init: while(true) {
			Reservation reservation = new Reservation();
			ArrayList<Guest> guests = new ArrayList<Guest>();
			ArrayList<Seat> seatChoices = new ArrayList<Seat>();		
			ArrayList<String> reservedSeats = new ArrayList<String>();
			
			int movieChoice = -1;
			int scheduleChoice = -1;
			int numberOfSeats = 40;
			int numberOfSeatsToReserve = 0;
			int numOfKids;
			int numOfRegulars;
			int numOfSeniors;
			int tries = 0;
			int guestCount = 0;
			int totalAmount = 0;
			String tempDate = null;
			String customerName = "";
			Date dateToReserve = new Date(Date.UTC(0, 0, 0, 0, 0, 0));
			Seat[] currentSeats = Seat.seats;
			
			String confirmChoice = null;
			String dateChoice = null;

			
			/*===========+===========+===========+===========+===========+===========*/
			/*					  MAKE USER SELECT FROM MOVIE LIST					 */
			/*===========+===========+===========+===========+===========+===========*/
			
			start:while(true) {
				System.out.println("===========+===========\n" + 
								   "\tWELCOME\n" + 
								   "===========+===========\n");	
				System.out.println("SELECT A MOVIE");
				System.out.println("--------------------------------------------------------------------------------------------------------------");
				rs = dbc.excecuteAndPrint("select movie_id as id, title, length, description, rating from movies","title",
					 44, "id", "title", "length", "description");
				System.out.println("--------------------------------------------------------------------------------------------------------------");
				System.out.print("YOUR CHOICE: ");
				movieChoice = Integer.parseInt(scan.nextLine());
				
				tries = 0;
				getMovie: while(true) {
					try {
						rs.beforeFirst();
						if(rs.next()) {
							reservation.getSchedule().setMovie(new Movie(rs.getInt("id"), rs.getString("title"), 
															rs.getString("description"), rs.getInt("length"),
															rs.getString("rating")));
						}
						break getMovie;
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("ERROR: Invalid movie id");
						tries++;
						if(tries >= 3) {
							System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
							break start;
						}
					}
				}
					
				/*===========+===========+===========+===========+===========+===========*/
				/*						    ASKS DATE FROM USER							 */
				/*===========+===========+===========+===========+===========+===========*/
					
				System.out.println("--------------------------------------------------------------------------------");
				System.out.print("Date(yyyy-mm-dd): ");
				tempDate = scan.nextLine();
				String[] dateArr = tempDate.split("-");
				
				try {
					dateToReserve = new Date(Integer.parseInt(dateArr[0]) - 1900, Integer.parseInt(dateArr[1]) -1, Integer.parseInt(dateArr[1]) - 1);
				}catch(NumberFormatException e) {
					e.printStackTrace();
					System.out.println("ERROR: Invalid date");
				}	
				System.out.println("--------------------------------------------------------------------------------");
				rs = dbc.viewSched(dateToReserve, movieChoice, "Sched#", "Cinema", "Time Showing", "Movie");
				System.out.println("--------------------------------------------------------------------------------");
				
				System.out.print("Choose Sched#: ");
				try {
					if(rs.next()) {
						scheduleChoice = Integer.parseInt(scan.nextLine());
						do{
							if(rs.getInt("cid") == scheduleChoice) {
								reservation.getSchedule().getCinema().setCinemaId(rs.getInt("cid"));
								reservation.getSchedule().getCinema().setCinemaNum(rs.getInt("Cinema"));
							}
						}while(rs.next());
					}else {
						System.out.println("Exiting...");
						break start;
					}
					
				}catch(NumberFormatException e) {
					e.printStackTrace();
					System.out.println("ERROR: Invalid no. of seats");
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("ERROR: Having problems with the database. Exiting...");
					break start;
				}
				
				reservedSeats = dbc.getSeats(scheduleChoice, "Seat");
				updateSeats(reservedSeats, currentSeats);
				
		
				System.out.println("--------------------------------------------------------------------------------");
				printSeats(currentSeats);
				System.out.println("--------------------------------------------------------------------------------");
				System.out.print("No. of available seats: " + (numberOfSeats - reservedSeats.size()));
				System.out.println("\n--------------------------------------------------------------------------------");
				System.out.print("No. of seats to reserve: ");
				try {
					numberOfSeatsToReserve = Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {
					e.printStackTrace();
					System.out.println("ERROR: Invalid no. of seats");
				}			
				
				for(int i = 0; i < numberOfSeatsToReserve; i++) {
					System.out.print("Seat #" + (i + 1) + ":\t");
					seatChoices.add(new Seat(scan.nextLine()));
				}
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println("Specify no. guest(s) for each type");
				System.out.println("--------------------------------------------------------------------------------");
				
				specifyGuests: while(true) {
					numOfKids = 0;
					numOfRegulars = 0;
					numOfSeniors = 0;
					try {
						System.out.print("No. of kid(s):\t");
						numOfKids = Integer.parseInt(scan.nextLine());
						numberOfSeats -= numOfKids;
						guestCount += numOfKids;
						for(int m = 0; m < numOfKids; m++) {
							//System.out.println("--------------------------------------------------------------------------------");
							//System.out.print("Name of Customer: ");
							guests.add(new Kid(scan.nextLine()));
							//System.out.println("--------------------------------------------------------------------------------");
						}
						if(guestCount != numberOfSeatsToReserve) {
							System.out.print("No. of regular(s):\t");
							numOfRegulars = Integer.parseInt(scan.nextLine());
							numberOfSeats -= numOfRegulars;								//
							for(int n = 0; n < numOfRegulars; n++) {
								//System.out.println("--------------------------------------------------------------------------------");
								//System.out.print("Name of Customer: ");
								guests.add(new Guest(scan.nextLine()));
								//System.out.println("--------------------------------------------------------------------------------");
							}
							if(guestCount != numberOfSeatsToReserve) {
								System.out.print("No. of senior(s):\t");
								numOfSeniors = Integer.parseInt(scan.nextLine());
								numberOfSeats -= numOfSeniors;
								for(int o = 0; o < numOfSeniors; o++) {
									//System.out.println("--------------------------------------------------------------------------------");
									//System.out.print("Name of Customer: ");
									guests.add(new Senior(scan.nextLine()));
									//System.out.println("--------------------------------------------------------------------------------");
								}
							}
						}
						if(guestCount == numberOfSeatsToReserve) {
							break specifyGuests;
						}else if(guestCount > numberOfSeatsToReserve){
							System.out.println("ERROR: You have enumerated " + (guestCount - numberOfSeatsToReserve) + " more no. of guest(s). Reverting...\n"
									+ "Please input the number of guest for each category again." );
						}else {
							System.out.println("ERROR: You have enumerated " + (numberOfSeatsToReserve - guestCount) + " less no. of guest(s). Reverting...\n"
									+ "Please input the number of guest for each category again." );
						}
					}catch(NumberFormatException e) {
						e.printStackTrace();
						System.out.println("ERROR: Invalid no. guests");
					}
				}				

				
				/*===========+===========+===========+===========+===========+===========*/
				/*						 SAVE TRANSACTION TO DATABASE					 */
				/*===========+===========+===========+===========+===========+===========*/
				
				for(Guest guest: guests) {
					totalAmount += guest.getRate();
				}
				
				System.out.println("===========+===========+===========+===========" + 
								   "\tTRANSACTION SUMMARY\n" + 
								   "===========+===========+===========+===========");
				System.out.println("Category\t\tQuantity\tAmount");
				if(0 < numOfRegulars) {
					System.out.print("Regular\t\t₱" + Guest.REGULAR_RATE + "\t"+ numOfKids +"\t₱ "+ Guest.REGULAR_RATE*numOfRegulars);
				}
				if(0 < numOfKids) {
					System.out.print("Kid\t\t₱ " + Guest.KID_RATE + "\t"+ numOfKids +"\t₱ "+ Guest.KID_RATE*numOfKids);
				}
				if(0 < numOfSeniors) {
					System.out.print("Senior\t\t₱ " + Guest.SENIOR_RATE + "\t"+ numOfKids +"\t₱ "+ Guest.KID_RATE*numOfSeniors);
				}
				System.out.print("Total\t\t\t\t\t\t₱ "+ totalAmount);
				
				
				
				/*===========+===========+===========+===========+===========+===========*/
				/*						    CONFIRM TRANSACTION	?						 */
				/*===========+===========+===========+===========+===========+===========*/
				tries = 0;
				confirm: while(true) {
					System.out.println("--------------------------------------------------------------------------------");
					System.out.print("Proceed with this transaction? (Y/N): ");
					confirmChoice = scan.nextLine();
					System.out.println("--------------------------------------------------------------------------------");
					
					if(confirmChoice.equalsIgnoreCase("y")) {
						System.out.println("--------------------------------------------------------------------------------");
						System.out.print("Name of Customer: ");
						customerName = scan.nextLine();
						for(Guest guest: guests) {
							guest.setName(customerName);
							reservation.setGuest(guest);
							dbc.insertToDB(reservation);
						}
						System.out.println("DONE\n\n\n");
						
						break confirm;
					}else if(confirmChoice.equalsIgnoreCase("n")) {
						System.out.println("Canceling transaction...");
						break start;
					}else {
						System.out.println("Invalid...");
						tries++;
						if(tries >= 3) {
							System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
							break start;
						}
						continue;							//optional
					}
				}				
			}
			
			
			
			
//			System.out.println("--------------------------------------------------------------------------------");
//			System.out.println("**input 0 to go back to last page");
//			System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//			System.out.println("Director:\tMovieDirector");							//Needs conversion to actual class
//			System.out.println("Schedule #\t\tCinema #\t\tDate & Time Slot");
//			for(int i = 1; i <= 3; i++) {		//Dapat mu end sa number of items
//				System.out.print(i + "\t\t\tCinema " + i + "\t\tDate & Time Slot" + i + "\n");	//Needs conversion to actual class
//			}
//			System.out.println("--------------------------------------------------------------------------------");
//			System.out.println("Choose Schedule #: ");
//			scheduleChoice = myObj.nextInt();
//			
//			System.out.println("--------------------------------------------------------------------------------");
//			System.out.println("# of seats");
//			System.out.println("\tKid(s): ");
//			numberOfSeats = myObj.nextInt();
//			System.out.println("\tRegular(s): ");
//			numberOfSeats += myObj.nextInt();
//			System.out.println("\tSenior(s): ");
//			numberOfSeats += myObj.nextInt();
//			myObj.nextLine();
//			System.out.println("--------------------------------------------------------------------------------");
//			
//			System.out.println("--------------------------------------------------------------------------------");
//			System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//			System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
//			System.out.println("Seats");
//			for(int i = 0; i <= 5; i++) {											//Prints the seating table
//				for(int j = 0; j <= 8; j++) {										//No reserved seats yet
//					if(i == 0)
//						System.out.print(j + "\t");
//					else {
//						System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
//						if(j == 7)
//							break;
//					}
//				}
//				if(i != 5)
//					System.out.print("\n" + (char)('A' + i));
//			}
//			System.out.println("\n--------------------------------------------------------------------------------");
//			for(int i = 0; i < numberOfSeats; i++) {
//				System.out.println("Seat " + (i + 1) + ": ");
//				seatChoice.add(myObj.nextLine());
//			}
//			
//			System.out.println("----------------------------------------------------------------------------------");
//			System.out.println("**input 0 to go back to last page");
//			System.out.println("Title\tMovieTitle");								//Needs conversion to actual class
//			System.out.println("Date & Time Slot#");								//Needs conversion to actual class
//			System.out.println("Cinema#");											//Needs conversion to actual class
//			System.out.println("Seat #: ");
//			for(String i : seatChoice) {
//				System.out.print(i + ", ");
//			}
//			System.out.println("\nGuest name: ");
//			guestName = myObj.nextLine();											//Gets name, dummy variable
//			System.out.println("Confirm (Y/N): ");
//			confirmChoice = myObj.nextLine();										//Lacking function if confirmation == N
//			System.out.println("----------------------------------------------------------------------------------");
//			
//			System.out.println("----------------------------------------------------------------------------------");
//			System.out.println("CHECKOUT");
//			System.out.println("Subtotal:\t\t# of Tickets\t\tPrice");
//			System.out.println("\tKid(s):\t\t#ofKid(s)\tx\tEachPrice");				//Needs conversion to actual class
//			System.out.println("\tRegular(s):\t#ofRegular(s)\tx\tEachPrice");		//Needs conversion to actual class
//			System.out.println("\tSenior(s):\t#ofSenior(s)\tx\tEachPrice");			//Needs conversion to actual class
//			System.out.println("Total: TotalPrice");								//Needs conversion to actual class
//			System.out.println("----------------------------------------------------------------------------------");
//			
//			System.out.println("----------------------------------------------------------------------------------");
//			System.out.println("Seat # ");
//			for(String i : seatChoice) {
//				System.out.print(i);
//				if(i != null)
//					System.out.print(", ");
//			}
//			System.out.println("\n----------------------------------------------------------------------------------");
//			System.out.println("Title:\t\tMovieTitle");								//Needs conversion to actual class
//			System.out.println("Cinema #\tDate & Time Slot #");						//Needs conversion to actual class
//			System.out.println("Seats");
//			for(int i = 0; i <= 5; i++) {											//Prints the seating table
//				for(int j = 0; j <= 8; j++) {										//No reserved seats yet
//					if(i == 0)
//						System.out.print(j + "\t");
//					else {
//						System.out.print("\t" + (char)('A' + (i - 1)) + (j + 1));
//						if(j == 7)
//							break;
//					}
//				}
//				if(i != 5)
//					System.out.print("\n" + (char)('A' + i));
//			}
//			System.out.println("\n----------------------------------------------------------------------------------");
//			
//			System.out.println("End");
		}
		
	}
}