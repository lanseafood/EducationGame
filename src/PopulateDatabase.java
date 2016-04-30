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
		db.set_Question_Data("Simba", "000000000000000000000000000000000000000000000000000000000000");
				
		//add organisms
		db.add_Ecology(1, "King Vulture", 4);
		db.add_Ecology(2, "Eagle", 4);
		db.add_Ecology(3, "Lion", 3);
		db.add_Ecology(4, "Cheetah", 3);
		db.add_Ecology(5, "Tiger", 3);
		db.add_Ecology(6, "Hyena", 3);
		db.add_Ecology(7, "Elephant", 2);
		db.add_Ecology(8, "Giraffe", 2);
		db.add_Ecology(9, "Antelope", 2);
		db.add_Ecology(10, "Rhinoceros", 2);
		db.add_Ecology(11, "Acacia", 1);
		db.add_Ecology(12, "Elephant Grass", 1);
		db.add_Ecology(13, "Baobab", 1);
		db.add_Ecology(14, "River Bushwillow", 1);
		db.add_Ecology(15, "Marabou Stork", 4);
		db.add_Ecology(16, "Chameleon", 3);
		db.add_Ecology(17, "Nile Crocodile", 3);
		db.add_Ecology(18, "Koala", 2);
		db.add_Ecology(19, "Leopard Tortoise", 2);
		db.add_Ecology(20, "Jackalberry", 1);
		
		//add question and answers
		//Vultures:
		
		db.add_QA(1, 1, "At what age does the King Vulture take its first flight?", "3 months");
		db.add_QA(2, 1, "What habitat does the King Vulture inhabit?", "Savannas and Grasslands");
		db.add_QA(3, 1, "How long do King Vultures live in captivity?", "30 years");
		
		//Eagles:
		db.add_QA(4, 2, "What are eagles'' nests called?", "Eyries");
		db.add_QA(5, 2, "How many species of the eagle exist?", "More than 60");
		db.add_QA(6, 2, "How many eggs do eagles normally lay?", "2");
		
		//Lions:
		db.add_QA(7, 3, "How long does a lion typically sleep in a day?", "16-20 hours");
		db.add_QA(8, 3, "How much does a lion cub normally weigh?", "3 pounds");
		db.add_QA(9, 3, "How large is a pride?", "15 lions");
		
		//Cheetahs:
		db.add_QA(10, 4, "How fast can a cheetah run?", "70 mph");
		db.add_QA(11, 4, "What name is given to the line of long hair that grows on the backs of cheetah cubs?", "Mantle");
		db.add_QA(12, 4, "What is the gestation period of the cheetah?", "3 months");
		
		//Tigers:
		db.add_QA(13, 5, "How much can an adult tiger weigh?", "660 pounds");
		db.add_QA(14, 5, "What is a group of tigers known as?", "Ambush or Streak");
		db.add_QA(15, 5, "How fast can tigers run?", "40 mph");
		
		//Hyenas:
		db.add_QA(16, 6, "What does a hyena''s laugh indicate?", "Social status and age");
		db.add_QA(17, 6, "True or false: Hyenas are matriarchal.", "True");
		db.add_QA(18, 6, "What is the gestation period for a hyena?", "110 days");
		
		//Elephants:
		db.add_QA(19, 7, "What is the lifespan of the African elephant?", "60-70 years");
		db.add_QA(20, 7, "What is the gestation period of the elephant?", "22 months");
		db.add_QA(21, 7, "What is the tusk of an elephant made of?", "Ivory");
		
		//Giraffes:
		db.add_QA(22, 8, "What is the color of a giraffe''s tongue?", "Purple");
		db.add_QA(23, 8, "How much do giraffes sleep per day?", "10 minutes - 2 hours");
		db.add_QA(24, 8, "At what age can a baby giraffe stand up?", "30 minutes");
		
		//Antelope:
		db.add_QA(25, 9, "True or false: Antelope are ruminants.", "True");
		db.add_QA(26, 9, "How large can an antelope''s horns grow?", "5 feet");
		db.add_QA(27, 9, "How fast can antelope run?", "43 mph");
		
		//Rhinoceros:
		db.add_QA(28, 10, "What does the word ''rhinoceros'' mean?", "Nose horn");
		db.add_QA(29, 10, "What is the horn of a rhino made of?", "Keratin");
		db.add_QA(30, 10, "What is the gestation period for a rhino?", "15-16 months");
		
		//Acacia:
		db.add_QA(31, 11, "How tall can an acacia grow to be?", "40 feet");
		db.add_QA(32, 11, "How many petals does the Acacia flower have?", "5");
		db.add_QA(33, 11, "How long does the acacia live?", "15-30 years");
		
		//Elephant Grass:
		db.add_QA(34, 12, "How tall can elephant grass grow?", "10 feet");
		db.add_QA(35, 12, "How long can elephant grass live in the wild?", "20 years");
		db.add_QA(36, 12, "What agent does elephant grass use for pollination?", "Wind");
		
		//Baobab:
		db.add_QA(37, 13, "How many species of the baobab exist?", "9");
		db.add_QA(38, 13, "What agent does the baobab use for pollination?", "Bats");
		db.add_QA(39, 13, "What does the baobab store in its trunk?", "Water");
		
		//River Bushwillow:
		db.add_QA(40, 14, "What type of climate does the river bushwillow normally grow in?", "Warm and dry");
		db.add_QA(41, 14, "What family does the river bushwillow belong to?", "Combretum");
		db.add_QA(42, 14, "What special type of root system does the bushwillow have?", "Hydrophilic");
		
		//Marabou Stork:
		db.add_QA(43, 15, "True or false: The Marabou Stork is a solitary bird.", "False");
		db.add_QA(44, 15, "How many eggs does the Marabou Stork lay at a time?", "2 - 3");
		db.add_QA(45, 15, "What is the wingspan of the Marabou Stork?", "3 meters");
		
		//Chameleon:
		db.add_QA(46, 16, "True or false: A chameleon does not need to drink any water.", "False");
		db.add_QA(47, 16, "What is the gestation period for chameleons?", "4 - 6 months");
		db.add_QA(48, 16, "What family does the chameleon belong to?", "Chamaeleonidae");
		
		//Nile Crocodile:
		db.add_QA(49, 17, "How long can the Nile crocodile grow to be?", "6 m");
		db.add_QA(50, 17, "How many eggs can a female Nile Crocodile lay at one time?", "60");
		db.add_QA(51, 17, "What environmental factor determines the sex of a baby crocodile?", "Temperature");
				
		
		//Koala:
		db.add_QA(52, 18, "What is a baby Koala known as?", "Joey");
		db.add_QA(53, 18, "How much do Koalas sleep per day?", "18-20 hours");
		db.add_QA(54, 18, "What do you call the 窶�snore-like窶� sound that Koalas make?", "Bellow");
		
		//Leopard Tortoise:
		db.add_QA(55, 19, "How long can an adult Leopard Tortoise grow to be?", "30 inches");
		db.add_QA(56, 19, "How many years can a Leopard Tortoise live in the wild?", "50-100 years");
		db.add_QA(57, 19, "True or false: Leopard Tortoises do not hibernate.", "True");
		
		//Jackalberry
		db.add_QA(58, 20, "How tall does an average Jackalberry tree grow to be?", "15-18 feet");
		db.add_QA(59, 20, "Which months do the leaves of the Jackalberry grow in?", "June-October");
		db.add_QA(60, 20, "True or false: The fruit of the jackalberry grows only on female trees.", "True");
		
		
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
		
		db.add_Title(15, "The Stork Stalker");
		db.add_Title(16, "Karma Chameleon");
		db.add_Title(17, "Croc");
		db.add_Title(18, "The Koala-fier");
		db.add_Title(19, "The Slow Poke");
		db.add_Title(20, "The Jackalberry Jester");
		
		db.add_Title(-1, "N00b");
		db.add_Title(-2, "Novice");
		db.add_Title(-3, "Legendary");
		db.add_Title(-4, "Godlike");
		
		db.print_Ecology();
		db.print_QAs();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  }
}
