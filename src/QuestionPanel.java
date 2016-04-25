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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class QuestionPanel extends JScrollPane{
	
	public int animal_id;
	
	HashMap<String, String> QAPairs;
	HashMap<String, Integer> questionIDs;
	
	
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
		questionIDs = new HashMap<String, Integer>();
		JPanel panel = new JPanel();
		
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			
			animal_id = db.get_Ecology_ID(animalName);
			ArrayList<Question> questionList = db.get_QAs(animal_id);
			
			//answered = Utilities.decodeAnswers(username);
			
			
			Iterator<Question> iter = questionList.iterator();
			while (iter.hasNext()){
				Question q = iter.next();
				QAPairs.put(q.get_Question(), q.get_Answer());
				questionIDs.put(q.get_Question(), q.get_Question_ID());
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			animalName = "";
			e.printStackTrace();
		}
		
		BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		

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
    	
    	panel.add(imagePanel);
    	
		
		if (parent.finishedAnimals.contains(animalName)){
			imagePanel.setOpaque(true);
			imagePanel.setBackground(Color.GREEN);
			
		}
		
		
		JButton test = new JButton();
		test.setText("Exit");
		test.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.removeQuestionPanel();
				
			}
			
		});
		
		final JPanel par = panel;
		Iterator<String> iter = QAPairs.keySet().iterator();
		
		for (int i = 1; iter.hasNext(); ++i){
			
			JPanel questionContainer = new JPanel();
			questionContainer.setLayout(new BoxLayout(questionContainer, BoxLayout.Y_AXIS));

			
			String question = iter.next();
		
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
			
			//TODO: Remove this 
			//ansSpot.setText(answer);
			answerButton.setText("Submit");
			ansSpot.setDisabledTextColor(Color.BLACK);
			
			if (parent.answered.get(question) != null && parent.answered.get(question)){
				questionContainer.setBackground(Color.GREEN);
				answerSlot.setBackground(Color.GREEN);
				ansSpot.setEnabled(false);
				ansSpot.setText(answer);
				ansSpot.setDisabledTextColor(Color.BLACK);
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
							int id = questionIDs.get(question);
							parent.answeredIDs.put(id, true);
							Utilities.saveGame(parent);
							
							
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
		
		
		panel.add(test);
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
	
	
	private boolean compareAnswers(String s1, String s2) {
		// Build string 1
		String[] s1_frags = s1.trim().toLowerCase().split("/s+");
		for (int i = 0; i < s1_frags.length; i++)
			if (s1_frags[i].matches("[0-9]+"))
				s1_frags[i] = NumbersToWords.convert(Long.parseLong(s1_frags[i]));
		
		StringBuilder build1 = new StringBuilder();
		for (String s :  s1_frags)
			build1.append(s);
		
		// Build string 2
		String[] s2_frags = s2.trim().toLowerCase().split("/s+");
		for (int i = 0; i < s2_frags.length; i++)
			if (s2_frags[i].matches("[0-9]+"))
				s2_frags[i] = NumbersToWords.convert(Long.parseLong(s2_frags[i]));
		
		StringBuilder build2 = new StringBuilder();
		for (String s :  s2_frags)
			build2.append(s);
		
		// Return match
		return build1.toString().equals(build2.toString());
	}
}
