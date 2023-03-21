package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;
import java.util.List;

import utils.observer.Observable;
import utils.observer.SucessoEncomendaEvent;

public class Reserva extends Observable<SucessoEncomendaEvent>{

	private List<Compra> listaCompras;
	private String codigo;
	private EstadoReserva estado;

	/**
	 * Construtor de Reserva
	 * @param listaCompras lista de compras a associar
	 * @param u utilizador que fez esta reserva
	 * @param hInicio data de inicio para esta reserva
	 */
	public Reserva(List<Compra> listaCompras, Utilizador u, LocalDateTime hInicio) {
		this.listaCompras = listaCompras;
		estado = EstadoReserva.PENDENTE;

		codigo = u.getUsername() + " de " + hInicio;
	}

	
	
	//Só se usa esta classe após a reserva ser criada
	
	/**
	 * Retorna codigo associado ah reserva e propaga om objeto SucessoEncomendaEvent
	 * @return codigo da reserva
	 */
	public String getCodigo() {
		this.dispatchEvent(new SucessoEncomendaEvent(getReservaInfo()));
		return codigo;
	}

	
	/**
	 * Retorna a informacao da reserva, quais os produtos necessarios e a 
	 * quantidade dos mesmos
	 * @return string com a informacao da reserva
	 */
	private String getReservaInfo() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Esta encomenda precisa de:" + "\n");
		for (Compra c : listaCompras) {
			sb.append(c + "\n");
		}
		return sb.toString();
	}


	/**
	 * Confirma que a recolha foi feita
	 */
	public void confirmaRecolha() {
		this.estado = EstadoReserva.RECOLHIDA;
	}
	
	/**
	 * Devolve estado da Reserva
	 * @return estado da reserva
	 */
	public EstadoReserva getEstado() {
		return this.estado;
	}
}
