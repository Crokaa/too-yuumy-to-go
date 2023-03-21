package pt.tooyummytogo.facade;

import java.util.Optional;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.dominio.Conta;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

/**
 * Esta é a classe do sistema.
 */
public class TooYummyToGo {

	private CatContas catContas = new CatContas();

	// UC1
	/**
	 * Devolve um RegistarUtilizadorHandler handler
	 * @return RegistarUtilizadorHandler
	 */
	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(catContas);
	}

	/**
	 * Returns an optional Session representing the authenticated user.
	 * @param username
	 * @param password
	 * @return
	 * 
	 * UC2
	 */
	public Optional<Sessao> autenticar(String username, String password) {

		Optional<Conta> currConta = catContas.tryLogin(username, password);

		//se a optional<conta> for vazia o map nao faz nada
		return currConta.map(u -> new Sessao(u, catContas));
	}

	// UC3
	/**
	 * Devolve um RegistarComercianteHandler handler
	 * @return RegistarComercianteHandler
	 */
	public RegistarComercianteHandler getRegistarComercianteHandler() {
		return new RegistarComercianteHandler(catContas);
	}


}
