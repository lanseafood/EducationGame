import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.sql.SQLException;
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
	public PyramidMasterPanel getFoodChainPanel() {
		//TODO: change this to read from database
		ecology = Arrays.asList("King Vulture", "Lion", "Elephant", "Giraffe", "Acacia", "Baobab");
		
		PyramidMasterPanel panel = new PyramidMasterPanel("Simba", ecology); //JPanel();
		
		return panel;
	}
	
	public ProfilePanel getQuestionPanel(PyramidMasterPanel p) {
		
		SQLiteJDBC db = new SQLiteJDBC();
		String data = "";
		try {
			data = db.get_Question_Data(p.username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProfilePanel panel = new ProfilePanel(p.username, null, data);
		
		
		//panel.add(profPanel);
		return panel;
	}
	
	
}
