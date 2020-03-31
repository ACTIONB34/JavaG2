import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MRSystem {
	public static void main(String[] args){
		Connection conn = null;
		Statement smt = null;
		ResultSet rs = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_clinic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
		    		   "root", "awsys+123");

		    smt = conn.createStatement();
		    rs = smt.executeQuery("select * from pets;");
		    
		    while(rs.next()) {
		    	System.out.println(rs.getString(3));
		    }
		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
