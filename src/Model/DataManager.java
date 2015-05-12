/**
 * @author hychoi
 * Abstract class used for any sort of data that wants to be displayed
 * Uses the Model/View design and has the necessary listeners to connect the Model and View
 * Contains the required functions as abstract so that whichever set of data that is used can
 * write their own code for it or whatever
 */
package Model;

import java.util.Vector;

public abstract class DataManager {

	private Vector<dataManagerListener> listeners = new Vector<dataManagerListener>();
	
	public abstract Object getSet();
	public abstract void SaveData();
	public abstract void LoadData();
	
	/**
	 * Adds the object that is doing the displaying into a Vector.
	 * That object must be of type dataManagerListener.
	 * @param l - the display object
	 */
	public final void addListener(dataManagerListener l){
		
		listeners.add(l);
	}
	
	/**
	 * Only called when some data has changed in the Model.
	 * Notifies all the possible display objects of the changes.
	 * It tells the object to update its display since some part of the data has changed.
	 */
	public final void notifyListeners()
	{
		for(dataManagerListener l: listeners) {
			l.update();
		}
	}
}
