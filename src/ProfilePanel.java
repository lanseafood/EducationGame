import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;


public class ProfilePanel extends JPanel implements ActionListener {
	
	public int animal_id;
	ArrayList<String> questions;
	ArrayList<String> answers;
	ArrayList<Boolean> answered;
	public String animalName;
	PyramidMasterPanel parent;
	
	// Set values, can be scaled up as project expands
	private int speciesCount = 14;
	private int milestones = 2;
	// assuming two milestones at present: 50% and 100% score
	
	private String currentTitle = "Novice";
	
	String username;
	ImageIcon image;
	int score = 0;
	
	// Pass in username, image icon, and data for user
	// Get panel showing all information and allowing switch of user title
	// Title is either global or pulled from data as last set of bits (assumed global for now)
	public ProfilePanel(String username, ImageIcon image, String data) {
		this.username = username;
		this.image = image;
		
		// Create list for holding title indices during data reading
		ArrayList<Integer> titleIDs = new ArrayList<Integer>();
		
		// First title, novice title, auto-unlocked
		titleIDs.add(0);
		
		// Process data for useful info
		for (int i = 0; i < speciesCount; i++) {
			// Get score for species i
			int speciesScore = Integer.parseInt(data.substring(i*2, (i+1)*2), 2);
			score += speciesScore;
			if (speciesScore == 3)
				titleIDs.add(i + 1); //assume species IDs/enumeration begins at 1
		}
		
		// Check for other milestones
		int maxScore = 3*speciesCount;
		for (int i = 1; i <= milestones; i++) {
			if (score > Math.floor(i*maxScore/milestones))
				titleIDs.add(-i);
		}
		
		// Order titleIDs such that milestones come first (best to worst sorted)
		Collections.sort(titleIDs);
		
		// Create title dropdown
		JComboBox<String> titles = new JComboBox<String>();
		
		// Open database instance to fetch titles
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			// Populate title dropdown
			for (int i = 0; i < titleIDs.size(); i++) {
				titles.addItem(db.get_Title(titleIDs.get(i)));
			}
			
			// Set selected title to current title
			titles.setSelectedItem(currentTitle);
			
			// Set up event listener
			titles.addActionListener(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			titles.addItem("Error");
			e.printStackTrace();
		}
		
		// Draw panel
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		
		JLabel user = new JLabel(username, image, JLabel.CENTER);
		JTextField scoreText = new JTextField();
		JButton exit = new JButton("Close");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//parent.removeProfilePanel();
			}
			
		});
		scoreText.setText("Score: " + score);
		this.add(user);
		this.add(scoreText);
		this.add(exit);
		
	}
	
	public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        currentTitle = (String)cb.getSelectedItem();
    }
}
