package pt.tooyummytogo.dominio;

public class Compra {

	private int quantidade;
	private Venda venda;

	/**
	 * Construtor de uma compra
	 * @param venda venda a associar a esta compra
	 * @param quantidade quantidade do produto a comprar
	 */
	public Compra(Venda venda, int quantidade) {
		this.venda = venda;
		this.quantidade = quantidade;
	}

	
	/**
	 * Devolve o preco da compra
	 * @return preco da compra
	 */
	public double getPreco() {
		return venda.getPreco() * quantidade;
	}

	
	/**
	 * Reduz a quantidade de venda do produto feito pela compra do mesmo
	 */
	public void reduzQuantVenda() {
		venda.reduz(quantidade);

	}

	public int getQuantidade() {
		return quantidade;
	}
	
	@Override
	public String toString() {
		return "Produto: " + venda.getNomeProduto() + ", quantidade: " + quantidade;
	}

}
