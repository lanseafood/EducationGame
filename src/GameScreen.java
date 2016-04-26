import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GameScreen extends JPanel {
	
	public PyramidPanel pyramidPanel;
	public JComponent eastPanel;
	public JComponent westPanel;
	public JComponent southPanel;
	public JComponent center;
	public ProfilePanel profilePanel;
	
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
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BorderLayout layout = new BorderLayout();
		pyramidPanel = new PyramidPanel(username, sg.ecology, this);
		this.setLayout(layout);
		
		center = new JPanel();
		header = new JPanel();
		this.add(center, BorderLayout.CENTER);
		this.produceQuestionPanel(null);
		this.produceTrophicMemberPanel(-1);
		this.add(new WatsonPanel(), BorderLayout.SOUTH);
		
		
		center.setLayout(new CardLayout());
		center.add(pyramidPanel, "1");
		center.add(profilePanel, "2");
		pyramidPanel.setVisible(true);
		
		JButton btn1 = new JButton("Your Profile");
		
		header.add(btn1);
		this.add(header, BorderLayout.NORTH);
        btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (card1Showing) {
					
					GameScreen.this.eastPanel.setVisible(false);
					GameScreen.this.westPanel.setVisible(false);
					
					profilePanel.reload();
					profilePanel.setVisible(true);
					pyramidPanel.setVisible(false);
					card1Showing = false;
					btn1.setText("Resume Game");
				}
				else {
					GameScreen.this.eastPanel.setVisible(true);
					GameScreen.this.westPanel.setVisible(true);
					
					
					profilePanel.setVisible(false);
					pyramidPanel.setVisible(true);
					card1Showing = true;
					btn1.setText("Your Profile");
				}
			}
        });
		
	}

	
	public void produceQuestionPanel(String animalName){
		
		if (eastPanel != null)
			this.remove(eastPanel);
		eastPanel = new QuestionPanel(animalName, username, this);
		this.add(eastPanel, BorderLayout.EAST);
		repaint();
		revalidate();
	}
	
	public void removeQuestionPanel(){
		produceQuestionPanel(null);
	}
	
	public void produceTrophicMemberPanel(int trophicLevel){
		
		
		if (westPanel != null)
			this.remove(westPanel);
		westPanel = new TrophicMemberPanel(trophicLevel, this);
		this.add(westPanel, BorderLayout.WEST);
		repaint();
		revalidate();
	}
	
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
    	try {
			image = ImageIO.read(new File("assets/cheap_diagonal_fabric/cheap_diagonal_fabric/cheap_diagonal_fabric.png"));
			image = image.getScaledInstance(Utilities.big_width, Utilities.big_width, 0);
			g.drawImage(image, 0, 0, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("oops");

		}

    }
	
}
