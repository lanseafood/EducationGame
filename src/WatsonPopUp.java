import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;


public class WatsonPopUp extends JDialog{

	public WatsonPopUp(){
		setTitle("Ask Watson!");
		
		JPanel bottom = new JPanel();
		GridLayout grid = new GridLayout(1, 2);
		grid.setHgap(5);
		bottom.setLayout(grid);
		
		EmptyBorder buttonBorder = new EmptyBorder(10,5,10,5);
	
		JButton firstButton = new JButton("Ask");
		JButton secondButton = new JButton("Cancel");
		bottom.add(firstButton);
		bottom.add(secondButton);
		bottom.setBorder(buttonBorder);
		
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1,2));
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
		JLabel email = new JLabel("Question: ");
		EmptyBorder labelBorder = new EmptyBorder(10, 10, 30, 50);
		email.setBorder(labelBorder);
		left.add(email);
		JLabel phone = new JLabel("Answer: ");
		phone.setBorder(labelBorder);
		left.add(phone);
		
		
		
		JPanel right = new JPanel();
		GridLayout rightLayout = new GridLayout(2, 1);
		rightLayout.setVgap(15);
		right.setLayout(rightLayout);
		
		
		
		final JTextField questionField = new JTextField(30);
		final JTextArea answerField = new JTextArea(5, 30);
		
		final JScrollPane pane = new JScrollPane(answerField);
		answerField.setEditable(false);
		answerField.setLineWrap(true);
		answerField.setWrapStyleWord(true);
		final WatsonPopUp d = this;
		
		firstButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						
						String answer = WatsonAPI.askWatson(questionField.getText());
						answerField.setText(answer);
						
						return null;
					}
					
					@Override
					protected void done(){
						
					}
					
				};
				
				
				try {
					worker.execute();
				} catch (Exception e){
					answerField.setText("Error querying Watson.");
				}
				
			}
			
		});
		
		
		secondButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
			}
			
		});
		
		
		right.add(questionField);
		right.add(pane);
		
		top.add(left);
		top.add(right);
		
		setLayout(new GridLayout(3,1));
		
		JLabel descript = new JLabel("Ask Watson a question!");
		descript.setHorizontalAlignment(JLabel.CENTER);
		add(descript);
		add(top);
		add(bottom);
		setLocationByPlatform(true);
		setAlwaysOnTop(true);
		pack();

	}
	
	
	
}
