package pt.tooyummytogo;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.dominio.Comerciante;
import pt.tooyummytogo.dominio.Conta;
import pt.tooyummytogo.dominio.Utilizador;
import pt.tooyummytogo.exceptions.OperacaoProibidaException;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

public class Sessao {

	private Conta currConta;
	private CatContas catContas;

	public Sessao(Conta u, CatContas catContas) {

		this.currConta = u;
		this.catContas = catContas;
	}

	// UC4
	/**
	 * Devolve um handler do tipo AdicionarTipoDeProdutoHandler
	 * @return AdicionarTipoDeProdutoHandler
	 * @throws OperacaoProibidaException lancada quando o utilizador tenta 
	 * 			realizar uma acao destina a comerciantes
	 */
	public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() throws OperacaoProibidaException {

		if(currConta instanceof Utilizador)
			throw new OperacaoProibidaException();

		return new AdicionarTipoDeProdutoHandler( (Comerciante) currConta);
	}

	// UC5
	/**
	 * Devolve um handler do tipo ColocarProdutoHandler
	 * @return ColocarProdutoHandler
	 * @throws OperacaoProibidaException lancada quando o utilizador tenta 
	 * 			realizar uma acao destinada a comerciantes
	 */
	public ColocarProdutoHandler getColocarProdutoHandler() throws OperacaoProibidaException {

		if(currConta instanceof Utilizador)
			throw new OperacaoProibidaException();

		return new ColocarProdutoHandler((Comerciante) currConta);
	}

	/**
	 * Devolve um handler do tipo EncomendarHandler
	 * @return EncomendarHandler
	 * @throws OperacaoProibidaException lancada quando o comerciante tenta 
	 * 			realizar uma acao destinada a utilizadores
	 */
	public EncomendarHandler getEncomendarComerciantesHandler() throws OperacaoProibidaException {

		if(currConta instanceof Comerciante)
			throw new OperacaoProibidaException();

		return new EncomendarHandler(catContas, (Utilizador) currConta);
	}
}
