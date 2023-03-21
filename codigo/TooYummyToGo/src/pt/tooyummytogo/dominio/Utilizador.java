package pt.tooyummytogo.dominio;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Utilizador extends Conta{

	private HashMap<String, Reserva> listaReservas;

	/**
	 * Construtor de utilizador
	 * @param username
	 * @param password
	 */
	public Utilizador(String username, String password) {
		super(username, password);
		listaReservas = new HashMap<>();
	}

	/**
	 * O utilizador efetua pagamento com total de preco
	 * @return true se o pagamento for bem sucedido false caso contrario
	 */
	public boolean efetuaPagamento(double total, String numero, String ccv2, String validade) {
		Cartao c = new Cartao();
		return c.efetuaPagamento(total, numero, ccv2, validade);
	}

	
	/**
	 * O utilizador cria uma Reserva
	 * @return Reserva que foi feita
	 */
	public Reserva criaReserva(List<Compra> lcp, LocalDateTime hInicio) {
		
		Reserva r  = new Reserva(lcp, this, hInicio);
		listaReservas.put(r.getCodigo(), r);
		return r;
	}

}
