import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameScreen extends JPanel {
	
	public PyramidPanel pyramidPanel;
	public JComponent eastPanel;
	public JComponent westPanel;
	public JComponent southPanel;
	public JComponent center;
	public ProfilePanel profilePanel;
	
	public JComponent questionPanel;
	public JComponent trophicPanel;
	public JLabel scoreLabel;
	public JLabel feedbackLabel;
	
	public JComponent header;
	
	public String username;
	public HashMap<String, Boolean> answered;
	
	public HashMap<Integer, Boolean> answeredIDs;
	

	public TreeSet<String> finishedAnimals;
	public TreeSet<String> correctlyPlacedAnimals;

	
	public static Boolean card1Showing = true;

	
	public GameScreen(){
		
		SetupGame sg = new SetupGame();
		this.answered = new HashMap<String, Boolean>();
		this.answeredIDs = new HashMap<Integer, Boolean>();
		this.finishedAnimals = new TreeSet<String>();
		this.correctlyPlacedAnimals = new TreeSet<String>();
		this.username = sg.username;
		Utilities.loadGame(this, username);
		
		
		try {
			profilePanel = new ProfilePanel(username, null, Utilities.getDataString(username, answeredIDs));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		BorderLayout layout = new BorderLayout();
		pyramidPanel = new PyramidPanel(username, sg.ecology, this);
		this.setLayout(layout);


		westPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		//eastPanel.add(scorePanel);
		
		
		
		
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		
		JButton btn1 = new JButton("Your Profile");
		
		header =new JPanel(){
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Utilities.paintComponent(g, this);

		    }
		};
		header.setLayout(new BorderLayout());
		header.add(btn1, BorderLayout.WEST);
		
		feedbackLabel = new JLabel();
		feedbackLabel.setFont(feedbackLabel.getFont().deriveFont(24.0f));
		feedbackLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
		header.add(feedbackLabel, BorderLayout.CENTER);
		feedbackLabel.setText("Drag the organisms to their correct location to begin!");
		
		scoreLabel = new JLabel("Score is: " + profilePanel.score);
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(24.0f));
		scoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		header.add(scoreLabel, BorderLayout.EAST);
		this.add(header, BorderLayout.NORTH);
        btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (card1Showing) {
					
					GameScreen.this.eastPanel.setVisible(false);
					GameScreen.this.westPanel.setVisible(false);
					GameScreen.this.southPanel.setVisible(false);
					
					profilePanel.reload();
					profilePanel.setVisible(true);
					pyramidPanel.setVisible(false);
					card1Showing = false;
					btn1.setText("Resume Game");
					
				}
				else {
					GameScreen.this.eastPanel.setVisible(true);
					GameScreen.this.westPanel.setVisible(true);
					GameScreen.this.southPanel.setVisible(true);
					
					profilePanel.setVisible(false);
					pyramidPanel.setVisible(true);
					card1Showing = true;
					scoreLabel.setText("Score is: " + profilePanel.score);
					btn1.setText("Your Profile");
				}
			}
        });
		
		center = new JPanel();
		this.add(center, BorderLayout.CENTER);
		this.produceQuestionPanel(null);
		this.produceTrophicMemberPanel(-1);
		
		southPanel = new WatsonPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		
		eastPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		westPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		southPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		
		
		center.setLayout(new CardLayout());
		center.add(pyramidPanel, "1");
		center.add(profilePanel, "2");
		pyramidPanel.setVisible(true);
		

		
	}

	
	public void produceQuestionPanel(String animalName){
		
		if (questionPanel != null)
			eastPanel.remove(questionPanel);
		questionPanel = new QuestionPanel(animalName, username, this);
		questionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		eastPanel.add(questionPanel);
		repaint();
		revalidate();
	}
	
	public void removeQuestionPanel(){
		produceQuestionPanel(null);
	}
	
	public void produceTrophicMemberPanel(int trophicLevel){

		if (trophicPanel != null)
			westPanel.remove(trophicPanel);
		trophicPanel = new TrophicMemberPanel(trophicLevel, this);
		westPanel.add(trophicPanel, BorderLayout.WEST);
		trophicPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		repaint();
		revalidate();
	}
	
	

	
}
