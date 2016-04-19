import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class ProfilePanel extends JPanel implements ActionListener {
	
	public int animal_id;
	ArrayList<String> questions;
	ArrayList<String> answers;
	ArrayList<Boolean> answered;
	public String animalName;
	PyramidMasterPanel parent;
	
	
	JLabel scoreText;
	JComboBox<String> titles;
	
	// Set values, can be scaled up as project expands
	private int speciesCount = 14;
	private int milestones = 2;
	// assuming two milestones at present: 50% and 100% score
	
	private String currentTitle = "Novice";
	
	String username;
	ImageIcon image;
	int score = 0;
	
	String data;
	
	// Pass in username, image icon, and data for user
	// Get panel showing all information and allowing switch of user title
	// Title is either global or pulled from data as last set of bits (assumed global for now)
	public ProfilePanel(String username, ImageIcon image, String data) {
		this.username = username;
		//System.out.println(username);
		this.image = image;
		this.data = data;
		// Create title dropdown
		titles = new JComboBox<String>();


		// Draw panel
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		
		JLabel user = new JLabel(username, image, JLabel.CENTER);
		scoreText = new JLabel();
		JButton exit = new JButton("Close");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//parent.removeProfilePanel();
			}
			
		});
		
		load();
		
		this.add(user);
		Border b = new EmptyBorder(500, 0, 100, 0);
		scoreText.setBorder(b);
		this.add(titles);
		this.add(scoreText);
		
		this.add(exit);
		
	}
	
	public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        currentTitle = (String)cb.getSelectedItem();
    }
	
	public void reload(){
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			data = db.get_Question_Data(username);
			
			//System.out.println(username + data);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		load();
	}
	
	public void load(){
		titles.removeAllItems();
		// Create list for holding title indices during data reading
		ArrayList<Integer> titleIDs = new ArrayList<Integer>();
		
		// First title, novice title, auto-unlocked
		titleIDs.add(-1);
		score = 0;
		// Process data for useful info
		for (int i = 0; i < speciesCount; i++) {
			// Get score for species i
			int q1score = Integer.parseInt(data.substring(i*3, i*3 + 1));
			int q2score = Integer.parseInt(data.substring(i*3 + 1, i*3 + 2));
			int q3score = Integer.parseInt(data.substring(i*3 + 2, i*3 + 3));
			int speciesScore = q1score + q2score + q3score;
			
			
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
		System.out.println(titleIDs);
		scoreText.setText("Score: " + score);
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
			titles.removeAllItems();
			titles.addItem("Error");
			e.printStackTrace();
		}
	}
}
