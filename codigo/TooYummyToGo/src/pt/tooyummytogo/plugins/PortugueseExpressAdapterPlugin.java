package pt.tooyummytogo.plugins;

import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;

public class PortugueseExpressAdapterPlugin implements MeioPagamentoPlugin {

	/**
	 * Javadoc igual ao da interface mas com um requires
	 * @requires mes.length() == 2 &&  1 >= Integer.parseInt(mes) <=12 && ano.length () == 4
	 */
	@Override
	public boolean efetuaPagamento(double total, String numero, String ccv2, String mes, String ano){
		PortugueseExpress card = new PortugueseExpress();
		card.setCcv(Integer.parseInt(ccv2));
		card.setMonth(Integer.parseInt(mes));
		card.setYear(Integer.parseInt(ano));
		card.setNumber(numero);

		if(card.validate()) {
			try {
				card.block(total);
				card.charge(total);
				return true;
			} catch (InvalidCardException e) {
				System.out.println("Os parametros do cartao sao invalidos");
			}		
		}		
		return false;			
	}
}
