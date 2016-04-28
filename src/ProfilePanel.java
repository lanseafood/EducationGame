import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
//	String welcomeMessage = "Hello";
	int score = 0;
	
	String data;
	
	//FOR GRID
	ArrayList<String> allAnimalNames;
	HashMap<String, ImageIcon> animalSealImages;
	HashMap<String, String> animalTitles;
	JScrollPane j = new JScrollPane();
	JPanel jp = new JPanel(new GridLayout(3, 5));
	JLabel info;
	// Pass in username, image icon, and data for user
	// Get panel showing all information and allowing switch of user title
	// Title is either global or pulled from data as last set of bits (assumed global for now)
	public ProfilePanel(String username, ImageIcon image, String data) throws IOException {
		
		this.username =  username;
		//System.out.println(username);		
		this.image = image;
		this.data = data;
		// Create title dropdown
		titles = new JComboBox<String>();

		//Create grid of animals seals
//		JLabel j1 = new JLabel();
//		j1.setIcon(new ImageIcon("assets/" + animalName.toLowerCase() + ".png");
//
//		j1.addMouseListener(...);
//
//		Jp.add(j1); 
//		
		//getting seal images
		animalSealImages = new HashMap<String, ImageIcon>();
    	
    	SQLiteJDBC db = new SQLiteJDBC();
    	try {
			allAnimalNames = db.retrieve_Ecology();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			allAnimalNames = new ArrayList<String>();
		}    
    	
		// Draw panel
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		
		JLabel hi = new JLabel("Hello " + username, null, JLabel.CENTER);
//		JLabel user = new JLabel(username, image, JLabel.CENTER);
		info = new JLabel("You are nothing yet", null, JLabel.CENTER);
		scoreText = new JLabel();
		
		load();

		//this.add(user);
		//Border b = new EmptyBorder(500, 0, 100, 0);
		//scoreText.setBorder(b);
		this.add(hi);
		this.add(info);
		this.add(scoreText);
		j.add(jp);
		j.setViewportView(jp);
		j.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(j, BorderLayout.CENTER);

		//this.add(titles);		
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
		jp.removeAll();
		// Create list for holding title indices during data reading
		ArrayList<Integer> titleIDs = new ArrayList<Integer>(); //titles that you can click on
		ArrayList<Integer> lockedTitleIDs = new ArrayList<Integer>(); //all the ones you haven't done yet
		
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
			if (speciesScore == 3){
				titleIDs.add(i + 1); //assume species IDs/enumeration begins at 1
			}
			else {
				lockedTitleIDs.add(i + 1); //assume species IDs enumeration begins at 1 
			}
		}
		
		// Check for other milestones
		int maxScore = 3*speciesCount;
		for (int i = 1; i <= milestones; i++) {
			if (score > Math.floor(i*maxScore/milestones))
				titleIDs.add(-i);
		}
		
		// Order titleIDs such that milestones come first (best to worst sorted)
		Collections.sort(titleIDs);
		Collections.sort(lockedTitleIDs);
//		System.out.println("THE ONES YOU FINISHED");
		System.out.println(titleIDs);
//		System.out.println("THE ONES NOT COMPLETED");
		System.out.println(lockedTitleIDs);
		scoreText.setText("Your score is " + score);
		SQLiteJDBC db = new SQLiteJDBC();
		try {
			// Populate title dropdown
			for (int i = 0; i < titleIDs.size(); i++) {
				titles.addItem(db.get_Title(titleIDs.get(i)));
				if (i > 0) {
					Integer ID = titleIDs.get(i);
					String animalName = db.get_Ecology_Name(ID);
					String animalSealName = animalName + "Seal";
		        	ImageIcon sealImage = new ImageIcon();
		        	Image tempI = null;
		        	System.out.println("FINISHED animals");
		        	System.out.println(animalName);
		        	try {
						tempI = ImageIO.read(new File("assets/IconSeals/" + animalSealName.toLowerCase() + ".png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	tempI = tempI.getScaledInstance(200, 200, 0);
		        	sealImage.setImage(tempI);
		    		animalSealImages.put(animalName, sealImage);
		    		
		    		JLabel a = new JLabel();
		    		a.setName(animalName);
		    		a.setIcon(sealImage);
		    		MouseListener listener = new MouseAdapter() {
		        	    public void mouseClicked(MouseEvent e) {
//		        	    	String titleID = animalTitles.get(a.getName());
		        	    	try {
		        	    		System.out.println(db.get_Title(ID));
								currentTitle = db.get_Title(ID);
//								info = new JLabel(, null, JLabel.CENTER);
		        	    		System.out.println(info);
								info.setText("You are the " + currentTitle);
		        	    		System.out.println(info);

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	    }};        	    
		    		a.addMouseListener(listener);
		    		jp.add(a);
				}
			}
			for (int i = 0; i < lockedTitleIDs.size(); i++) {
				titles.addItem(db.get_Title(lockedTitleIDs.get(i)));
				if (i > 0){
					String animalName = db.get_Ecology_Name(lockedTitleIDs.get(i));
					String animalSealName = animalName + "Seal";
		        	ImageIcon sealImage = new ImageIcon();
		        	Image tempI = null;
		        	System.out.println("locked animals");
		        	System.out.println(animalName);
		        	try {
						tempI = ImageIO.read(new File("assets/IconSeals/" + animalSealName.toLowerCase() + ".png"));
						tempI.getGraphics().setColor(Color.DARK_GRAY);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	tempI = tempI.getScaledInstance(200, 200, 0);
		        	sealImage.setImage(tempI);
		    		animalSealImages.put(animalName, sealImage);
		    		
		    		JLabel a = new JLabel();
		    		a.setName(animalName);
		    		a.setIcon(sealImage);
					a.setBackground(new Color(0,0,0,64));
		    		jp.add(a);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			titles.removeAllItems();
			titles.addItem("Error");
			e.printStackTrace();
		}
	}

}
