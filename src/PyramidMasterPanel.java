import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;


public class PyramidMasterPanel extends JPanel {

	
	public PyramidPanel pyramidPanel;
	public JPanel questionPanel;
	String username;
	
	public PyramidMasterPanel(String username, List<String> animals){
		this.username = username;
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
	
	
}
