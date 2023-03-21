package pt.tooyummytogo.plugins;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

public class MonsterCardAPIAdapterPlugin implements MeioPagamentoPlugin{

	/**
	 * Javadoc igual ao da interface mas com um requires
	 * @requires mes.length() == 2 &&  1 >= Integer.parseInt(mes) <=12 && ano.length () == 4
	 */
	@Override
	public boolean efetuaPagamento(double total, String numero, String ccv2, String mes, String ano){
		Card cartao = new Card(numero, ccv2, mes, ano);
		MonsterCardAPI monsterCartao = new MonsterCardAPI();

		if(monsterCartao.isValid(cartao) && monsterCartao.block(cartao, total)) {
			return monsterCartao.charge(cartao, total);
		}
		return false;
	}

}
