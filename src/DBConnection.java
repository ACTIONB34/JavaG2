import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection{
	
	private Connection conn;
	private Statement smt;
	private ResultSet rs;
	
	public DBConnection() {

		this.conn = null;
		this.smt = null;
		this.rs = null;
		
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/mrsdb?useUnicode=true&"
		    		+ "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
		    		   "root", "awsys+123");
		    
		    System.out.println("Connection established...\n\n");
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public ResultSet excecuteAndPrint(String q, String lengthyColumn, int maxLength, String... columns) {
		try {
			this.smt = this.conn.createStatement();
		    this.rs = smt.executeQuery(q);
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
				    	System.out.print(rs.getString(column) + "\t");
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
	
	
}
