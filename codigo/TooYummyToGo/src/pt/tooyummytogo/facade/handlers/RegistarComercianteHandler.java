package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.exceptions.UsernameJaExisteException;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class RegistarComercianteHandler {

	private CatContas catContas;

	/**
	 * Construtor de um handler RegistarComercianteHandler
	 * @param catContas catalogo de contas
	 * @requires catContas != null
	 */
	public RegistarComercianteHandler(CatContas catContas) {
		this.catContas = catContas;
	}

	/**
	 * Regista um Comerciante
	 * @param Username
	 * @param Password
	 * @throws UsernameJaExisteException lancada se ja existe um comerciante com esse
	 * 			username
	 * @ensures existe um novo comerciante com esse username
	 */
	public void registarComerciante(String username, String password, PosicaoCoordenadas p) throws UsernameJaExisteException{

			catContas.adicionaComerciante(username, password, p);

	}

}
