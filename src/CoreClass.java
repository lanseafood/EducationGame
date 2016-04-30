import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class CoreClass {
	private static void createAndShowGUI() {
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/bebas_neue/BebasNeue.otf")));
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
        JFrame frame = new JFrame("Safari Watson");
        

        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width - 200, screenSize.height - 200);
        frame.setContentPane(new GameScreen());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setLocationByPlatform(true);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Welcome, Simba! Please drag and drop organisms into their correct trophic level on the pyramid. \n\n"
        		+ "If you don't know what a trophic level is, try asking Watson using the toolbar at the bottom! \n"
        		+ "As a matter of fact, you can ask Watson questions about anything you find confusing in the game. "
        		+ "\nHe can tell you more about an organism you're interested in learning about. "
        		+ "\nDon't know what an autotroph is? Watson to the rescue!");
        
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
