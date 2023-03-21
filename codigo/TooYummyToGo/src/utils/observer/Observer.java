package utils.observer;

/**
 * Esta interface permite a um objeto Observer ser informado de propagacao de
 * eventos Event em objetos Observable
 *
 * @param <E> objeto que extende Event
 */
public interface Observer<E extends Event> {

	/**
	 * Define o que fazer quando eh propagado um objeto do tipo Event
	 * @param e objeto implements Event que foi propagado 
	 */
	void handleNewEvent(E e);

}
