import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FoodChainPanel extends JPanel {

	List<String> ecology = new ArrayList<String>();
	int[] xChain = {20, 120, 200, 200, 280, 280};
    int[] yChain = {75, 75, 25, 125, 25, 125};
    
    int[] xEcology = {400, 400, 400, 400, 400, 400, 400};
    int[] yEcology = {25, 100, 175, 250, 325, 400, 475};
    
    private int width = 50;
    private int height = 50;

    private MouseDrag mouseDrag;
    
    private final class MouseDrag extends MouseAdapter {
        private boolean dragging = false;
        private Point last;
        private int lastNumber = -1;

        @Override
        public void mousePressed(MouseEvent m) {
            last = m.getPoint();
            //if == -1, then not dragging any circle
            lastNumber = isInsideEllipse(last);
            dragging = lastNumber != -1;
        }

        @Override
        public void mouseReleased(MouseEvent m) {
            last = null;
            dragging = false;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent m) {
        	if (lastNumber != -1) {
	            if (dragging) {
	                xEcology[lastNumber] = m.getX();
	                yEcology[lastNumber] = m.getY();
	            } 
	            last = m.getPoint();
        	}
            repaint();
        }
    }


    public FoodChainPanel(List<String> l) {
        ecology.addAll(l);
    	setBackground(Color.WHITE);
        mouseDrag = new MouseDrag();
        addMouseListener(mouseDrag);
        addMouseMotionListener(mouseDrag);
    }

    public int isInsideEllipse(Point point) {
    	Boolean returnValue = false;
    	int circleNumber = -1;
    	for (int i = 0; i < xEcology.length; i++) {
    		if( new Ellipse2D.Float(xEcology[i], yEcology[i], width, height).contains(point))
    			circleNumber = i;
    	}
    	return circleNumber;
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //draw the food chain
        for (int i = 0; i < ecology.size(); i++) {
        	g.drawOval(xChain[i], yChain[i], width, height);
        }
        
        //draw the ecology options
        for (int i = 0; i < ecology.size(); i++) {
        	g.drawOval(xEcology[i], yEcology[i], width, height);
        	drawCenteredText(g, xEcology[i]+(width/2), yEcology[i]+(height/2), 10 , ecology.get(i));
        }
    }
    
    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
    	// Create a new font with the desired size
    	Font newFont = g.getFont().deriveFont(size);
    	g.setFont(newFont);
    	// Find the size of string s in font f in the current Graphics context g.
    	FontMetrics fm = g.getFontMetrics();
    	java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

    	int textHeight = (int) (rect.getHeight());
    	int textWidth = (int) (rect.getWidth());

    	// Find the top left and right corner
    	int cornerX = x - (textWidth / 2);
    	int cornerY = y - (textHeight / 2) + fm.getAscent();

    	g.drawString(text, cornerX, cornerY);  // Draw the string.
    	}

}