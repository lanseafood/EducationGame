import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


public class QuestionPanel extends JScrollPane{
	
	public int animal_id;
	
	HashMap<String, String> QAPairs;
	HashMap<String, Integer> questionIDs;
	
	
	ArrayList<String> answers;
	
	String username;
	
	public String animalName;
	
	GameScreen parent;
	
	HashMap<String, Integer> questionsAnswered;
	
	
	public QuestionPanel(String animalName, String username, GameScreen parent){

		this.parent = parent;
		this.username = username;
		this.animalName = animalName;
		
		QAPairs = new HashMap<String, String>();
		questionIDs = new HashMap<String, Integer>();
		JPanel panel = new JPanel();
		
		JLabel header = new JLabel();
		header.setText("Hi!");

		GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		
		if (animalName == null){
			header.setText("d");
			

			
			BufferedImage image = new BufferedImage ( Utilities.east_width, 1, BufferedImage.TYPE_INT_ARGB );

			ImageIcon icon = new ImageIcon(image);
			

			JLabel bum = new JLabel();
			bum.setIcon(icon);
			
			layout.setHorizontalGroup(
				    layout.createSequentialGroup()
				        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				            .addComponent(header)
				            .addComponent(bum)

				            ));
			
			layout.setVerticalGroup(
				    layout.createSequentialGroup()
				        .addGroup(layout.createSequentialGroup()
				            .addComponent(header)
				            .addComponent(bum)
				            ));	
			
	        
			this.setViewportView(bum);
			
			return;
		}
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
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
    	
    	
    	
    	panel2.add(imagePanel);
    	
		
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
		
		final JPanel par = panel2;
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
			System.out.println(answer);
			ansSpot.setText(answer);
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

		layout.setHorizontalGroup(
			    layout.createSequentialGroup()
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			            .addComponent(header)
			            .addComponent(panel2)
			            .addComponent(test)
			            ));
		
		layout.setVerticalGroup(
			    layout.createSequentialGroup()
			        .addGroup(layout.createSequentialGroup()
			            .addComponent(header)
			            .addComponent(panel2)
			            .addComponent(test)
			            ));	
		
        layout.linkSize(SwingConstants.HORIZONTAL, panel2, header, test);
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	public boolean allAnswered(String animalName, GameScreen parent){
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
	
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Image image;
    	try {
			image = ImageIO.read(new File("assets/cheap_diagonal_fabric/cheap_diagonal_fabric/cheap_diagonal_fabric.png"));
			image = image.getScaledInstance(Utilities.east_width, 1, 0);
			g.drawImage(image, 0, 0, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("oops");

		}

    }
	
	
}
