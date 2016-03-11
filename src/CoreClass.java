import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.*;

public class CoreClass {
	private static void createAndShowGUI() {
        JFrame frame = new JFrame("Client Tracker");
        frame.setSize(700, 700);
        frame.setContentPane(new GameScreen());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
