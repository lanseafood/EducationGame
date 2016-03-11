import java.util.*;
import java.sql.*;

public class SQLiteJDBC
{
  /* Private global varaible for DB connection */
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
    System.out.println("Opened database successfully");
  }
  
  /* Private method for creating the tables if they do not exist (clean build) */
  private void create_Tables() throws SQLException {
	Statement stmt = c.createStatement();
	String sql =
		"CREATE TABLE IF NOT EXISTS PROFILES (" +
		"NAME VARCHAR(20) PRIMARY KEY NOT NULL, " +
		"QUESTIONS VARCHAR(20) NOT NULL, " + 
		"INTERESTS VARCHAR(20) NOT NULL)";
	stmt.executeUpdate(sql);
	sql =
		"CREATE TABLE IF NOT EXISTS QUESTIONS (" +
		"ECOLOGY_ID INT NOT NULL, " +
		"QUESTION VARCHAR(256) NOT NULL, " + 
		"ANSWER VARCHAR(64) NOT NULL)";
	stmt.executeUpdate(sql);
	sql =
		"CREATE TABLE IF NOT EXISTS ECOLOGY (" +
		"ID INT PRIMARY KEY NOT NULL, " +
		"NAME VARCHAR(32) NOT NULL, " + 
		"TROPIC INT NOT NULL)";
	stmt.executeUpdate(sql);	
	stmt.close();
  }
  
  /* Clear all tables (debug only) */
  public void clear_Tables() throws SQLException {
	Statement stmt = c.createStatement();
	stmt.executeUpdate("DELETE FROM PROFILES;");
	stmt.executeUpdate("DELETE FROM QUESTIONS;");
	stmt.executeUpdate("DELETE FROM ECOLOGY;");	
	stmt.close();
  }
  
  /* // PROFILES Table Methods // */
  
  /* Add a user to the table */
  public void add_User(String name) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO PROFILES " +
		"VALUES ('%s', '00000000000000000000', '00000000000000000000');", name);
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
  public void add_QA(int ecology_id, String question, String answer) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO QUESTIONS " +
		"VALUES (%d, '%s', '%s');", ecology_id, question, answer);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  /* Prints all stored questions and answers (debug only) */
  public void print_QAs() throws SQLException {
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT ROWID, * FROM QUESTIONS;");
	while (rs.next()) {		
		int rowid = rs.getInt("rowid");
		int eid = rs.getInt("ecology_id");
		String q = rs.getString("question");
		String a = rs.getString("answer");
		System.out.printf("%d: %s (%d)\n\t%s\n", rowid, q, eid, a);
	}
	rs.close();
	stmt.close();
  }
  
  /* Fetches a single question/answer pair from a particular row in the table */
  public Question get_QA(int row) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"SELECT ROWID, * FROM QUESTIONS " +
		"WHERE ROWID=%d;", row);
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	int rowid = rs.getInt("rowid");
	int id = rs.getInt("ecology_id");
	String q = rs.getString("question");
	String a = rs.getString("answer");
	rs.close();
	stmt.close();
	Question out = (q == null) ? null : new Question(rowid, id, q, a);
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
		"SELECT ROWID, * FROM QUESTIONS " +
		"WHERE ECOLOGY_ID=%d;", ecology_id);
	rs = stmt.executeQuery(sql);
	while (rs.next()) {
		int rowid = rs.getInt("rowid");
		int eid = rs.getInt("ecology_id");
		String q = rs.getString("question");
		String a = rs.getString("answer");
		out.add(new Question(rowid, eid, q, a));
	}
	rs.close();
	stmt.close();
	return out;
  }
  
  /* Remove a question/answer pair from the table */
  public void remove_QA(int row) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM QUESTIONS " +
		"WHERE ROWID=%d;", row);
	stmt.executeUpdate(sql);
	stmt.close();
  }
  
  
  
  /* // ECOLOGY Table Methods // */
  
  /* Add a new ecology to the table */
  public void add_Ecology(int ecology_id, String name, int tropic) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"INSERT INTO ECOLOGY " +
		"VALUES (%d, '%s', %d);", ecology_id, name, tropic);
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
  
  /* Fetches the troipic level number of a species in the table */
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
  
  /* Remove a question/answer pair from the table */
  public void remove_Ecology(int id) throws SQLException {
	Statement stmt = c.createStatement();
	String sql = String.format(
		"DELETE FROM ECOLOGY " +
		"WHERE ID=%d;", id);
	stmt.executeUpdate(sql);
	stmt.close();
  }
}