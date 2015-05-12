/**
 * @author hychoi
 * An object that is of type DataManager.
 * This class deals with the collection of data sets.
 * Deals primarily with data sets that is used for graph displays.
 */
package Model;

import java.util.Vector;

public class SetsOfGraphData extends DataManager {

	private Vector<GraphData> SetsOfGraphData;
	//FileManager fm;
	private String xAxis;
	private String yAxis;
	private String title;
	
	public SetsOfGraphData() {
		
		SetsOfGraphData = new Vector<GraphData>();
		xAxis = "";
		yAxis = "";
		title = "";
		addNewData();
		//fm = new FileManager();
	}
	
	public void addNewData() {
		
		GraphData g = new GraphData();
		SetsOfGraphData.add(g);
	}
	
	/**
	 * @param setIndex - the index of the set to which the plot should be added
	 * @param x - the x coordinate that is being added
	 * @param y - the y coordinate that is being added
	 */
	public void plotAdded(int setIndex, double x, double y) {
		
		SetsOfGraphData.get(setIndex).plotAdded(x, y);
		notifyListeners();
	}
	
	/**
	 * @param setIndex - the index of the set in which its point has moved
	 * @param plotIndex - the index of the point in the set which has moved
	 * @param x - the new x coordinate of the particular point
	 * @param y - the new y coordinate of the particular point
	 */
	public void plotMoved(int setIndex, int plotIndex, double x, double y) {
		
		SetsOfGraphData.get(setIndex).plotMoved(plotIndex, x, y);
		notifyListeners();
	}
	
	public void SaveData() {
		//fm.Save
	}
	
	public void LoadData() {
		//fm.load
	}
	
	public void setXAxis(String xAxis) {
		this.xAxis = xAxis;
		notifyListeners();
	}
	public void setYAxis(int setIndex, String yAxis) {
		getSet().get(setIndex).setYAxis(yAxis);
		notifyListeners();
	}
	public void setTitle(String title) {
		this.title = title;
		notifyListeners();
	}
	public String getXAxis() {
		return xAxis;
	}
	public String getYAxis(int setIndex) {
		return getSet().get(setIndex).getYAxis();
	}
	public String getTitle() {
		return title;
	}
	
	public Vector<GraphData> getSet() {
		
		return SetsOfGraphData;
	}
	public double getPlot(int setIndex, int plotIndex) {
		
		return SetsOfGraphData.get(setIndex).getPlot(plotIndex);
	}
}
