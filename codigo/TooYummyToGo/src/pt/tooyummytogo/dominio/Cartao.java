package pt.tooyummytogo.dominio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.plugins.MeioPagamentoPlugin;

public class Cartao {

	/**
	 * Efetua o pagamento com total de preco
	 * @param total - valor a ser pago
	 * @param numero - numero do cartao
	 * @param ccv2 - ccv2 do cartao
	 * @param validade - validade do cartao da forma "mes/ano(2 digitos)"
	 * @return true se cartao for valido e o pagamento for bem sucedido false caso contrario
	 */
	public boolean efetuaPagamento(double total, String numero, String ccv2, String validade) {

		String[] val = validade.split("/");
		String mes = val[0];
		String ano = "20" + val[1];

		Properties p = new Properties();
		
		try {
			p.load(new FileInputStream(new File("pagamento.properties")));
			
			String className = p.getProperty("meioDePagamento");
			
			@SuppressWarnings("unchecked")
			Class<MeioPagamentoPlugin> klass = (Class<MeioPagamentoPlugin>) Class.forName(className);
			Constructor<MeioPagamentoPlugin> cons = klass.getConstructor();
			MeioPagamentoPlugin meio = cons.newInstance();
			
			return meio.efetuaPagamento(total, numero, ccv2, mes, ano);
			
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (ClassNotFoundException e) {
			// Do nothing
		} catch (NoSuchMethodException e) {
			// Do nothing
		} catch (InstantiationException e) {
			// Do nothing
		} catch (IllegalAccessException e) {
			// Do nothing
		} catch (IllegalArgumentException e) {
			// Do nothing
		} catch (InvocationTargetException e) {
			// Do nothing
		} catch (InvalidCardException e) {
			// Do nothing
		}

		return false;

	}



}