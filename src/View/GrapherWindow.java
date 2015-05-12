package View;
import java.util.Vector;

import Model.*;

public class GrapherWindow implements dataManagerListener {

	private DataManager SetsOfGraphData;
	private Graph graph;
	private Table table;
	
	public GrapherWindow() {
		
		SetsOfGraphData = new SetsOfGraphData();
		SetsOfGraphData.addListener(this);
		Object dataSets = SetsOfGraphData.getSet();
		Graph graph = new Graph((Vector<GraphData>) dataSets);
		Table table = new Table((Vector<GraphData>) dataSets);
	}
	
	public void displayWindow() {
		
		graph.makeWindow();
	}

	public void update() {
		
		Object dataSets = SetsOfGraphData.getSet();
		graph.update((Vector<GraphData>) dataSets);
		table.update();
		if(graph.toggleTable()){
			table.display();
		}
	}
}
