import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


public class TrophicMemberPanel extends JScrollPane{

	GameScreen parent;
	public TrophicMemberPanel(int trophicLevel, GameScreen parent){
		
		
		this.parent = parent;
		JPanel outerPanel = new JPanel(){ 
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Utilities.paintComponent(g, this);

		    }
		};
		
		
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));

		
		if (trophicLevel == -1 || this.getTrophicLevelMembersAnswered(trophicLevel) == 0){
			JLabel label = new JLabel(){
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Utilities.paintComponent(g, this);

			    }
			};
			BufferedImage image = new BufferedImage ( Utilities.east_width, 1, BufferedImage.TYPE_INT_ARGB );

			ImageIcon icon = new ImageIcon(image);
			
			label.setIcon(icon);
			
			outerPanel.add(label);
			add(outerPanel);
			

			this.setViewportView(outerPanel);
			
			return;
		
		}
		
		
		TreeSet<String> correctlyPlacedAnimals = parent.correctlyPlacedAnimals;
		TreeSet<String> finishedAnimals = parent.finishedAnimals;
		
		parent.feedbackLabel.setText("Click on an icon to answer questions about it!");
		
		
		Iterator<String> iter = correctlyPlacedAnimals.iterator();
		while (iter.hasNext()){
			JLabel label = new JLabel();
			String animalName = iter.next();
			if (getTrophicLevel(animalName) == trophicLevel){
		    	Image image;
		    	try {
					image = ImageIO.read(new File("assets/" + animalName.toLowerCase() + ".png"));
					image = image.getScaledInstance(Utilities.big_width, Utilities.big_width, 0);
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
					
				}
		    	
		    	ImageIcon icon = new ImageIcon(image);
		    	
		    	label.setIcon(icon);
		    	label.setBorder(new EmptyBorder(10, 0, 10, 0));
		    	
		    	if (finishedAnimals.contains(animalName)){
		    		label.setOpaque(true);
		    		label.setBackground(Utilities.GREEN);
		    	}

		    	MouseListener listener = new MouseAdapter() {
		    	    public void mouseClicked(MouseEvent e) {
		    	      parent.produceQuestionPanel(animalName);
		    	    }};
		    	    
		    	    label.addMouseListener(listener);
		    	  
		    	
		    	outerPanel.add(label);
			}
		}
			
		
			this.setViewportView(outerPanel);
			this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
	}
	

	public int getTrophicLevel(String animalName){
		SQLiteJDBC db = new SQLiteJDBC();
		int trophicLevel = -1;
		try {
			int animal_id = db.get_Ecology_ID(animalName);
			trophicLevel = db.get_Ecology_Tropic(animal_id);
		} catch (Exception e){
			
	}
		return trophicLevel;
	}
	
	public int getTrophicLevelMembersAnswered(int trophic){
		SQLiteJDBC db = new SQLiteJDBC();
		int count = 0;
		Iterator<String> iter = this.parent.correctlyPlacedAnimals.iterator();
		
		try {
			while (iter.hasNext()){
				String animalName = iter.next();
				if (getTrophicLevel(animalName) == trophic){
					count++;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return count;
	}
		
		
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Utilities.paintComponent(g, this);

    }

	
}
