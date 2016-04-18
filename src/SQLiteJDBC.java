import java.util.*;
import java.sql.*;

public class SQLiteJDBC
{
  /* Private global variable for DB connection */
  private Connection c;
  
  /* Constructor for access, update, and use of the DB */
  public SQLiteJDBC() {
    c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:gamedata.db");
	  create_Tables();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    //System.out.println("Opened database successfully");
  }
  
  /* Private method for creating the tables if they do not exist (clean build) */
  private void create_Tables() throws SQLException {
	Statement stmt = c.createStatement();
	String sql =
		"CREATE TABLE IF NOT EXISTS PROFILES (" +
		"NAME VARCHAR(24) NOT NULL, " +
		"ANIMALS VARCHAR(14) NOT NULL, " + 
		"QUESTIONS VARCHAR(52) NOT NULL, " + 
		"TITLE INT NOT NULL, " + 		
		"INTERESTS VARCHAR(20) NOT NULL, " + 
		"PRIMARY KEY (NAME))";
	stmt.executeUpdate(sql);
	sql =
		"CREATE TABLE IF NOT EXISTS QUESTIONS (" +
		"QA_ID INT PRIMARY KEY, " +
		"ECOLOGY_ID INT NOT NULL, " +
		"QUESTION VARCHAR(256) NOT NULL, " + 
		"ANSWER VARCHAR(64) NOT NULL)";
	stmt.executeUpdate(sql);
	sql =
		"CREATE TABLE IF NOT EXISTS ECOLOGY (" +
		"ID INT PRIMARY KEY, " +
		"NAME VARCHAR(32) NOT NULL, " + 
		"TROPIC INT NOT NULL)";
	stmt.executeUpdate(sql);
	sql =
		"CREATE TABLE IF NOT EXISTS TITLES (" +
		"ID INT PRIMARY KEY NOT NULL, " +
		"TITLE VARCHAR(64) NOT NULL)";
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Clear all tables (debug only) */
  public void clear_Tables() throws SQLException {
	Statement stmt = c.createStatement();
	stmt.executeUpdate("DELETE FROM PROFILES;");
	stmt.executeUpdate("DELETE FROM QUESTIONS;");
	stmt.executeUpdate("DELETE FROM ECOLOGY;");
	stmt.executeUpdate("DELETE FROM TITLES;");	
	stmt.close();
  }
  
  /* // PROFILES Table Methods // */
  
  /* Add a user to the table */
  public void add_User(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO PROFILES " +
		"VALUES ('%s', '00000000000000', " +
	    "'0000000000000000000000000000000000000000000000000000', " +
		"0, '00000000000000000000');", name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Returns an array list of the names of all users in the table */
  public ArrayList<String> get_Users() throws SQLException {
	Statement stmt = c.createStatement();	
	// Get count of rows
	ResultSet rs = stmt.executeQuery("SELECT COUNT(NAME) FROM PROFILES;");
	rs.next(); 
	int count = rs.getInt("count(name)");
	rs.close();	
	// Create array of count size and read names into it
	ArrayList<String> out = new ArrayList<String>(count);
	rs = stmt.executeQuery("SELECT NAME FROM PROFILES");
	while (rs.next()) {
		out.add(rs.getString("name"));
	}
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Fetches the question data of a user in the table */
  public String get_Animal_Data(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT ANIMALS FROM PROFILES " +
		"WHERE NAME='%s';", name);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String out = rs.getString("animals");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Updates the question data of a user in the table */
  public void set_Animal_Data(String name, String data) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"UPDATE PROFILES " +
		"SET ANIMALS='%s' " +
		"WHERE NAME='%s';", data, name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Fetches the question data of a user in the table */
  public String get_Question_Data(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT QUESTIONS FROM PROFILES " +
		"WHERE NAME='%s';", name);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String out = rs.getString("questions");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Updates the question data of a user in the table */
  public void set_Question_Data(String name, String data) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"UPDATE PROFILES " +
		"SET QUESTIONS='%s' " +
		"WHERE NAME='%s';", data, name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Fetches the question data of a user in the table */
  public int get_User_Title(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT TITLE FROM PROFILES " +
		"WHERE NAME='%s';", name);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	int out = rs.getInt("title");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Updates the question data of a user in the table */
  public void set_User_Title(String name, int data) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"UPDATE PROFILES " +
		"SET TITLE=%d " +
		"WHERE NAME='%s';", data, name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Fetches the interest data of a user in the table */
  public String get_Interest_Data(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT INTERESTS FROM PROFILES " +
		"WHERE NAME='%s';", name);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String out = rs.getString("interests");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Updates the interest data of a user in the table */
  public void set_Interest_Data(String name, String data) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"UPDATE PROFILES " +
		"SET INTERESTS='%s' " +
		"WHERE NAME='%s';", data, name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Remove a user from the table */
  public void remove_User(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM PROFILES " +
		"WHERE NAME='%s';", name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  
  
  /* // QUESTIONS Table Methods // */
  
  /* Add a new question/answer pair to the table */
  public void add_QA(int q_id, int e_id, String question, String answer) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO QUESTIONS " +
		"VALUES (%d, %d, '%s', '%s');", q_id, e_id, question, answer);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Prints all stored questions and answers (debug only) */
  public void print_QAs() throws SQLException {
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * FROM QUESTIONS;");
	while (rs.next()) {		
		int qa_id = rs.getInt("qa_id");
		int eid = rs.getInt("ecology_id");
		String q = rs.getString("question");
		String a = rs.getString("answer");
		System.out.printf("%d: %s (%d)\n\t%s\n", qa_id, q, eid, a);
	}
	rs.close();
	stmt.close();
  }
  
  /* Fetches a single question/answer pair from a particular row in the table */
  public Question get_QA(int qa_id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT * FROM QUESTIONS " +
		"WHERE QA_ID=%d;", qa_id);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	int q_id = rs.getInt("qa_id");
	int e_id = rs.getInt("ecology_id");
	String q = rs.getString("question");
	String a = rs.getString("answer");
	rs.close();
	stmt.close();
	Question out = (q == null) ? null : new Question(q_id, e_id, q, a);
	return out;
  }
  
  /* Returns an array list of Q/A pairs for a particular ID in the table */
  public ArrayList<Question> get_QAs(int ecology_id) throws SQLException {
	Statement stmt = c.createStatement();	
	// Get count of rows	
	String sql = String.format(
		"SELECT COUNT(QUESTION) FROM QUESTIONS " +
		"WHERE ECOLOGY_ID=%d;", ecology_id);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next(); 
	int count = rs.getInt("count(question)");
	rs.close();
	// Create array of count size and read Q/As into it
	ArrayList<Question> out = new ArrayList<Question>(count);	
	sql = String.format(
		"SELECT * FROM QUESTIONS " +
		"WHERE ECOLOGY_ID=%d;", ecology_id);
	rs = stmt.executeQuery(sql);
	while (rs.next()) {
		int qa_id = rs.getInt("qa_id");
		int eid = rs.getInt("ecology_id");
		String q = rs.getString("question");
		String a = rs.getString("answer");
		out.add(new Question(qa_id, eid, q, a));
	}
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Remove a question/answer pair from the table */
  public void remove_QA(int qa_id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM QUESTIONS " +
		"WHERE QA_ID=%d;", qa_id);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  
  
  /* // ECOLOGY Table Methods // */
  
  /* Add a new ecology to the table */
  public void add_Ecology(int e_id, String name, int tropic) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO ECOLOGY " +
		"VALUES (%d, '%s', %d);", e_id, name, tropic);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Fetches the ecology id of a species in the table */
  public int get_Ecology_ID(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT ID FROM ECOLOGY " +
		"WHERE NAME='%s';", name);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	int out = rs.getInt("id");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Fetches the ecology name of a species in the table */
  public String get_Ecology_Name(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT NAME FROM ECOLOGY " +
		"WHERE ID=%d;", id);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String out = rs.getString("name");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Fetches the tropic level number of a species in the table */
  public int get_Ecology_Tropic(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT TROPIC FROM ECOLOGY " +
		"WHERE ID=%d;", id);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	int out = rs.getInt("tropic");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Prints all stored ecologies (debug only) */
  public void print_Ecology() throws SQLException {
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * FROM ECOLOGY;");
	while (rs.next()) {		
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int tropic = rs.getInt("tropic");
		System.out.printf("%d: %s (%d)\n", id, name, tropic);
	}
	rs.close();
	stmt.close();
  }
  
  /* Returns an arraylist of all animal names in the db.*/
  public ArrayList<String> retrieve_Ecology() throws SQLException {
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * FROM ECOLOGY;");
	ArrayList<String> ans = new ArrayList<String>();
	while (rs.next()) {		
		String name = rs.getString("name");
		ans.add(name);
	}
	rs.close();
	stmt.close();
	return ans;
  }
  
  /* Remove a question/answer pair from the table */
  public void remove_Ecology(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM ECOLOGY " +
		"WHERE ID=%d;", id);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  
  
  /* // TITLE Table Methods // */
  
  /* Add a new title to the table */
  public void add_Title(int id, String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO TITLES " +
		"VALUES (%d, '%s');", id, name);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Fetches a title by its id in the table */
  public String get_Title(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT NAME FROM TITLES " +
		"WHERE ID=%d;", id);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String out = rs.getString("name");
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Remove a title from the table */
  public void remove_Title(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM TITLES " +
		"WHERE ID=%d;", id);
	stmt.executeUpdate(sql);
	stmt.close();
  }
}