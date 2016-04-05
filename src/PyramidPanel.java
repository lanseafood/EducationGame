import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class PyramidPanel extends JPanel {

	List<String> ecology = new ArrayList<String>();
	List<String> ecologyAnswers = new ArrayList<String>();
    
    ArrayList<Polygon> pyramids = new ArrayList<Polygon>();
    
    int[] xEcology = {400, 400, 400, 400, 400, 400, 400};
    int[] yEcology = {25, 100, 175, 250, 325, 400, 475};
    
    PyramidMasterPanel parent; 
    Boolean[] drawCircle;
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
            lastNumber = isInsideEllipse(last, xEcology, yEcology);
            if (lastNumber != -1 && drawCircle[lastNumber]){
            	dragging = lastNumber != -1;
            } else {
            	dragging = false;
            	
            	// This animal is ready to have questions answered for it 
            	if (lastNumber != -1){
            		String animal = ecology.get(lastNumber);
            		parent.produceQuestionPanel(animal);
            		
            	}
            }
        }

        @Override
        public void mouseReleased(MouseEvent m) {
            //check if the moved circle overlaps a main circle
        	if (lastNumber != -1 && dragging != false) {
        		Point newPoint = m.getPoint();
        		int newNumber = isInsideShape(newPoint);
        		if (newNumber != -1 && ellipseTrophicLevel(lastNumber) == newNumber) {//ie, it has stopped inside a circle of the food chain
        			ecologyAnswers.set(newNumber, ecology.get(newNumber));
        			drawCircle[lastNumber] = false;
        			System.out.println(ecology.get(lastNumber));
        		}
        	}
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


    public PyramidPanel(String username, List<String> l, PyramidMasterPanel parent) {

    	this.parent = parent;
    	
    	Point[] poly1 = {new Point(50, 400), new Point(350, 400), new Point(300, 300), new Point(100, 300)};
    	Polygon p1 = new Polygon();
    	int j = 0;
    	while (j < poly1.length){
    		p1.addPoint((int) poly1[j].getX(), (int) poly1[j].getY());
    		j++;
    	}
    	
    	
    	
    	Point[] poly2 = {new Point(100, 300), new Point(300, 300), new Point(250, 200), new Point(150, 200)};
    	Polygon p2 = new Polygon();
    	j = 0;
    	while (j < poly2.length){
    		p2.addPoint((int) poly2[j].getX(), (int) poly2[j].getY());
    		j++;
    	}
    	
    	
    	Point[] poly3 = {new Point(150, 200), new Point(250, 200), new Point(200, 100)};
    	Polygon p3 = new Polygon();
    	j = 0;
    	while (j < poly3.length){
    		p3.addPoint((int) poly3[j].getX(), (int) poly3[j].getY());
    		j++;
    	}
    	
    	Point[] poly4 = {new Point(50, 400), new Point(350, 400), new Point(400, 500), new Point(00, 500)};
    	Polygon p4 = new Polygon();
    	j = 0;
    	while (j < poly4.length){
    		p4.addPoint((int) poly4[j].getX(), (int) poly4[j].getY());
    		j++;
    	}
    	
    	pyramids.add(p4);
    	pyramids.add(p1);
    	pyramids.add(p2);
    	pyramids.add(p3);
    	
    	
    	long seed = System.nanoTime();
    	Collections.shuffle(l, new Random(seed));
        ecology.addAll(l);
        
        drawCircle = new Boolean[ecology.size()];
        for (int i = 0; i < ecology.size(); i++) {
        	ecologyAnswers.add("");
        	drawCircle[i] = true;
        }
    	setBackground(Color.WHITE);
        mouseDrag = new MouseDrag();
        addMouseListener(mouseDrag);
        addMouseMotionListener(mouseDrag);
    }

    public int isInsideShape(Point point) {
    	//System.out.println(point);
    	int shapeNumber = -1;
    	for (int i = 0; i < pyramids.size(); i++) {
    		if(pyramids.get(i).contains(point)){
    			shapeNumber = i + 1;
    			//System.out.println(shapeNumber);	
    		}
    		
    	}
    	return shapeNumber;
    }
    
    public int isInsideEllipse(Point point, int[] xArray, int[] yArray) {
    	
    	int circleNumber = -1;
    	for (int i = 0; i < xArray.length; i++) {
    		if( new Ellipse2D.Float(xArray[i], yArray[i], width, height).contains(point))
    			circleNumber = i;
    	}
    	return circleNumber;
    }
    
    public int ellipseTrophicLevel(int i) {
    	
    	int circleNumber = -1;
    	
    	String animalName = ecology.get(i);
    	SQLiteJDBC db = new SQLiteJDBC();
    	try {
    		//db.print_Ecology();
			int id = db.get_Ecology_ID(animalName);
			circleNumber = db.get_Ecology_Tropic(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
    			
    		
    	return circleNumber;
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCenteredText(g, getWidth()/2, 500, 20 , "Drag and drop organisms to their correct trophic levels!");
        
        //draw the food chain
        // Top polygon is always a triangle. Bottom polygon is always a trapezoid. 
        
        int length = pyramids.size();
        
        for (int i = 0; i < length; ++i){
        	g.drawPolygon(pyramids.get(i));
        	drawCenteredText(g, pyramids.get(i).getBounds().x + pyramids.get(i).getBounds().width/2, pyramids.get(i).getBounds().y + pyramids.get(i).getBounds().height/2, 10, "" + (i + 1));
        	
        }
        
        
        //draw the ecology options
        for (int i = 0; i < ecology.size(); i++) {
        	if (!drawCircle[i]){
        		g.setColor(Color.GREEN);
        	}
        	g.drawOval(xEcology[i], yEcology[i], width, height);
        	drawCenteredText(g, xEcology[i]+(width/2), yEcology[i]+(height/2), 10 , ecology.get(i));
        	g.setColor(Color.BLACK);
        	
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