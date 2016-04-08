import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PyramidPanel extends JPanel {
	
	ArrayList<String> allAnimalNames;
    
    ArrayList<Polygon> pyramids = new ArrayList<Polygon>();
    
    HashMap<String, Point> ellipsePoints;
    
    Point[] startingPoints;
    
    PyramidMasterPanel parent; 
    HashMap<String, Boolean> moveCircle;
    HashMap<String, Point> originalLocations;
    
    private int width = Utilities.small_width;
    private int height = Utilities.small_width;

    private MouseDrag mouseDrag;
    
    
    HashMap<String, Image> animalImages;
    
    
    private final class MouseDrag extends MouseAdapter {
        private boolean dragging = false;
        private Point last;
        private String lastAnimal = "";

        @Override
        public void mousePressed(MouseEvent m) {
            last = m.getPoint();
            //if == -1, then not dragging any circle
            lastAnimal = isInsideEllipse(last);
            
            // Check if in trophic thingy
            if (lastAnimal.equals("")){
            	int trophic = isInsideShape(last);
            	parent.produceTrophicMemberPanel(trophic);
            }
            
            
            if (!lastAnimal.equals("") && moveCircle.get(lastAnimal)){
            	dragging = lastAnimal != "";
            } else {
            	dragging = false;
            	
            	// This animal is ready to have questions answered for it 
            	if (!lastAnimal.equals("")){
            		parent.produceQuestionPanel(lastAnimal);
            		
            	}
            }
        }

        @Override
        public void mouseReleased(MouseEvent m) {
            //check if the moved circle overlaps a main circle
        	if (!lastAnimal.equals("") && dragging != false) {
        		Point newPoint = m.getPoint();
        		int newNumber = isInsideShape(newPoint);
        		if (newNumber != -1 && ellipseTrophicLevel(lastAnimal) == newNumber) {//ie, it has stopped inside a circle of the food chain
        			//ecologyAnswers.set(newNumber, ecology.get(newNumber));
        			moveCircle.put(lastAnimal, false);
        			System.out.println(lastAnimal + " is in correct spot!");
        			
        			parent.correctlyPlacedAnimals.add(lastAnimal);
        			
        			int randomIndex = (int) Math.floor(allAnimalNames.size() * Math.random());
        			
        			if (allAnimalNames.size() != 0){
        				String newAnimal = allAnimalNames.get(randomIndex);
        				System.out.println(newAnimal + " added!");
        				Point oldPoint = originalLocations.get(lastAnimal);
        				ellipsePoints.put(newAnimal, oldPoint);
        				originalLocations.put(newAnimal, oldPoint);
        				allAnimalNames.remove(newAnimal);
        				moveCircle.put(newAnimal, true);
        				
        			}
        			
        			
        		}
        	}
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent m) {
        	if (!lastAnimal.equals("")) {
	            if (dragging) {
	            	Point newP = new Point(m.getX(), m.getY());
	            	newP.setLocation(newP.x - Utilities.small_width/2, newP.y - Utilities.small_width/2);
	                ellipsePoints.put(lastAnimal, newP);
	            } 
	            last = m.getPoint();
        	}
            repaint();
        }
    }


    public PyramidPanel(String username, List<String> l, PyramidMasterPanel parent) {

    	originalLocations = new HashMap<String, Point>();
    	animalImages = new HashMap<String, Image>();
    	
    	SQLiteJDBC db = new SQLiteJDBC();
    	try {
			allAnimalNames = db.retrieve_Ecology();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			allAnimalNames = new ArrayList<String>();
		}
    	
    	Iterator<String> iter1 = allAnimalNames.iterator();
    	while (iter1.hasNext()){
    		String animalName = iter1.next();
        	Image image;
        	try {
				image = ImageIO.read(new File("assets/" + animalName.toLowerCase() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				image = null;
			}
        	
    		animalImages.put(animalName, image);
    	}
    	
    	
    	
    	
    	
    	this.parent = parent;
    	
    	ellipsePoints = new HashMap<String, Point>();
    	startingPoints = new Point[7];
    	startingPoints[0] = new Point(400, 25);
    	startingPoints[1] = new Point(400, 100);
    	startingPoints[2] = new Point(400, 175);
    	startingPoints[3] = new Point(400, 250);
    	startingPoints[4] = new Point(400, 325);
    	startingPoints[5] = new Point(400, 400);
    	startingPoints[6] = new Point(400, 475);
    	
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
        
        moveCircle = new HashMap<String, Boolean>();
        for (int i = 0; i < l.size(); i++) {
        	
        	ellipsePoints.put(l.get(i), startingPoints[i]);
        	originalLocations.put(l.get(i), startingPoints[i]);
        	moveCircle.put(l.get(i), true);
        	
        	allAnimalNames.remove(l.get(i));
        	

        	
        	
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
    
    public String isInsideEllipse(Point point) {
    	
    	String circleName = "";
    	
    	Iterator<String> iter = ellipsePoints.keySet().iterator();
    	while (iter.hasNext()) {
    		String name = iter.next();
    		Point p = ellipsePoints.get(name);
    		if( new Ellipse2D.Float(p.x, p.y, width, height).contains(point))
    			circleName = name;
    	}
    	return circleName;
    }
    
    public int ellipseTrophicLevel(String name) {
    	
    	int circleNumber = -1;
    	
    	String animalName = name;
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
        Iterator<String> iter = moveCircle.keySet().iterator();
        while (iter.hasNext()) {
        	String name = iter.next();
        	if (!moveCircle.get(name)){
        		g.setColor(Color.GREEN);
        	}
        	Point p = ellipsePoints.get(name);
        	if (p != null){
        		g.drawOval((int) p.getX(), (int) p.getY(), width, height);
        		
        		if (animalImages.get(name) != null){
            		g.drawImage(animalImages.get(name), (int) p.getX(), (int) p.getY(), width, height, null);
            	}
            	else {
            		drawCenteredText(g, (int) p.getX() +(width/2), (int) p.getY() +(height/2), 10 , name);
            	}
        	}
        	
        	
        	
        	
        	
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