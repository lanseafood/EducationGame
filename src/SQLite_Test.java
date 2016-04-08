import java.util.*;

public class SQLite_Test
{
  public static void main( String args[] )
  {
    SQLiteJDBC db = new SQLiteJDBC();
	
	try {
		db.clear_Tables();
		db.add_User("John");
		db.add_User("Paul");
		db.add_User("Fred");
		db.add_User("Derek");
		
		db.remove_User("Derek");
		
		ArrayList<String> list = db.get_Users();
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("User %d: %s\n", i, list.get(i));
		}
		
		System.out.println("\nPaul's current data:");
		System.out.println(db.get_Question_Data("Paul"));
		db.set_Question_Data("Paul", "00110AAFA01110051421");
		System.out.println("\nPaul's updated data:");
		System.out.println(db.get_Question_Data("Paul"));
		
		System.out.println("\nPaul's current interests:");
		System.out.println(db.get_Interest_Data("Paul"));
		db.set_Interest_Data("Paul", "91849142241414121244");
		System.out.println("\nPaul's updated interests:");
		System.out.println(db.get_Interest_Data("Paul"));
		
		db.add_QA("What do lions eat?", "Whatever they want.");
		db.add_QA("What is this database?", "Your best friend.");
		db.add_QA("Why did the chicken cross the road?", "I don''t know.");
		db.add_QA("Why are lions cool?", "Those gnarly teeth, yo.");
		db.remove_QA(2);
		
		System.out.println("\nQuestions and Answers:");
		db.print_QAs();

		Question q = db.get_QA(3);
		System.out.printf("\nQuestion #%d:\n", q.get_Question_ID());
		System.out.printf("%s (%d)\n\t%s\n", q.get_Question(), q.get_Ecology_ID(), q.get_Answer());

		System.out.println("\nQuery for all lion questions:");
		ArrayList<Question> qlist = db.get_QAs(1);
		for (int i = 0; i < qlist.size(); i++) {
			q = qlist.get(i);
			System.out.printf("%d: %s (%d)\n\t%s\n", q.get_Question_ID(),
				q.get_Question(), q.get_Ecology_ID(), q.get_Answer());
		}
		
		db.add_Ecology("lion", 3);
		db.add_Ecology("vulture", 4);
		db.add_Ecology("elephant", 2);
		db.add_Ecology("giraffe", 2);
		db.remove_Ecology(4);
		
		System.out.println("Query of all ecologies:");
		db.print_Ecology();
		
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  }
}