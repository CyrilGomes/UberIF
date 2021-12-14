package observer;

/**
 * Interface representing an observer object.
 * @see Observable
 */
public interface Observer {
	/**
	 * Update the data according to the new changes in the observables.
	 * @param o 	the observable that had a change
	 * @param args 	the corresponding arguments needed
	 */
	public void update(Observable o, Object args);
}
