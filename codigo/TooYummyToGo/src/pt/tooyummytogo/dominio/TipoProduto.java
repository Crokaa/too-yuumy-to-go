package pt.tooyummytogo.dominio;

public class TipoProduto {
	
	private String nomeTipoProduto;
	private String codigo;
	private double precoUnitario;
	
	
	/**
	 * Contrutor de um tipo de produto
	 * @param nome nome do tipo de produto
	 * @param preco preco 
	 */
	public TipoProduto(String nome, double preco) {

		this.nomeTipoProduto = nome;
		this.codigo = nome;
		this.precoUnitario = preco;
		
		//atualmente nao se regista ingredientes
	}

	
	/**
	 * Retorna o codigo do tipo de produto
	 * @return codigo associado
	 */
	public String getCodigo() {
		return this.codigo;
	}


	/**
	 * Retorna o nome do tipo de produto
	 * @return nome associado
	 */
	public String getNome() {
		return nomeTipoProduto;
	}
	

	/**
	 * Devolve o preco unitario deste tipo de produto
	 * @return preco unitario deste tipo de produto
	 */
	public double getPreco() {
		return this.precoUnitario;
	}


	/*Criou-se este metodo para quando o comerciante criar um tipo de produto
	 * com um nome que ja exista. Como nao eh dito o que fazer neste caso no 
	 * enunciado, eh alterado preco, para o novo preco dado pelo comerciante em
	 * vez de ser lancada uma excepcao */
	
	/**
	 * Altera o preco unitario do tipo de produto 
	 * @param precoNovo novo preco
	 * @requires precoNovo >= 0
	 */
	public void alteraPreco(double precoNovo) {
		this.precoUnitario = precoNovo;
		
	}

}
