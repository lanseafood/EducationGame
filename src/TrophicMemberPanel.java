import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class TrophicMemberPanel extends JScrollPane{

	public TrophicMemberPanel(int trophicLevel, PyramidMasterPanel parent){
		
		
		ArrayList<String> correctlyPlacedAnimals = parent.correctlyPlacedAnimals;
		ArrayList<String> finishedAnimals = parent.finishedAnimals;
		
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
		
		
		Iterator<String> iter = correctlyPlacedAnimals.iterator();
		while (iter.hasNext()){
			JLabel label = new JLabel();
			String animalName = iter.next();
			if (getTrophicLevel(animalName) == trophicLevel){
		    	Image image;
		    	try {
					image = ImageIO.read(new File("assets/" + animalName.toLowerCase() + ".png"));
					image = image.getScaledInstance(300, 300, 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					image = new BufferedImage ( 300, 300, BufferedImage.TYPE_INT_ARGB );
					Graphics g = image.getGraphics();
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, 300, 300);
					g.setColor(Color.BLACK);
					g.drawOval(0, 0, 300, 300);
					
					
					
					Utilities.drawCenteredText(g, 150, 150, 30, animalName);
					
					g.dispose();
					
				}
		    	
		    	ImageIcon icon = new ImageIcon(image);
		    	
		    	label.setIcon(icon);
		    	
		    	if (finishedAnimals.contains(animalName)){
		    		label.setOpaque(true);
		    		label.setBackground(Color.GREEN);
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
		
		

	
}
