package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;

public class Produto {

	private List<Venda> vendas;

	//suposto ser nome dado pelo comerciante
	private String nome;
	private TipoProduto tipoProduto;


	/**
	 * Construtor de um Produto
	 * @param nomeProduto nome do produto
	 * @param tipoProduto tipo de produto 
	 */
	public Produto(String nomeProduto, TipoProduto tipoProduto) {

		this.nome = nomeProduto;

		// codigo no diagrama classes
		this.tipoProduto = tipoProduto;

		this.vendas = new ArrayList<>();
	}


	/**
	 * Diz se existe uma venda entre hInicio e hFim
	 * @param hInicio inicio de horario de disponibilidade do produto
	 * @param hFim fim de horario de disponibilidade do produto
	 * @requires hInicio != null , hFim != null
	 * @return true se existe venda false caso contrario
	 */
	public boolean existeVenda(LocalDateTime hInicio, LocalDateTime hFim) {

		boolean estaAVenda = false;

		for (Venda v : vendas) {

			estaAVenda = v.estaAVenda(hInicio, hFim);

			if(estaAVenda)
				return true;
		}

		return false;
	}


	/**
	 * Associa uma nova venda ao produto
	 * @param quantidade quantidade de bens para vender
	 * @requires quantidade > 0
	 * @return venda 
	 */
	public Venda criarVenda(int quantidade) {

		Venda venda = new Venda(quantidade, tipoProduto.getPreco(), this);
		vendas.add(venda);
		return venda;
	}


	/**
	 * Cria compra com quantidade dada
	 * @param quantidade - quantidade de produto da compra
	 * @param hInicio - data de inicio da disponibilidade do produto
	 * @param hFim - data de fim da disponibilidade do produto
	 * @throws QuantidadeIndisponivelException se quantidade dada
	 * for maior que a quantidade vendida
	 * @requires quantidade > 0, hInicio != null, hFim != null
	 * @return Compra deste produto com quantidade dada
	 */
	public Compra criaCompra(int quantidade, LocalDateTime hInicio, LocalDateTime hFim) 
			throws QuantidadeIndisponivelException {

		for(Venda v : vendas)
			if(v.criaCompra(quantidade, hInicio, hFim) != null)
				return v.criaCompra(quantidade, hInicio, hFim);

		//entra sempre no for
		return null;
	}


	/**
	 * Retorna o tipo de produto associado ao produto
	 * @return codigo do tipo de produto
	 */
	public String getCodigo() {
		return tipoProduto.getCodigo();
	}


	public String getNome() {

		return nome;
	}

}
