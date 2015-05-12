package grapher;

import java.util.Vector;

import Model.GraphData;
import Model.SetsOfGraphData;
import View.Graph;
import View.GraphDisplay;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetsOfGraphData set = new SetsOfGraphData();
		Graph g = new Graph(set);
		g.makeWindow();
	}

}
