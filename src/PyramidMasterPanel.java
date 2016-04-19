import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class PyramidMasterPanel extends JPanel {

	
	public PyramidPanel pyramidPanel;
	public JComponent questionPanel;
	public String username;
	public HashMap<String, Boolean> answered;
	
	public HashMap<Integer, Boolean> answeredIDs;
	
	
	
	public TreeSet<String> finishedAnimals;
	public TreeSet<String> correctlyPlacedAnimals;
	
	public PyramidMasterPanel(String username, List<String> animals){
		this.username = username;
		this.answered = new HashMap<String, Boolean>();
		this.answeredIDs = new HashMap<Integer, Boolean>();
		this.finishedAnimals = new TreeSet<String>();
		this.correctlyPlacedAnimals = new TreeSet<String>();
		
		Utilities.loadGame(this, username);
		
		
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
