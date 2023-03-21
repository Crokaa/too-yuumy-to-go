package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.dominio.Comerciante;
import pt.tooyummytogo.dominio.Horario;
import pt.tooyummytogo.dominio.Produto;
import pt.tooyummytogo.dominio.Venda;
import pt.tooyummytogo.exceptions.TipoProdutoNaoExistenteException;

public class ColocarProdutoHandler {

	private Comerciante comercianteAtual;
	private List<Venda> vendasMesmoHorario = new ArrayList<>();

	
	/**
	 * Construtor de um handler ColocarProdutoHandler
	 * @param comercianteAtual comerciante corrente
	 * @requires comercianteAtual != null
	 */
	public ColocarProdutoHandler(Comerciante comercianteAtual) {
		this.comercianteAtual = comercianteAtual;
	}


	/**
	 * Retorna a lista com todos os tipos de produtos associados ao comerciante corrente
	 * @return lista com codigos de todos os tipos de produtos do comerciante
	 */
	public List<String> inicioDeProdutosHoje() {

		return comercianteAtual.getListaTipoProdutos();
	}

	/**
	 * Regista para venda o tipo de produto dado
	 * @param idTipoProduto tipo de produto a vender
	 * @param quantidade numeros de bens para venda
	 * @throws TipoProdutoNaoExistenteException lancada quando o tipo de produto
	 * 			dado nao existe
	 * @requires quantidade > 0
	 * @ensures existe mais um produto associado ao comerciante corrente e mais
	 *  		uma venda corrente 
	 */
	public void indicaProduto(String idTipoProduto, int quantidade) 
			throws TipoProdutoNaoExistenteException {

		Venda venda = comercianteAtual.criarVenda(idTipoProduto, quantidade);
		vendasMesmoHorario.add(venda);

	}


	/**
	 * Permite ao comerciante confirmar o horario de recolha do(s) tipo(s) de 
	 * produto(s) indicados para venda
	 * @param hInicio hora de inicio da recolha
	 * @param hFim fora de fim da recolha
	 * @requires hInicio != null, hFim != null
	 */
	public void confirma(LocalDateTime hInicio, LocalDateTime hFim) {

		Horario horario = new Horario (hInicio, hFim);
		for (Venda venda : vendasMesmoHorario) {
			venda.adicionaHorario(horario);
		}
	}

}
