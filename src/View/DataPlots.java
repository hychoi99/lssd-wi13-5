package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JComponent;

import Model.GraphData;

public class DataPlots extends JComponent{

	private final int yAxisDistance = 15;
	private final int buffer = 50;
	private boolean togglePoints = true;
	private boolean toggleLines = true;
	private static Vector<ImageObject>  imageCollection;
	Color color;
	GraphData dataSet;
	Image plotImage;
	
	public DataPlots(GraphData dataSet, Color color, Image plotImage){
		
		this.dataSet = dataSet;
		this.color = color;
		this.plotImage = plotImage;
	}
	
	public void paintAxis(int setIndex, Graphics g) {
		
        int width = getWidth();
        int height = getHeight();
		
		if(togglePoints || toggleLines){
			
        	g.drawLine(width/2 - (setIndex*yAxisDistance), buffer, width/2 - (setIndex*yAxisDistance), height-buffer);
		}
	}
	
	public void paintPlots(Graphics g, double xScale, double yScale) {
		
		for(int i = 0; i < dataSet.getData().size(); i+=2){
			
            double x = getWidth()/2 + (xScale * dataSet.getData().get(i));
            double y = getHeight()/2 - (yScale * dataSet.getData().get(i+1));
            g.drawImage(plotImage, (int)x, (int)y, null);
            ImageObject io = new ImageObject(x, y, plotImage);
            imageCollection.add(io);
            DecimalFormat myFormat = new DecimalFormat("0.00");
            
            g.drawString(myFormat.format(dataSet.getData().get(i)) + ", " + 
            		myFormat.format(dataSet.getData().get(i+1)), (int)x, (int)y);
		}
		sortImageCollection();
	}
	
	public void paintPlotLines(Graphics g) {
		
		if(toggleLines){
			g.setColor(color);
			for (int j = 0; j < imageCollection.size()-1; j++)
			{
				int x1 = (int)(imageCollection.elementAt(j).getXPosition()+imageCollection.elementAt(j).getImageWidth()/2);
				int y1 = (int)(imageCollection.elementAt(j).getYPosition()+imageCollection.elementAt(j).getImageHeight()/2);
				int x2 = (int)(imageCollection.elementAt(j).getXPosition()+imageCollection.elementAt(j).getImageWidth()/2);
				int y2 = (int)(imageCollection.elementAt(j).getYPosition()+imageCollection.elementAt(j).getImageHeight()/2);
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}
	
	public void togglePoints() {
		
		if(togglePoints == true){togglePoints = false;}
		else{togglePoints = true;}
	}
	public void toggleLines() {
		
		if(toggleLines == true){toggleLines = false;}
		else{toggleLines = true;}
	}
	public int getImageClicked(double x, double y) {

		for(int i = 0; i < imageCollection.size(); i++){
			
			double maxXBound = imageCollection.get(i).getImageWidth() + imageCollection.get(i).getXPosition();
			double maxYBound = imageCollection.get(i).getImageHeight() + imageCollection.get(i).getXPosition();
			
			if(x >= imageCollection.get(i).getXPosition() && x <= maxXBound &&
					y >= imageCollection.get(i).getYPosition() && y <= maxYBound){
				
				return i;
			}
		}
		return -1;
	}
	private void sortImageCollection(){
		
		for(int i = 0; i < imageCollection.size(); i ++) {
			
			int minIndex = i;
			
			for(int j = i; j < imageCollection.size(); j++){
				if(imageCollection.get(minIndex).getXPosition() > imageCollection.get(j).getXPosition()){
					minIndex = j;
				}
			}
			ImageObject tempX = imageCollection.get(i);
			imageCollection.set(i, imageCollection.get(minIndex));
			imageCollection.set(minIndex, tempX);
		}
	}
}
