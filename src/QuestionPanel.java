import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class QuestionPanel extends JPanel{
	
	public int animal_id;
	ArrayList<String> questions;
	ArrayList<String> answers;
	ArrayList<Boolean> answered;
	String username;
	public String animalName;
	PyramidMasterPanel parent;
	
	
	public QuestionPanel(String animalName, String username, PyramidMasterPanel parent){
		this.parent = parent;
		this.username = username;
		this.animalName = animalName;
		
		questions = new ArrayList<String>();
		answers = new ArrayList<String>();
		answered = new ArrayList<Boolean>();
		
		
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			
			animal_id = db.get_Ecology_ID(animalName);
			ArrayList<Question> questionList = db.get_QAs(animal_id);
			
			answered = Utilities.decodeAnswers(username);
			
			Iterator<Question> iter = questionList.iterator();
			while (iter.hasNext()){
				Question q = iter.next();
				questions.add(q.get_Question());
				answers.add(q.get_Answer());
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			animalName = "";
			e.printStackTrace();
		}
		
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		
		JTextField header = new JTextField();
		JButton test = new JButton();
		test.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.removeQuestionPanel();
				
			}
			
		});
		header.setText("Hi, aminal is " + animalName);
		this.add(header);
		this.add(test);
	}
	
}
