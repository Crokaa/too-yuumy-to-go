package pt.tooyummytogo.facade.dto;

public class ProdutoInfo {

	private String codigoProduto;
	
	/**
	 * Construtor de ProdutoInfo
	 * @param codigo
	 */
	public ProdutoInfo(String codigo) {
		this.codigoProduto = codigo;
	}

	/**
	 * Devolve codigo da informacao do produto
	 * @return codigo do ProdutoInfo
	 */
	public String getCodigo() {
		return codigoProduto;
	}
	
	/**
	 * Representacao textual do ProdutoInfo
	 * @return Representacao textual do ProdutoInfo
	 */
	public String toString() {
		return codigoProduto;
	}

}
