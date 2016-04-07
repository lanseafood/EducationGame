import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class PyramidMasterPanel extends JPanel {

	
	public PyramidPanel pyramidPanel;
	public JComponent questionPanel;
	String username;
	HashMap<String, Boolean> answered;
	
	ArrayList<String> finishedAnimals;
	ArrayList<String> correctlyPlacedAnimals;
	
	public PyramidMasterPanel(String username, List<String> animals){
		this.username = username;
		this.answered = new HashMap<String, Boolean>();
		this.finishedAnimals = new ArrayList<String>();
		this.correctlyPlacedAnimals = new ArrayList<String>();
		
		
		BorderLayout layout = new BorderLayout();
		pyramidPanel = new PyramidPanel(username, animals, this);
		questionPanel = new JPanel();
		this.setLayout(layout);
		this.add(pyramidPanel, BorderLayout.CENTER);
		this.add(new JButton(), BorderLayout.WEST);
		this.add(questionPanel, BorderLayout.EAST);
		
	}
	
	public void produceQuestionPanel(String animalName){
		this.remove(questionPanel);
		questionPanel = new QuestionPanel(animalName, username, this);
		this.add(questionPanel, BorderLayout.EAST);
		repaint();
		revalidate();
	}
	
	public void removeQuestionPanel(){
		this.remove(questionPanel);
		repaint();
		revalidate();
	}
	
	public void produceTrophicMemberPanel(int trophicLevel){
		
		if (trophicLevel <= 0 || trophicLevel > 4)
			return;
		
		this.remove(questionPanel);
		questionPanel = new TrophicMemberPanel(trophicLevel, this);
		this.add(questionPanel, BorderLayout.EAST);
		repaint();
		revalidate();
	}
	
	
}
