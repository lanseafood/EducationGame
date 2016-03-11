import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

public class GameScreen extends Container {
	public static Boolean card1Showing = true;
	GameScreen() {
		//layout is BorderLayout
		setLayout(new BorderLayout());
		EmptyBorder border = new EmptyBorder(10,10,10,10);
		
		//add basic empty panels to the North, Center and South
		JPanel north = new JPanel();
		add(BorderLayout.NORTH, north);
		north.setBorder(border);
		north.setBackground(Color.WHITE);
		
		JPanel south = new JPanel();
		add(BorderLayout.SOUTH, south);
		south.setBorder(border);
		south.setBackground(Color.BLUE);
		
		JPanel center = new JPanel();
		add(BorderLayout.CENTER, center);
		center.setBorder(border);
		center.setBackground(Color.ORANGE);
		
		//add labels to each
				
		north.setLayout(new FlowLayout());//BoxLayout(north, BoxLayout.Y_AXIS));
		JLabel labelNorth = new JLabel("North");
		north.add(labelNorth);
		
		south.setLayout(new FlowLayout());//BoxLayout(south, BoxLayout.Y_AXIS));
		JLabel labelSouth = new JLabel("South");
		south.add(labelSouth);
		
		
		//set the card layout
		SetupGame sg = new SetupGame();
		JPanel card1 = sg.getFoodChainPanel();
		JPanel card2 = sg.getQuestionPanel();
		
		center.setLayout(new CardLayout());
		center.add(card1, "1");
		center.add(card2, "2");
		card1.setVisible(true);
		
		JButton btn1 = new JButton("Show Card 2");
        btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (card1Showing) {
					card2.setVisible(true);
					card1.setVisible(false);
					card1Showing = false;
					btn1.setText("Show Card 1");
				}
				else {
					card2.setVisible(false);
					card1.setVisible(true);
					card1Showing = true;
					btn1.setText("Show Card 2");
				}
			}
        });
        
        south.add(btn1);
		//center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		//JLabel labelCenter = new JLabel("Center");
		//center.add(labelCenter);
		
	}

}
