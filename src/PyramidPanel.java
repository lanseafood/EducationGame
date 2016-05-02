import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PyramidPanel extends JPanel {
	
	ArrayList<String> allAnimalNames;
    
    ArrayList<Polygon> pyramids = new ArrayList<Polygon>();
    
    HashMap<String, Point> ellipsePoints;
    
    Point[] startingPoints;
    
    GameScreen parent; 
    HashMap<String, Boolean> moveCircle;
    HashMap<String, Point> originalLocations;
    String recSpot = null;
    
    
    
    private int width = Utilities.small_width;
    private int height = Utilities.small_width;
    
    boolean firstDone = false;

    private MouseDrag mouseDrag;
    
    
    Image sparkle;
    boolean sparkleReady;
    Point sparklePoint = new Point();
    long startTime;
    long lastTime;
    static long SPARKLE_TIMER = 500;
    
    HashMap<String, Image> animalImages;
    
    
	float topBuffer = Utilities.small_width;
    BufferedImage pyramidRef;
    Image pyramidImage;

    
    boolean resized;
    float prop = 1;
    
    
    
    
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
            if (lastAnimal == null || lastAnimal.equals("")){
            	int trophic = isInsideShape(last);
            	parent.produceTrophicMemberPanel(trophic);
            	return;
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
        			ellipsePoints.remove(lastAnimal);
        			System.out.println(lastAnimal + " is in correct spot!");
        			
        			parent.produceQuestionPanel(lastAnimal);
        			
        			if (!firstDone){
        				firstDone = true;
        				parent.feedbackLabel.setText("Great!! Click on the pyramid block to see ones you've dragged correctly.");
        			}
        			
        			else {
        				parent.feedbackLabel.setText("That's correct!!");
        			}
        			
        			// Ready animation!!!!!!!!!!!
        			

					try {
						String soundName = "assets/sounds/e73003_se-b02.wav";
						AudioInputStream audioInputStream;
						audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			startTime = System.currentTimeMillis();
			    	sparkleReady = true;
			    	sparklePoint = newPoint;
        			
        			Timer timer = new Timer(0, new ActionListener() {
        			    public void actionPerformed(ActionEvent evt) {
        				
        			    	lastTime = System.currentTimeMillis();
        			    	

        			    	
        			    	if (lastTime - startTime > SPARKLE_TIMER){
        			    		sparkleReady = false;
        			    		((Timer)evt.getSource()).stop();
        			    	}
        			    	
        			    }    
        			});
        			
        			timer.setRepeats(true);
        			timer.start();
        			
        			parent.correctlyPlacedAnimals.add(lastAnimal);
        			
        			if (lastAnimal.equalsIgnoreCase(recSpot)){
        				recSpot = null;
        				return;
        			}
        			
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


    public PyramidPanel(String username, List<String> l, GameScreen parent) {

    	resized = true;
    	this.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				resized = true;
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});

    	
    	try {
			pyramidRef = (BufferedImage) ImageIO.read(new File("assets/FINALPYRAMID.png"));
			prop = (this.getHeight() - topBuffer) / pyramidRef.getHeight();

				
			pyramidImage = pyramidRef.getScaledInstance((int) (pyramidRef.getWidth() * prop), (int) (pyramidRef.getHeight() * prop), 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	
    	
    	
 
    	originalLocations = new HashMap<String, Point>();
    	animalImages = new HashMap<String, Image>();
    	
    	sparkleReady = false;
    	
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
    	
    	
    	
    	sparkle = (new ImageIcon("assets/animations/sparkle.gif")).getImage();
        
    	
    	this.parent = parent;
    	
    	ellipsePoints = new HashMap<String, Point>();
    	startingPoints = new Point[3];
    	startingPoints[0] = new Point(0, 0);
    	startingPoints[1] = new Point(4 * Utilities.small_width/2, 0);
    	startingPoints[2] = new Point(8 * Utilities.small_width/2, 0);
    	
    	

    	
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
    		if (p == null){
    			return null;
    		}
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
		
		Graphics2D g2d = (Graphics2D) g;
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint(Utilities.GRAYTEX);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setPaint(oldPaint);
		

		//draw the food chain
        // Top polygon is always a triangle. Bottom polygon is always a trapezoid. 
        
		// Add pyramid graphic:::: 

    	float topBuffer = Utilities.small_width;
    	if (resized){

			prop = (this.getHeight() - topBuffer) / pyramidRef.getHeight();	
			pyramidImage = pyramidRef.getScaledInstance((int) (pyramidRef.getWidth() * prop), (int) (pyramidRef.getHeight() * prop), 0);
    		resized = false;
    		
    		pyramids = new ArrayList<Polygon>();
        	Polygon[] polys = this.findPolygons(pyramidImage);
        	pyramids.add(polys[3]);
        	pyramids.add(polys[2]);
        	pyramids.add(polys[1]);
        	pyramids.add(polys[0]);
        	
        	
        	System.out.println(polys[0].xpoints[0]);
        	System.out.println(prop);
    	}
		g.drawImage(pyramidImage,  (int) (this.getWidth() - (pyramidRef.getWidth() * prop)) / 2, (int) this.topBuffer,(int) (pyramidRef.getWidth() * prop), (int) (pyramidRef.getHeight() * prop),null);

	
		/*
        int length = pyramids.size();
        
        for (int i = 0; i < length; ++i){
        	g.drawPolygon(pyramids.get(i));
        	drawCenteredText(g, pyramids.get(i).getBounds().x + pyramids.get(i).getBounds().width/2, pyramids.get(i).getBounds().y + pyramids.get(i).getBounds().height/2, 10, "" + (i + 1));
        	
        }
        
    	*/
	
        
        //draw the ecology options
        Iterator<String> iter = moveCircle.keySet().iterator();
        while (iter.hasNext()) {
        	String name = iter.next();
        	if (moveCircle.get(name)){
        		Point p = ellipsePoints.get(name);
            	if (p != null){
          
            		if (animalImages.get(name) != null){
                		g.drawImage(animalImages.get(name), (int) p.getX(), (int) p.getY(), width, height, null);
                	}
                	else {
                		drawCenteredText(g, (int) p.getX() +(width/2), (int) p.getY() +(height/2), 10 , name);
                	}
            	}
        	}
        	
        	
        	
        	
        	
        	
        	g.setColor(Color.BLACK);
        	

        }
        if (sparkleReady){
        	
        	
        	int width = sparkle.getWidth(null);
        	int height = sparkle.getWidth(null);
        	
        	g.drawImage(sparkle, sparklePoint.x - width/2, sparklePoint.y - height/2, this);
        	
        	
        }

    }
    
    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
    	if (text == null){
    		return;
    	}
    	
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
    
    
    public Polygon[] findPolygons(Image pyramidImage){

    	int xShift = (int) (this.getWidth() - (pyramidRef.getWidth() * prop)) / 2;
    	int yShift = (int) this.topBuffer;
    	Polygon[] answer = new Polygon[4];
    	
    	Polygon first = new Polygon();
    	int width = (int) (pyramidImage.getWidth(null));
    	int height = (int) (pyramidImage.getHeight(null));
    	
    	first.addPoint(width/2 + xShift, 0 + yShift);
    	first.addPoint(width/2 - width/8 + xShift, height/4 + yShift);
    	first.addPoint(width/2 + width/8 + xShift, height/4 + yShift);
    	
    	Polygon second = new Polygon();
    	second.addPoint(width/2 - width/8 + xShift, height/4 + yShift);
    	second.addPoint(width/2 + width/8 + xShift, height/4 + yShift);
    	second.addPoint(width/2 + width/4 + xShift, height/2 + yShift);
    	second.addPoint(width/2 - width/4 + xShift, height/2 + yShift);

    	
    	Polygon third = new Polygon();
    	third.addPoint(width/2 - width/4 + xShift, height/2 + yShift);
    	third.addPoint(width/2 + width/4 + xShift, height/2 + yShift);
    	third.addPoint(width/2 + 3 * width/8 + xShift, 3 * height/4 + yShift);
    	third.addPoint(width/2 - 3 * width/8 + xShift, 3 * height / 4 + yShift);


    	Polygon fourth = new Polygon();
    	fourth.addPoint(width/2 - 3*width/8 + xShift, 3*height/4 + yShift);
    	fourth.addPoint(width/2 + 3*width/8 + xShift, 3*height/4 + yShift);
    	fourth.addPoint(width/2 + width/2 - 1 + xShift, height-1 + yShift);
    	fourth.addPoint(width/2 - width/2 + xShift, height-1 + yShift);

    	
    	answer[0] = first;
    	answer[1] = second;
    	answer[2] = third;
    	answer[3] = fourth;
    	return answer;
    
    }
    
    public void updateRecSpot(String animal){
    	
    	if (recSpot != null){
    		ellipsePoints.remove(recSpot);
    		originalLocations.remove(recSpot);
    		moveCircle.remove(recSpot);
    		allAnimalNames.add(recSpot);
    	}
    	
    	ellipsePoints.put(animal, startingPoints[2]);
    	recSpot = animal;
    	
    	originalLocations.put(animal, startingPoints[2]);
    	moveCircle.put(animal, true);
    	
    	allAnimalNames.remove(animal);

    }
    

}