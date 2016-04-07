import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetupGame{
	List<String> ecology = new ArrayList<String>();
	public JPanel getFoodChainPanel() {
		//TODO: change this to read from database
		ecology = Arrays.asList("King Vulture", "Lion", "Elephant", "Giraffe", "Acacia", "Baobab");
		
		JPanel panel = new PyramidMasterPanel("Simba", ecology); //JPanel();
		
		return panel;
	}
	
	public JPanel getQuestionPanel() {
		JPanel panel = new JPanel();
		JLabel l = new JLabel("Museum Data");
		panel.setLayout(new FlowLayout());
		//ProfilePanel profPanel = new ProfilePanel("Simba", null, null);
		
		panel.add(l);
		//panel.add(profPanel);
		return panel;
	}
	
	
}
