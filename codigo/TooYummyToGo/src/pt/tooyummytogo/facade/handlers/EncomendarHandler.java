package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.dominio.Comerciante;
import pt.tooyummytogo.dominio.Compra;
import pt.tooyummytogo.dominio.Reserva;
import pt.tooyummytogo.dominio.Utilizador;
import pt.tooyummytogo.exceptions.PagamentoSemSucessoException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.strategies.PesquisarComerciantesPorPeriodo;
import pt.tooyummytogo.strategies.PesquisarComerciantesPorRaio;


public class EncomendarHandler {

	private PosicaoCoordenadas loc;
	private CatContas catContas;
	private LocalDateTime hInicio;
	private LocalDateTime hFim;
	private int raio;
	private Comerciante comercianteAtual;
	private Utilizador utilizadorAtual;
	private List<Compra> listaComprasAtuais;

	/**
	 * Construtor de um handler EncomendarHandler
	 * @param catContas catalogo de contas
	 * @param utilizadorAtual utilizador corrente
	 * @requires catContas != null, utilizadorAtual != null
	 */
	public EncomendarHandler(CatContas catContas, Utilizador utilizadorAtual) {
		this.catContas = catContas;
		this.listaComprasAtuais = new ArrayList<>();
		this.utilizadorAtual = utilizadorAtual;

		//default
		this.hInicio = LocalDateTime.now();
		this.hFim = LocalDateTime.now().plusHours(1);
		this.raio = 5; //Km
	}


	/**
	 * Verifica que comerciantes estao disponiveis perto
	 * da localizacao dada e no horario de recolha atual
	 * @param coordinate localizacao atual
	 * @requires coordinate != null
	 * @return lista de informacao de comerciantes disponiveis
	 */
	public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas coordinate) {

		this.loc = coordinate;
		return catContas.getComerciantes(loc,hInicio,hFim,raio);
	}


	/**
	 * Procura comerciantes disponiveis com raio dado
	 * @param i raio pretendido em Km, i >= 0
	 * @return lista de informacao de comerciantes disponiveis
	 */
	public List<ComercianteInfo> redefineRaio(int i) {

		this.raio = i;
		PesquisarComerciantesPorRaio raioStrategy = new PesquisarComerciantesPorRaio(catContas, i, loc);
		return raioStrategy.pesquisaComerciantes();

	}


	/**
	 * Procura comerciantes disponiveis no horario de recolha dado
	 * @param now inicio do horario de procura
	 * @param plusHours fim do horario de procura
	 * @requires now != null , plusHours != null
	 * @return lista de informacao de comerciantes disponiveis
	 */
	public List<ComercianteInfo> redefinePeriodo(LocalDateTime now, LocalDateTime plusHours) {

		this.hInicio = now;
		this.hFim = plusHours;
		PesquisarComerciantesPorPeriodo periodoStrategy = new PesquisarComerciantesPorPeriodo(catContas, now, plusHours);
		return periodoStrategy.pesquisaComerciantes();
	}


	/**
	 * Devolve lista dos informacao dos produtos disponiveis do comerciante dado
	 * @param comercianteInfo informacao do comerciante
	 * @ensures comerciante eh comerciante corrente 
	 * @requires (comerciante do comericanteInfo).estaDisponivel(loc, hInicio, hFim, raio)
	 * @return lista de ProdutoInfo 
	 */
	public List<ProdutoInfo> escolheComerciante(ComercianteInfo comercianteInfo) {

		Comerciante c = (Comerciante) catContas.getConta(comercianteInfo.getUsername());
		this.comercianteAtual = c;

		return c.getProdutosDisp(hInicio, hFim);
	}


	/**
	 * Escolhe um produto da lista de produtos disponiveis e a quantidade a comprar
	 * @param pInfo informacao do produto a comprar
	 * @param i quantidade a comprar
	 * @ensures existe mais uma compra corrente na lista de compras correntes
	 * @requires comercianteAtual.getProdutosDisp(hInicio, hFim).contains(pInfo), i > 0
	 * @throws QuantidadeIndisponivelException lancada quando a i < quantidade disponivel
	 * do produto
	 */
	public void indicaProduto(ProdutoInfo pInfo, int i) 
			throws QuantidadeIndisponivelException {

		listaComprasAtuais.add(comercianteAtual.criaCompra(pInfo.getCodigo(), i, hInicio, hFim));
	}


	/**
	 * Eh feito o pagamento da lista de compras feita pelo utilizador
	 * @param numero numero do cartao
	 * @param validade validade do cartao
	 * @param ccv2 ccv2 do cartao
	 * @ensures caso pagamento bem sucedido existe mais uma reserva no sistema
	 * @return codigo da reserva
	 * @throws PagamentoSemSucessoException se pagamento nao for bem sucedido
	 */
	public String indicaPagamento(String numero, String validade, String ccv2) 
			throws PagamentoSemSucessoException {

		double total = 0;

		for(Compra c : listaComprasAtuais)
			total += c.getPreco();

		if(utilizadorAtual.efetuaPagamento(total, numero, ccv2, validade)) {

			Reserva r = utilizadorAtual.criaReserva(listaComprasAtuais, hInicio);
			r.addObserver(comercianteAtual);
			String cod = comercianteAtual.confirmaReserva(r);

			for(Compra cp : listaComprasAtuais) {
				cp.reduzQuantVenda();
			}

			return cod;
		}
		else
			throw new PagamentoSemSucessoException();

	}



}
