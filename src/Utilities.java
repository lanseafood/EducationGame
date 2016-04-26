import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;


public class Utilities {

	static int big_width = 300;
	static int small_width = 75;
	
	static int west_width = 350;
	static int east_width = 350;
	
	// Given a user, produces an arraylist indicating which questions have been properly answered.
	// Uses 0-indexing, so Question 1 is at index 0. 
	
	public static void saveGame(GameScreen p){
		try {
			Utilities.updateAnswers(p.username, p.answeredIDs);
		} catch (SQLException e) {
			System.out.println("Failed to save game.");
			e.printStackTrace();
			return;
		}
	}
	
	public static void loadGame(GameScreen p, String username){
		try {
			p.answeredIDs = Utilities.decodeAnswers(username);
			System.out.println(p.answeredIDs);
			HashMap<String, Boolean> answered = new HashMap<String, Boolean>(); 
			Iterator<Integer> qIDs = p.answeredIDs.keySet().iterator();
			SQLiteJDBC db = new SQLiteJDBC();
			//System.out.println(p.answeredIDs.size());
			while (qIDs.hasNext()){
				Integer i = qIDs.next();
				//System.out.println(i);
				Question question = db.get_QA(i);
				if (question == null){
					return;
				}
				String q = question.get_Question();
				Boolean qAnsd = p.answeredIDs.get(i);
				answered.put(q, qAnsd);
			}
			
			p.answered = answered != null ? answered : new HashMap<String, Boolean>();
			p.correctlyPlacedAnimals = new TreeSet<String>();
			p.finishedAnimals = new TreeSet<String>();
			
			ArrayList<String> animals = db.retrieve_Ecology();
			Iterator<String> anIter = animals.iterator();
			while (anIter.hasNext()){
				String animalName = anIter.next();
				if (Utilities.checkAnimalCleared(p, animalName)){
					p.correctlyPlacedAnimals.add(animalName);
					p.finishedAnimals.add(animalName);
				}
			}
			
			
		} catch (SQLException e) {
			p.answeredIDs = new HashMap<Integer, Boolean>();
			p.answered = new HashMap<String, Boolean>();
			
			System.out.println("Failed to load game.");
			e.printStackTrace();
			return;
		}
	}
	
	public static boolean checkAnimalCleared(GameScreen p, String animal){
		SQLiteJDBC db = new SQLiteJDBC();
		
		try {
			int id = db.get_Ecology_ID(animal);
			ArrayList<Question> qs = db.get_QAs(id);
			Iterator<Question> iter = qs.iterator();
			while (iter.hasNext()){
				Question q = iter.next();
				String question = q.get_Question();
				if (question == null){
					System.out.println("wow");
					System.out.flush();
				}
				if (p.answered.get(question) == null || p.answered.get(question) == false)
					return false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
	
	public static HashMap<Integer, Boolean> decodeAnswers(String username) throws SQLException{
		SQLiteJDBC db = new SQLiteJDBC();
		String answered = db.get_Question_Data(username);
		
		HashMap<Integer, Boolean> ans = new HashMap<Integer, Boolean>();
		int i = 0;
		while (i < answered.length()){
			Boolean a = answered.charAt(i) == '1' ? true : false;
			ans.put(i+1, a);
			i++;
		}
		
		return ans;
	}
	
	
	// Produces and sends a bit-string corresponding to the answered questions. 
	// The ====left bit==== corresponds to Question 1. 
	public static String getDataString(String user, HashMap<Integer, Boolean> answered) throws SQLException{
		
		Iterator<Integer> iter = answered.keySet().iterator();

		SQLiteJDBC db = new SQLiteJDBC();
		int count = db.get_Num_QAs();
		
		
		char[] vals = new char[count];
		
		while (iter.hasNext()){
			Integer qNum = iter.next();
			if (answered.get(qNum) == true){
				vals[qNum-1] = 1;
			}
			
		}
		
		int i = 0; 
		while (i < count){
			// for proper utf-16 values
			if (vals[i] == 0){
				vals[i] = '0';
			} else {
				vals[i] = '1';
			}
		
			i++;
		}
		
		return String.valueOf(vals);
		
		
	}
	
	public static void updateAnswers(String user, HashMap<Integer, Boolean> answered) throws SQLException{
		
		Iterator<Integer> iter = answered.keySet().iterator();

		SQLiteJDBC db = new SQLiteJDBC();
		int count = db.get_Num_QAs();
		
		
		char[] vals = new char[count];
		
		while (iter.hasNext()){
			Integer qNum = iter.next();
			if (answered.get(qNum) == true){
				vals[qNum-1] = 1;
			}
			
		}
		
		int i = 0; 
		while (i < count){
			// for proper utf-16 values
			if (vals[i] == 0){
				vals[i] = '0';
			} else {
				vals[i] = '1';
			}
		
			i++;
		}
		
		String answer = String.valueOf(vals);
		
		db.set_Question_Data(user, answer);
	}
	
	
    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
    	// Create a new font with the desired size
    	Font newFont = g.getFont().deriveFont(size);
    	g.setFont(newFont);
    	// Find the size of string s in font f in the current Graphics context g.
    	FontMetrics fm = g.getFontMetrics();
    	java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

    	int textHeight = (int) (rect.getHeight());
    	int textWidth = (int) (rect.getWidth());

    	// Find the top left and right corner
    	int cornerX = x - (textWidth / 2);
    	int cornerY = y - (textHeight / 2) + fm.getAscent();

    	g.drawString(text, cornerX, cornerY);  // Draw the string.
    	}
}
