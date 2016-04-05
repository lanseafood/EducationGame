import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


public class Utilities {

	
	
	// Given a user, produces an arraylist indicating which questions have been properly answered.
	// Uses 0-indexing, so Question 1 is at index 0. 
	
	public static ArrayList<Boolean> decodeAnswers(String username) throws SQLException{
		SQLiteJDBC db = new SQLiteJDBC();
		String answered = db.get_Question_Data(username);
		
		//Currently, the rightmost-bit is Question 1: 
		
		ArrayList<Boolean> ans = new ArrayList<Boolean>();
		int i = username.length() - 1;
		while (i >= 0){
			Boolean a = answered.charAt(i) == '1' ? true : false;
			ans.add(a);
			i--;
		}
		
		return ans;
	}
	
	
	// Produces and sends a bit-string corresponding to the answered questions. 
	// The ====rightmost bit==== corresponds to Question 1. 
	public void updateAnswers(String user, ArrayList<Boolean> answered) throws SQLException{
		String answer = "";
		Iterator<Boolean> iter = answered.iterator();
		while (iter.hasNext()){
			String prepend = iter.next() == true ? "1" : "0";
			answer = prepend + answer;
		}
		
		SQLiteJDBC db = new SQLiteJDBC();
		db.set_Question_Data(user, answer);
	}
}
