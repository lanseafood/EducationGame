import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;


public class WatsonPanel extends JPanel{

	public WatsonPanel(){
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		
		JLabel name = new JLabel("Watson Toolbar");
		JTextField questionField = new JTextField();
		questionField.setText("Ask me a question!");
		questionField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		questionField.setColumns(40);
		
		
		JTextArea answerField = new JTextArea();
		answerField.setColumns(40);
		
		JScrollPane answerPane = new JScrollPane(answerField);
		
		JButton submit = new JButton("Ask");
		
		questionField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				submit.doClick();
				
			}
			
		});
		
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						answerField.setText("Waiting for response...");
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
		
		answerField.setRows(5);
		answerField.setLineWrap(true);
		answerField.setWrapStyleWord(true);
		answerField.setEditable(false);
		answerField.setDisabledTextColor(Color.BLACK);
		
		answerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(name);
		add(questionField);
		add(answerPane);
		add(submit);
		
	}
}
