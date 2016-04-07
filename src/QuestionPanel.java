import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class QuestionPanel extends JPanel{
	
	public int animal_id;
	
	HashMap<String, String> QAPairs;
	ArrayList<String> answers;
	
	String username;
	
	public String animalName;
	
	PyramidMasterPanel parent;
	
	HashMap<String, Integer> questionsAnswered;
	
	
	public QuestionPanel(String animalName, String username, PyramidMasterPanel parent){
		this.parent = parent;
		this.username = username;
		this.animalName = animalName;
		
		QAPairs = new HashMap<String, String>();
		
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			
			animal_id = db.get_Ecology_ID(animalName);
			ArrayList<Question> questionList = db.get_QAs(animal_id);
			
			//answered = Utilities.decodeAnswers(username);
			
			
			Iterator<Question> iter = questionList.iterator();
			while (iter.hasNext()){
				Question q = iter.next();
				QAPairs.put(q.get_Question(), q.get_Answer());
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			animalName = "";
			e.printStackTrace();
		}
		
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		

    	JPanel imagePanel = new JPanel();
    	JLabel label = new JLabel();
    	
    	Image image;
    	ImageIcon icon;
    	try {
			image = ImageIO.read(new File("assets/" + animalName.toLowerCase() + ".png"));
			image = image.getScaledInstance(Utilities.big_width, Utilities.big_width, 0);
			icon = new ImageIcon(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			
			image = new BufferedImage ( Utilities.big_width, Utilities.big_width, BufferedImage.TYPE_INT_ARGB );
			Graphics g = image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Utilities.big_width, Utilities.big_width);
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, Utilities.big_width, Utilities.big_width);
			
			
			
			Utilities.drawCenteredText(g, Utilities.big_width/2, Utilities.big_width/2, Utilities.big_width/10, animalName);
			
			g.dispose();
			
			icon = new ImageIcon(image);
		}
    	
    	
    	label.setIcon(icon);
    	imagePanel.add(label);
    	
    	this.add(imagePanel);
    	
    	
		
		
		JButton test = new JButton();
		test.setText("Exit");
		test.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.removeQuestionPanel();
				
			}
			
		});
		
		final JPanel par = this;
		Iterator<String> iter = QAPairs.keySet().iterator();
		
		for (int i = 1; iter.hasNext(); ++i){
			
			JPanel questionContainer = new JPanel();
			questionContainer.setLayout(new BoxLayout(questionContainer, BoxLayout.Y_AXIS));
		
			
			String question = iter.next();
			System.out.println(QAPairs.get(question));
			
			JTextField field = new JTextField();
			field.setText("Question " + (i) + ": " + question);
			field.setEditable(false);
			
			questionContainer.add(field);
			
			JPanel answerSlot = new JPanel();
			answerSlot.setLayout(new FlowLayout());
			final JTextField ansSpot = new JTextField();
			ansSpot.setColumns(20);
			
			JButton answerButton = new JButton();
			final String answer = QAPairs.get(question);
			ansSpot.setText(answer);
			answerButton.setText("Submit");
			
			
			if (parent.answered.get(question) != null && parent.answered.get(question)){
				questionContainer.setBackground(Color.GREEN);
				answerSlot.setBackground(Color.GREEN);
				ansSpot.setEnabled(false);
				ansSpot.setText(answer);
			}
			else{
				final String copyName = new String(animalName);
				answerButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String ansGiven = ansSpot.getText();
						if (ansGiven.equals(answer)){
							questionContainer.setBackground(Color.GREEN);
							answerSlot.setBackground(Color.GREEN);
							
							parent.answered.put(question, true);
							ansSpot.setEnabled(false);
							if (allAnswered(copyName, parent)){
								parent.finishedAnimals.add(copyName);
								imagePanel.setOpaque(true);
								imagePanel.setBackground(Color.GREEN);
							}
							
						}
						
						
					}
					
				});
			}
			
			
			
			answerSlot.add(ansSpot);
			answerSlot.add(answerButton);
			questionContainer.add(answerSlot);
			par.add(questionContainer);

		}
		
		
		this.add(test);
	}
	
	public boolean allAnswered(String animalName, PyramidMasterPanel parent){
		SQLiteJDBC db = new SQLiteJDBC();
		int i = 0;
		try {
			
			int animal_id = db.get_Ecology_ID(animalName);
			ArrayList<Question> questionList = db.get_QAs(animal_id);
			Iterator<Question> iter = questionList.iterator();
			while (iter.hasNext()){
				Question q = iter.next();
				if (parent.answered.get(q.get_Question()) == true){
					i++;
				}
			}
			
			if (i == questionList.size()){
				return true;
			}
		} catch (Exception e){
			
		}
		
		return false;
	}
	
}
