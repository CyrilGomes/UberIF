package observer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class representing an observable object.
 * @see Observer
 */
public class Observable {
	/**
	 * Collection of observers associated to the observable.
	 */
	private final Collection<Observer> listeObservers;

	/**
	 * Constructor of Observable object.
	 */
	public Observable() {
		listeObservers = new ArrayList<>();
	}

	/**
	 * Add a new observer to the observable object.
	 * @param o the observer to be added
	 */
	public void addObserver(Observer o){
		if(!listeObservers.contains(o)){
			listeObservers.add(o);
		}
	}

	/**
	 * Warns the observers that changes have been made.
	 * @param args the corresponding arguments needed
	 */
	public void notifyObservers(Object args){
		listeObservers.forEach(observer -> {
			observer.update(this, args);
		});
	}
}
