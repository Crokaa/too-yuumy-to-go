package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.dominio.Comerciante;

public class AdicionarTipoDeProdutoHandler {
	
	private Comerciante comercianteAtual;

	/**
	 * Construtor de um handler AdicionarTipoDeProdutoHandler
	 * @param comercianteAtual comerciante corrente
	 * @requires comercianteAtual != null
	 */
	public AdicionarTipoDeProdutoHandler(Comerciante comercianteAtual) {

		this.comercianteAtual = comercianteAtual;
	}


	/**
	 * Associa ao comerciante corrente um novo tipo de produto
	 * @param tp nome do novo tipo de produto
	 * @param preco preco unitario de cada bem
	 * @requires preco >= 0
	 */
	public void registaTipoDeProduto(String tp, double preco) {
		comercianteAtual.registaNovoTipoProduto(tp, preco);
	}
	
}


