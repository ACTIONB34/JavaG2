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
		for(String curr: reservedSeats) {
			for(int i = 0; i < seats.length; i++) {
				if(seats[i].getSeatNum().equalsIgnoreCase(curr)) {
					seats[i].setSeatNum("XX");
				}
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
			int inputSeatCount = 0;
			String tempDate = null;
			String customerName = "";
			Date dateToReserve = new Date(Date.UTC(0, 0, 0, 0, 0, 0));
			Seat[] currentSeats = Seat.seats;
			String seatTemp = "";
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
				
				tries = 0;
				getMovie: while(true) {
					try {
						movieChoice = Integer.parseInt(scan.nextLine());
						
						if(rs.next()) {
							do{
								if(rs.getInt("id") == movieChoice) {
									reservation.setSchedule(new Schedule());
									reservation.getSchedule().setMovie(new Movie(rs.getInt("id"), rs.getString("title"), 
																	   rs.getString("description"), rs.getInt("length"),
																	   rs.getString("rating")));

									break getMovie;
								}
							}while(rs.next());
						}
					}catch(NumberFormatException e) {
						System.out.println("ERROR: Invalid movie id");
						tries++;
						if(tries >= 3) {
							System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
							break start;
						}
					} catch (SQLException e) {
						e.printStackTrace();
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
					dateToReserve = new Date(Integer.parseInt(dateArr[0]) - 1900, Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]) + 1);
					System.out.println(dateToReserve);
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
							if(rs.getInt("Sched#") == scheduleChoice) {
								reservation.getSchedule().setSchedID(scheduleChoice);
								reservation.getSchedule().setCinema(new Cinema());
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
				tries = 0;
				
				getNumToReserve: while(true) {
					try {
						System.out.print("No. of seats to reserve: ");						
						numberOfSeatsToReserve = Integer.parseInt(scan.nextLine());
						if(numberOfSeatsToReserve < 1) {
							System.out.println("ERROR: Input a number greater than 0.");
							if(tries >= 3) {
								tries++;
								System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
								break start;
							}
						}else if(numberOfSeatsToReserve > numberOfSeats){
							System.out.println("ERROR: The number of seats you entered exceeds the remaining available seats. [Available Seats: " + numberOfSeats + "]");
							if(tries >= 3) {
								tries++;
								System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
								break start;
							}
						}else {
							break getNumToReserve;
						}
					}catch(NumberFormatException e) {
						e.printStackTrace();
						System.out.println("ERROR: Invalid no. of seats");
						tries++;
						if(tries >= 3) {
							System.out.println("You have given invalid input thrice(3). Exiting...\n\n\n\n\n");
							break start;
						}
					}	
				}		
				
				for(int i = 0; i < numberOfSeatsToReserve; i++) {
					System.out.print("Seat #" + (i + 1) + ": ");
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
							guests.add(new Kid(customerName));
						}
						if(guestCount != numberOfSeatsToReserve) {
							System.out.print("No. of regular(s):\t");
							numOfRegulars = Integer.parseInt(scan.nextLine());
							numberOfSeats -= numOfRegulars;	
							guestCount += numOfRegulars;
							for(int n = 0; n < numOfRegulars; n++) {
								guests.add(new Guest(customerName));
							}
							if(guestCount != numberOfSeatsToReserve) {
								System.out.print("No. of senior(s):\t");
								numOfSeniors = Integer.parseInt(scan.nextLine());
								numberOfSeats -= numOfSeniors;
								guestCount += numOfSeniors;
								for(int o = 0; o < numOfSeniors; o++) {
									guests.add(new Senior(customerName));
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
					System.out.println("Senior\t\t" + Guest.SENIOR_RATE + "\t"+ numOfSeniors +"\t\t"+ Guest.KID_RATE*numOfSeniors);
				}
				System.out.println("Total:\t\t\t\t\t"+ totalAmount);
				
				
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
						
						for(int i = 0; i < guests.size(); i++) {
							Guest guest = guests.get(i);
							guest.setName(customerName);
							reservation.setSeat(seatChoices.get(i));
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
		}		
	}
}