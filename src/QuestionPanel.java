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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
		JPanel panel = new JPanel(){
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Utilities.paintComponent(g, this);

		    }
		};
		
		panel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		JLabel header = new JLabel();

		GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
		
		if (animalName == null){
			

			
			BufferedImage image = new BufferedImage ( Utilities.east_width, 1, BufferedImage.TYPE_INT_ARGB );

			ImageIcon icon = new ImageIcon(image);
			

			JLabel bum = new JLabel(){
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Utilities.paintComponent(g, this);

			    }
			};
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
		
		JPanel panel2 = new JPanel(){
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Utilities.paintComponent(g, this);

		    }
		};
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


    	JPanel imagePanel = new JPanel(){
    		@Override
    	    protected void paintComponent(Graphics g) {
    	        super.paintComponent(g);
    	        Utilities.paintComponent(g, this);

    	    }
    	};
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
			imagePanel.setBackground(Utilities.GREEN);
			
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
			
			JPanel questionContainer = new JPanel(){
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Utilities.paintComponent(g, this);

			    }
			};
			questionContainer.setLayout(new BoxLayout(questionContainer, BoxLayout.Y_AXIS));

			
			String question = iter.next();
		
			JScrollPane p = new JScrollPane();
			JTextArea field = new JTextArea();
			field.setBackground(Color.LIGHT_GRAY);
			field.setColumns(30);
			field.setLineWrap(true);
			field.setWrapStyleWord(true);
			p.setViewportView(field);
			
			field.setText("Question " + (i) + ": " + question);
			field.setEditable(false);
			
			p.setPreferredSize(field.getPreferredSize());
			
			
			questionContainer.add(p);
			
			JPanel answerSlot = new JPanel(){
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Utilities.paintComponent(g, this);

			    }
			};
			answerSlot.setLayout(new FlowLayout());
			final JTextField ansSpot = new JTextField();
			ansSpot.setColumns(20);
			
			JButton answerButton = new JButton();
			final String answer = QAPairs.get(question);
			
			//TODO: Remove this 
			System.out.println(answer);
			//ansSpot.setText(answer);
			answerButton.setText("Submit");
			ansSpot.setDisabledTextColor(Color.BLACK);
			
			if (allAnswered(animalName, parent)){
				parent.feedbackLabel.setText("You've finished this animal already!");
			}
			
			if (parent.answered.get(question) != null && parent.answered.get(question)){
				questionContainer.setBackground(Utilities.GREEN);
				answerSlot.setBackground(Utilities.GREEN);
				ansSpot.setEnabled(false);
				ansSpot.setText(answer);
				ansSpot.setDisabledTextColor(Color.BLACK);
				ansSpot.setBackground(Utilities.GREEN);
				
			}
			else{
				final String copyName = new String(animalName);
				answerButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String ansGiven = ansSpot.getText();
						
						if (!compareAnswers(ansGiven, answer)){
							parent.feedbackLabel.setText("Try again! Ask Watson if you need help!");
						}
						if (compareAnswers(ansGiven, answer)){
							questionContainer.setBackground(Utilities.GREEN);
							answerSlot.setBackground(Utilities.GREEN);
							ansSpot.setBackground(Utilities.GREEN);
							
							parent.answered.put(question, true);
							int id = questionIDs.get(question);
							parent.answeredIDs.put(id, true);
							Utilities.saveGame(parent);
							parent.feedbackLabel.setText("Correct answer!");
							parent.scoreLabel.setText("Score: " + ++parent.profilePanel.score);
							
							ansSpot.setEnabled(false);
							String soundName = "";
							if (allAnswered(copyName, parent)){
								soundName = "assets/sounds/e71020_short-a.wav";   
								parent.finishedAnimals.add(copyName);
								imagePanel.setOpaque(true);
								imagePanel.setBackground(Utilities.GREEN);
								String rec = getRec(copyName);
								parent.pyramidPanel.updateRecSpot(rec);
								
								parent.feedbackLabel.setText("You've unlocked a title!! Check it out in your Profile page.");
							} else {
								soundName = "assets/sounds/e71026_short-g.wav";    
							}
							
							
							
							try {
								AudioInputStream audioInputStream;
								audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
								Clip clip = AudioSystem.getClip();
								clip.open(audioInputStream);
								clip.start();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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
        Utilities.paintComponent(g, this);

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
	
	String getRec(String animalName){
		SQLiteJDBC db = new SQLiteJDBC();
		
		try {
			int id = db.get_Ecology_ID(animalName);
			ArrayList<Integer> recs = db.get_Relation(id);
			if (recs.size() == 0){
				return null;
			}
			
			Iterator<Integer> iter = recs.iterator();
			while (iter.hasNext()){
				int nex = iter.next();
				String ans = db.get_Ecology_Name(nex);
				if (!parent.correctlyPlacedAnimals.contains(ans)){
					return ans;
				}
				
				
			}
			
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	
}
