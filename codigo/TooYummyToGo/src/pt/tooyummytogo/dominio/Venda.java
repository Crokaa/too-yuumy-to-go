package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;

import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;

public class Venda {

	Horario horario;
	private int quantidade;
	private double precoUnidade;
	private Produto produtoReferente;

	/**
	 * Construtor de venda
	 * @param quantidade quantidade para venda do produto
	 * @param preco preco para venda
	 */
	public Venda(int quantidade, double preco, Produto produto) {
		
		this.quantidade = quantidade;
		this.precoUnidade = preco;
		this.produtoReferente = produto;
	}

	/**
	 * Diz se a venda existe entre hInicio e hFim
	 * @param hInicio inicio horario para sobreposicao
	 * @param hFim fim horario para sobreposicao
	 * @requires hInicio != null , hFim != null
	 * @return true se a venda sobrepoe hInicio e hFim
	 */
	public boolean estaAVenda(LocalDateTime hInicio, LocalDateTime hFim) {

		return horario.sobrepoe(hInicio, hFim);
	}

	
	/**
	 * Associa um horario de recolha ah venda
	 * @param horario horario de recolha
	 */
	public void adicionaHorario(Horario horario) {

		this.horario = horario;
	}

	
	 /**
	 * Cria compra com quantidade dada
	 * @param quantidade - quantidade de produto da compra
	 * @param hInicio - data de inicio da disponibilidade do produto
	 * @param hFim - data de fim da disponibilidade do produto
	 * @throws QuantidadeIndisponivelException se quantidade for maior que a quantidade vendida
	 * @requires hInicio != null , hFim != null, quantidade > 0
	 * @return Compra com quantidade dada
	 */
	public Compra criaCompra(int quantidade, LocalDateTime hInicio, LocalDateTime hFim) 
			throws QuantidadeIndisponivelException {

		if(horario.sobrepoe(hInicio, hFim)) {
			if(quantidade <= this.quantidade)
				return new Compra(this, quantidade);
			else
				throw new QuantidadeIndisponivelException("Erro, a quantidade disponivel do produto e : " + this.quantidade);
		}
		else
			return null;
	}

	/**
	 * Devolve o preco da venda do produto
	 * @return preco da venda do produto
	 */
	public double getPreco() {
		return precoUnidade;
	}

	
	/**
	 * Reduz a quantidade da venda de produto
	 * @param quantidade - quantidade a reduzir na venda apos reserva
	 * @requires quantidade <= this.quantidade, quantidade > 0
	 */
	public void reduz(int quantidade) {
		this.quantidade -= quantidade;

	}
	
	public Produto getProtudo () {
		return produtoReferente;
	}

	public String getNomeProduto() {
		
		return produtoReferente.getNome();
	}

}
