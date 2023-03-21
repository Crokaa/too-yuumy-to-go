package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.exceptions.UsernameJaExisteException;

public class RegistarUtilizadorHandler {

	private CatContas catContas;

	/**
	 * Construtor de um handler RegistarUtilizadorHandler
	 * @param catContas catalogo de contas
	 * @requires catContas != null
	 */
	public RegistarUtilizadorHandler(CatContas catContas) {
		this.catContas = catContas;
	}


	/**
	 * Regista um utilizador normal.
	 * @param Username
	 * @param Password
	 * @throws UsernameJaExisteException lancada se ja existe um utilizador com esse
	 * 			username
	 * @ensures existe um novo utilizador com esse username
	 */
	public void registarUtilizador(String username, String password) throws UsernameJaExisteException{
		
			catContas.adicionaUtilizador(username, password);

	}

}
