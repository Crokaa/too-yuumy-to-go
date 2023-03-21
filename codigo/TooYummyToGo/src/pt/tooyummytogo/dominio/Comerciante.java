package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.exceptions.TipoProdutoNaoExistenteException;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import utils.observer.Observer;
import utils.observer.SucessoEncomendaEvent;

public class Comerciante extends Conta implements Observer<SucessoEncomendaEvent> {

	private PosicaoCoordenadas localizacao;
	private HashMap<TipoProduto, Produto> produtos;
	private HashMap<String, TipoProduto> tiposProdutos;
	private HashMap<String, Reserva> listaReservas;


	/**
	 * Construtor de um Comerciante 
	 * @param username 
	 * @param password
	 * @param p coordenadas do estabelecimento
	 */
	public Comerciante(String username, String password, PosicaoCoordenadas p) {
		super(username, password);
		this.localizacao = p;
		produtos = new HashMap<>();
		tiposProdutos = new HashMap<>();
		listaReservas = new HashMap<>();
	}

	/**
	 * Determina se este comerciante estah disponivel
	 * @param loc localizacao para procura
	 * @param hInicio inicio horario recolha
	 * @param hFim fim horario recolha
	 * @param raio raio para procura
	 * @requires loc != null, hInicio != null, hFim != null
	 * @return true se o comerciante estah disponivel, false caso contrario
	 */
	public boolean estaDisponivel(PosicaoCoordenadas loc, LocalDateTime hInicio, LocalDateTime hFim, int raio) {

		boolean estaPerto = this.localizacao.distanciaEmMetros(loc)/1000 <= raio;

		return estaPerto && estaDisponivelHorario(hInicio, hFim);
	}


	/**
	 * Retorna a lista com todos os tipos de produtos associados ao comerciante
	 * @return lista com codigos de todos os tipos de produtos do comerciante
	 */
	public List<String> getListaTipoProdutos() {		
		return tiposProdutos.values().stream().map(t -> t.getCodigo()).collect(Collectors.toList());
	}


	/**
	 * Disponibiliza uma nova venda com a quantidade dada do tipo de produto
	 * 			escolhido
	 * @param codTipoProduto tipo de produto a vender
	 * @param quantidade numero de bens a vender
	 * @return venda do tipo de produto escolhido
	 * @throws TipoProdutoNaoExistenteException lançada quando o tipo de produto
	 * 			dado nao existe
	 * @requires quantidade > 0
	 */
	public Venda criarVenda(String codTipoProduto, int quantidade) 
			throws TipoProdutoNaoExistenteException {

		if (!tiposProdutos.containsKey(codTipoProduto)) {

			/* Se nao existir o Tipo de Produto seria realizado o UC4 
			 * (registarNovoTipoProduto), mas não sabemos qual o preco a passar
			 * como argumento */

			throw new TipoProdutoNaoExistenteException();
		}

		// se tipo existe == Happy Path 
		Produto produto;
		TipoProduto tipo = tiposProdutos.get(codTipoProduto);

		// jah foi colocado ah venda anteriormente
		if (produtos.containsKey(tipo)) {
			produto = produtos.get(tipo);

			// primeira vez colocado ah venda	
		} else {
			produto = new Produto(tipo.getNome(), tipo);
			produtos.put(tipo, produto);
		}

		return produto.criarVenda(quantidade);
	}


	/**
	 * Associa ao comerciante um novo tipo de produto
	 * @param nomeTipoProduto nome do novo tipo de produto
	 * @param preco preco unitario de cada bem 
	 * @requires preco >= 0
	 * @ensures existe um unico produto com nome nomeTipoProduto associado
	 * 			ao comerciante
	 */
	public void registaNovoTipoProduto(String nomeTipoProduto, double preco) {

		TipoProduto novoTipo = new TipoProduto(nomeTipoProduto, preco);

		/*
		 *Pelo enunciado o comerciante indica o nome, e o sistema gera um 
		 *codigo, nesta implementacao o nome e codigo sao iguais.
		 *Caso jah exista um tipo de produto com o mesmo nome, o sistema 
		 *altera o preco do produto para o preco que foi passado como argumento
		 * 
		 */		

		boolean existe = false;
		for (TipoProduto tp : tiposProdutos.values()) {
			if(tp.getNome().equals(nomeTipoProduto)) {
				tp.alteraPreco(preco);
				existe = true;
			}
		}
		if(!existe)
			tiposProdutos.put(novoTipo.getCodigo(), novoTipo);

	}


	/**
	 * Devolve a lista de produtos disponiveis entre hInicio e hFim
	 * @param hInicio hora inicial 
	 * @param hFim hora final
	 * @return lista de produtos disponiveis entre hInicio e hFim
	 */
	public List<ProdutoInfo> getProdutosDisp(LocalDateTime hInicio, LocalDateTime hFim){

		List<ProdutoInfo> listaProdutosDisp = new ArrayList<>();
		for(Produto p : produtos.values()) {

			if(p.existeVenda(hInicio, hFim))
				listaProdutosDisp.add(new ProdutoInfo(p.getCodigo()));
		}

		return listaProdutosDisp;
	}


	/**
	 * Cria uma compra com o produto com codigoProduto e quantidade
	 * @param codigoProduto produto escolhido para compra
	 * @param quantidade numero de bens pretendidos
	 * @param hInicio hora inicial janela temporal
	 * @param hFim hora final janela temporal
	 * @return Compra com produto == codigoProduto e quantidade == quantidade
	 * @throws QuantidadeIndisponivelException lancada se quantidade for maior 
	 * 			que a quantidade vendida
	 */
	public Compra criaCompra(String codigoProduto, int quantidade, LocalDateTime hInicio, LocalDateTime hFim) 
			throws QuantidadeIndisponivelException {

		Produto p = produtos.get(tiposProdutos.get(codigoProduto));
		return p.criaCompra(quantidade, hInicio, hFim);
	}


	/**
	 * O comerciante confirma a reserva
	 * @param r reserva 
	 * @requires r != null
	 * @return codigo da reserva confirmada
	 */
	public String confirmaReserva(Reserva r) {
		String codigo = r.getCodigo();
		listaReservas.put(codigo, r);
		return codigo;
	}

	public boolean estaDisponivelHorario(LocalDateTime start, LocalDateTime end) {

		for (Produto p : produtos.values()) {

			//pelo menos um produto serve, nao interessa recolher aqui
			if(p.existeVenda(start, end))
				return true;
		}

		return false;
	}

	public boolean estaDentroRaio(PosicaoCoordenadas loc, int raio) {

		return this.localizacao.distanciaEmMetros(loc)/1000 <= raio;
	}

	
	/**
	 * @see utils.observer.SucessoEncomendaEvent
	 */
	@Override
	public void handleNewEvent(SucessoEncomendaEvent e) {
		System.out.println(e.getInfoEncomenda());

	}
}
