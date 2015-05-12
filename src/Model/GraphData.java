/**
 * @author hychoi
 * A single set of data that primarily deals with graphs
 */
package Model;

import java.util.Vector;

public class GraphData {

	private Vector<Double> dataSet;
	private String xAxis;
	private String yAxis;
	private String title;
	private String plotImage;
	private String color;
	
	public GraphData() {
		
		dataSet = new Vector<Double>();
		xAxis = "";
		yAxis = "";
		title = "";
	}
	
	public double getPlot(int index) {
		
		return dataSet.get(index);
	}
	
	/**
	 * @param x - the x-coordinate of the plot to be added
	 * @param y - the y-coordinate of the plot to be added
	 */
	public void plotAdded(double x, double y) {
		
		dataSet.add(x);
		dataSet.add(y);
		sort();
	}
	
	/**
	 * @param plotIndex - the index of the plot in the set that moved
	 * @param x - the new x-coordinate of the plot
	 * @param y - the new y-coordinate of the plot
	 */
	public void plotMoved(int plotIndex, double x, double y) {
		
		dataSet.set(plotIndex*2, x);
		dataSet.set(plotIndex*2+1, y);
		sort();
	}
	
	private void sort(){
		
		for(int i = 0; i < dataSet.size()-1; i += 2) {
			
			int minIndex = i;
			
			for(int j = i; j < dataSet.size()-1; j+= 2){
				if(dataSet.get(minIndex) > dataSet.get(j)){
					minIndex = j;
				}
			}
			double tempX = dataSet.get(i);
			double tempY = dataSet.get(i+1);
			dataSet.set(i, dataSet.get(minIndex));
			dataSet.set(i+1, dataSet.get(minIndex+1));
			dataSet.set(minIndex, tempX);
			dataSet.set(minIndex+1, tempY);
		}
	}
	
	public Vector<Double> getData(){
		
		return dataSet;
	}
	
	public void setYAxis(String yAxis) {
		this.yAxis = yAxis;
	}
	public void setXAxis(String xAxis) {
		this.xAxis = xAxis;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setPlotImage(String imageName) {
		this.plotImage = imageName;
	}
	public String getYAxis() {
		return yAxis;
	}
	public String getXAxis() {
		return xAxis;
	}
	public String getTitle() {
		return title;
	}
	public String getColor() {
		return color;
	}
	public String getPlotImage() {
		return plotImage;
	}
}
