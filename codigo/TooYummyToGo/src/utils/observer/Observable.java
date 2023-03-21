package utils.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe permite definir objetos do tipo Observable que sao capazes de 
 * propagar eventos a todos os objetos que implementam Observer e que estejam
 * a "observar" este objeto, ou seja, estejam na lista observers
 *
 * @param <E> objeto que extende a interface Event
 */
public abstract class Observable<E extends Event> {
	
	private List<Observer<E>> observers = new ArrayList<>();
	
	
	/**
	 * Permite adicionar um novo observer para ser "notificado" sempre que um
	 * evento for propagado
	 * @param o observer a er adicionado
	 */
	public void addObserver (Observer<E> o) {
		observers.add(o);
	}
	
	
	/**
	 * Propaga um Event por todos os objetos da lista observers que extendem Observable 
	 * @param e evento a ser propagado
	 */
	protected void dispatchEvent(E e) {
		for (Observer<E> o : observers) {
			o.handleNewEvent(e);
		}
	}
	
	

}
