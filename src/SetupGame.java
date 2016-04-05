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
		ecology = Arrays.asList("Vulture", "Lion", "Elephant", "Giraffe", "Acacia", "WildFruit");
		
		JPanel panel = new PyramidPanel(ecology); //JPanel();
		
		return panel;
	}
	
	public JPanel getQuestionPanel() {
		JPanel panel = new JPanel();
		JLabel l = new JLabel("Museum Data");
		panel.setLayout(new FlowLayout());
		
		panel.add(l);
		return panel;
	}
	
	
}
