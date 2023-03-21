package utils.observer;


public class SucessoEncomendaEvent implements Event{

	private String infoEncomenda;
	
	
	/**
	 * Construtor objeto SucessoEncomendaEvent 
	 * @param info informacao relativa ah encomenda
	 */
	public SucessoEncomendaEvent (String info) {
		
		this.infoEncomenda = info;
	}

	
	/**
	 * Retorna a informacao associada a este objeto
	 * @return conteud de infoEncomenda 
	 */
	public String getInfoEncomenda () {
		
		return infoEncomenda;
	}
	
	
	

}
