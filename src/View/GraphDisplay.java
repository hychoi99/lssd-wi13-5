/**
 * Plots points from data from a datamanager
 * Allows user to click on empty space to create a new point
 * Allows user to click on a point and drag it to a new position
 * Keyboard events allow user to input axis and title
 * as well as saving the entire data
 * 
 * @author hychoi
 */
package View;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.*;

import javax.swing.*;
import Model.*;

public class GraphDisplay extends JComponent implements dataManagerListener, MouseListener, KeyListener{

	Vector<GraphData> setOfData;
	private static final int BUFFER = 50;
	private static final int dotRadius = 4;
	protected Vector<RoundRectangle2D> plotsVector;
	private boolean dragging = false;
	private boolean displayLines = true;
	private boolean displayPoints = true;
    private Point last;
    private Point released;
    private int clickedPointIndex;
    private int width;
    private int height;
    private double maxValue;
    private int setIndex = 0;
    SetsOfGraphData dm;
    Scanner scanner;
	
	/**
	 * Constructs a new graphdisplay object and takes in a new set
	 * of data from a datamanager object
	 */
	public GraphDisplay() {

		scanner = new Scanner(System.in);
		dm = new SetsOfGraphData();
	}
	
	/**
	 * Creates the initial frame and initializes the event listeners
	 */
	public void makeFrame() {

		JFrame graphFrame = new JFrame("Graph Plot Display Window");
		graphFrame.getContentPane().add(this);
        graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphFrame.setSize(600, 499);
        graphFrame.setLocation(200,200);
        graphFrame.setVisible(true);
        graphFrame.addKeyListener(this);
        addMouseListener(this);
        dm.addListener(this);
        System.out.println("Press the spacebar to display or not display lines");
        System.out.println("Press \'p\' to display or not display points");
        System.out.println("Press \'s\' to save the data");
        System.out.println("Press \'a\' to enter Axis names and Title");
	}
	
	/**
	 * Creates the graph with the plots and lines connecting the plots
	 */
	//@Override
	public void paintComponent(Graphics g) {
		
		setOfData = (Vector<GraphData>)dm.getSet();
		super.paintComponent(g);
		//t.draw(g);
        width = getWidth();
        height = getHeight();
        g.drawLine(width/2, BUFFER, width/2, height-BUFFER);
        g.drawLine(BUFFER, height/2, width-BUFFER, height/2);
        g.setColor(Color.red);
        maxValue = 1.0;
        for(int j = 0; j < setOfData.size(); j++){
        	for(int i = 0; i < setOfData.get(j).getData().size(); i++){
        		if(Math.abs(setOfData.get(j).getPlot(i)) > maxValue){
        			maxValue = Math.abs(setOfData.get(j).getPlot(i));
        		}
        	}
        }
        double xScale = (width/2 - BUFFER) / maxValue;
        double yScale = (height/2 - BUFFER) / maxValue;
        int x0 = width/2;
        int y0 = height/2;

        if(dm.getTitle() != null){
        	g.drawString(dm.getTitle(), width/2, BUFFER/2);
        	g.drawString(dm.getXAxis(), BUFFER/2, height/2);
        	g.drawString(dm.getYAxis(setIndex), width/2, height-(BUFFER/2));
        }
       
        if (displayPoints)
        {
            plotsVector = new Vector<RoundRectangle2D>();
        	for (int i = 0; i < setOfData.size(); i++) {
        		
            for (int j = 0; j < setOfData.get(i).getData().size(); j=j+2)
            {
                double x = x0 + (xScale * setOfData.get(i).getData().get(j));
                double y = y0 - (yScale * setOfData.get(i).getData().get(j+1));
                RoundRectangle2D.Double shape = new RoundRectangle2D.Double(x-dotRadius, y-dotRadius, 2*dotRadius, 2*dotRadius, 50, 50);
                plotsVector.add(shape);
                Graphics2D g2 = (Graphics2D)g;
                g2.draw(shape);
                DecimalFormat myFormat = new DecimalFormat("0.00");
                g.drawString(myFormat.format(setOfData.get(i).getData().get(j)) + ", " + 
                		myFormat.format(setOfData.get(i).getData().get(j+1)), (int)x+dotRadius, (int)y);
            }
        	}
        }
        if (displayLines)
        {
            for (int j = 0; j < plotsVector.size()-1; j++)
            {
                int x1 = (int)(plotsVector.elementAt(j).getX() + dotRadius);
                int y1 = (int)(plotsVector.elementAt(j).getY() + dotRadius);
                int x2 = (int)(plotsVector.elementAt(j+1).getX() + dotRadius);
                int y2 = (int)(plotsVector.elementAt(j+1).getY() + dotRadius);
                g.drawLine(x1, y1, x2, y2);
            }
        }
	}
	
	private void getTitleAndAxis() {
		System.out.println("Enter the title: ");
		String title = scanner.nextLine();
		System.out.println("Enter xAxis: ");
		String xAxis = scanner.nextLine();
		System.out.println("Enter yAxis: ");
		String yAxis = scanner.nextLine();
		
		dm.setTitle(title);
		dm.setXAxis(xAxis);
		dm.setYAxis(setIndex, yAxis);
		//dm.(title, xAxis, yAxis);
	}
//	private void saveFile() {
//		System.out.println("Enter name of file: ");
//		String fileName = scanner.nextLine();
//		try {
//			dm.saveEverything(fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	private double[] getPlots(double x1, double y1){
		//the location of origin
        int x0 = width/2;
        int y0 = height/2;
        double xScale = (x0 - BUFFER) / maxValue;
        double yScale = (y0 - BUFFER) / maxValue;
        double plotX = (x1-x0)/xScale;
        double plotY = (y0-y1)/yScale;
        double[] plot = {plotX, plotY}; 
        return plot;
	}
	
	/**
	 * Listens to mouse clicks
	 * Allows user to drag when clicked inside a point
	 * Allows user to create a new point when clicked on empty space
	 */
	public void mousePressed(MouseEvent m) {
		last = m.getPoint();
	    clickedPointIndex = isMouseInsidePoint(last);
	    if (clickedPointIndex != -1) {
	        dragging = true;
	    }
	    else{
	      	dragging = false;
	      	double[] plot = getPlots(last.x, last.y);
	      	dm.plotAdded(setIndex, plot[0], plot[1]);
	    }
	}
	
	public void mouseReleased(MouseEvent m) {
	   	released = m.getPoint();
	    if (dragging) {
	        double[] plot = getPlots(released.x, released.y);
	       	dm.plotMoved(setIndex, clickedPointIndex, plot[0], plot[1]);
	       //	dataMoved(plot[0], plot[1], clickedPointIndex);
	    }
	}

	/**
	 * Listens to key events
	 * Allows user to save when 's' is pressed
	 * Allows user to enter axis and title values when 'a' is pressed
	 * Allows user to display/undisplay the line connecting the plots
	 */
	@Override
	public void keyTyped(KeyEvent k) {
		if(k.getKeyChar() == ' '){
			if(displayLines == true)
				displayLines = false;
			else
				displayLines = true;
			update();
		}
		else if(k.getKeyChar() == 'p'){
			if(displayPoints == true)
				displayPoints = false;
			else
				displayPoints = true;
			update();
		}
		else if(k.getKeyChar() == 's') {
			//saveFile();
		}
		else if(k.getKeyChar() == 'a') {
			getTitleAndAxis();
		}
	}
	
	private int isMouseInsidePoint(Point point) {
	   	for(int i = 0; i < plotsVector.size(); i++){
	   		if(plotsVector.elementAt(i).contains(point)){
	   			return i;
	   		}
	   	}
	   	return -1;
	}

	/**
	 * Refreshes the graph to display new changes in the data
	 */
	public void update() {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}