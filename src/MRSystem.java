import java.sql.Connection;

public class MRSystem {
	public static void main(String[] args){
		DBConnection dbc = new DBConnection();
		Connection conn = dbc.getConnection();
		dbc.excecuteAndPrint("select movie_id as id, title, length, description from movies","title", 50, "title", "length", "id", "description");
	}
}
