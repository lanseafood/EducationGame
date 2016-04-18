/*
 * DO NOT USE THIS TO APPEND DATA TO THE DATABASE! THIS SIMPLY INITIALISES THE DATABASE
 * AND CLEARS PREVIOUS TABLES
 */
import java.util.ArrayList;

public class PopulateDatabase
{
  public static void main( String args[] )
  {
    SQLiteJDBC db = new SQLiteJDBC();
	
	try {
		db.clear_Tables();
		
		//add user
		db.add_User("Simba");
		
		//set question data
		//db.set_Question_Data("Simba", "00000000000000000000");
				
		//add organisms
		db.add_Ecology("King Vulture", 4);
		db.add_Ecology("Eagle", 4);
		db.add_Ecology("Lion", 3);
		db.add_Ecology("Cheetah", 3);
		db.add_Ecology("Tiger", 3);
		db.add_Ecology("Hyena", 3);
		db.add_Ecology("Elephant", 2);
		db.add_Ecology("Giraffe", 2);
		db.add_Ecology("Antelope", 2);
		db.add_Ecology("Rhinoceros", 2);
		db.add_Ecology("Acacia", 1);
		db.add_Ecology("Elephant Grass", 1);
		db.add_Ecology("Baobab", 1);
		db.add_Ecology("River Bushwillow", 1);
		
		//add question and answers
		//Vultures:
		
		db.add_QA(1, "At what age does the King Vulture take its first flight?", "3 months");
		db.add_QA(1, "What habitat does the King Vulture inhabit?", "Savannas and Grasslands");
		db.add_QA(1, "How long do King Vultures live in captivity?", "30 years");
		
		//Eagles:
		db.add_QA(2, "What are eagles’ nests called?", "Eyries");
		db.add_QA(2, "How many species of the eagle exist?", "More than 60");
		db.add_QA(2, "How many eggs do eagles normally lay?", "2");
		
		//Lions:
		db.add_QA(3, "How long does a lion typically sleep in a day?", "16-20 hours");
		db.add_QA(3, "How much does a lion cub normally weigh?", "3 pounds");
		db.add_QA(3, "How large is a pride?", "15 lions");
		
		//Cheetahs:
		db.add_QA(4, "How fast can a cheetah run?", "70 mph");
		db.add_QA(4, "What name is given to the line of long hair that grows on the backs of cheetah cubs?", "Mantle");
		db.add_QA(4, "What is the gestation period of the cheetah?", "3 months");
		
		//Tigers:
		db.add_QA(5, "How much can an adult tiger weigh?", "660 pounds");
		db.add_QA(5, "What is a group of tigers known as?", "Ambush or Streak");
		db.add_QA(5, "How fast can tigers run?", "40 mph");
		
		//Hyenas:
		db.add_QA(6, "What does a hyena’s laugh indicate?", "Social status and age");
		db.add_QA(6, "True or false: Hyenas are matriarchal.", "True");
		db.add_QA(6, "What is the gestation period for a hyena?", "110 days");
		
		//Elephants:
		db.add_QA(7, "What is the lifespan of the African elephant?", "60-70 years");
		db.add_QA(7, "What is the gestation period of the elephant?", "22 months");
		db.add_QA(7, "What is the tusk of an elephant made of?", "Ivory");
		
		//Giraffes:
		db.add_QA(8, "What is the color of a giraffe’s tongue?", "Purple");
		db.add_QA(8, "How much do giraffes sleep per day?", "10 minutes - 2 hours");
		db.add_QA(8, "At what age can a baby giraffe stand up?", "30 minutes");
		
		//Antelope:
		db.add_QA(9, "True or false: Antelope are ruminants.", "True");
		db.add_QA(9, "How large can an antelope’s horns grow?", "5 feet");
		db.add_QA(9, "How fast can antelope run?", "43 mph");
		
		//Rhinoceros:
		db.add_QA(10, "What does the word ‘rhinoceros’ mean?", "Nose horn");
		db.add_QA(10, "What is the horn of a rhino made of?", "Keratin");
		db.add_QA(10, "What is the gestation period for a rhino?", "15-16 months");
		
		//Acacia:
		db.add_QA(11, "How tall can an acacia grow to be?", "40 feet");
		db.add_QA(11, "How many petals does the Acacia flower have?", "5");
		db.add_QA(11, "How long does the acacia live?", "15-30 years");
		
		//Elephant Grass:
		db.add_QA(12, "How tall can elephant grass grow?", "10 feet");
		db.add_QA(12, "How long can elephant grass live in the wild?", "20 years");
		db.add_QA(12, "What agent does elephant grass use for pollination?", "Wind");
		
		//Baobab:
		db.add_QA(13, "How many species of the baobab exist?", "9");
		db.add_QA(13, "What agent does the baobab use for pollination?", "Bats");
		db.add_QA(13, "What does the baobab store in its trunk?", "Water");
		
		//River Bushwillow:
		db.add_QA(14, "What type of climate does the river bushwillow normally grow in?", "Warm and dry");
		db.add_QA(14, "What family does the river bushwillow belong to?", "Combretum");
		db.add_QA(14, "What special type of root system does the bushwillow have?", "Hydrophilic");
				
		
		//add titles:
		db.add_Title(1, "The Culture Vulture");
		db.add_Title(2, "The Eagle Eye");
		
		db.add_Title(3, "The Lion King");
		db.add_Title(4, "Flamin Hot Cheeto");
		db.add_Title(5, "Tigger");
		db.add_Title(6, "Joker");
		
		db.add_Title(7, "Dumbo");
		db.add_Title(8, "Melman");
		db.add_Title(9, "The Leaping Antelope");
		db.add_Title(10, "Master Thundering Rhino");
		
		db.add_Title(11, "The Acacia Accolade");
		db.add_Title(12, "Master of the Grass");
		db.add_Title(13, "The Baobab Baboon");
		db.add_Title(14, "The Whomping Rhino");
		
		db.add_Title(-1, "N00b");
		db.add_Title(-2, "Novice");
		db.add_Title(-3, "Legendary");
		db.add_Title(-4, "Godlike");
		
		
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  }
}
