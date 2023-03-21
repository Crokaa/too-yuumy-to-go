package pt.tooyummytogo.plugins;

import pt.portugueseexpress.InvalidCardException;

public interface MeioPagamentoPlugin {
	
	/**
	 * Efetua o pagamento atraves de um meio de pagamento (classes que implementam o metodo)
	 * @param total - dinheiro a ser pago
	 * @param numero - numero do cartao
	 * @param ccv2 - ccv do cartao
	 * @param mes - mes da validade do cartao
	 * @param ano - ano da validade do cartao
	 * @return true se pagamento bem sucedido
	 * @throws InvalidCardException
	 */
	public boolean efetuaPagamento(double total, String numero, String ccv2, String mes, String ano) throws InvalidCardException;
}
