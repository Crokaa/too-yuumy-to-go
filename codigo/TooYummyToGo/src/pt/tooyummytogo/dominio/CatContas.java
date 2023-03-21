package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import pt.tooyummytogo.exceptions.UsernameJaExisteException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class CatContas {

	private HashMap<String, Conta> contas = new HashMap<>();

	/**
	 * Verifica que comerciantes estao disponiveis perto
	 * da localizacao dada e no horario de recolha atual
	 * @param loc localizacao atual
	 * @param hInicio inicio horario recolha
	 * @param hFim fim horario recolha
	 * @param raio raio para procura de comerciantes 
	 * @requires loc != null, hInicio != null, hFim != null
	 * @return lista de informacao de comerciantes disponiveis
	 */
	public List<ComercianteInfo> getComerciantes(PosicaoCoordenadas loc, LocalDateTime hInicio, LocalDateTime hFim, int raio) {
		List<ComercianteInfo> comerciantesDisp = new ArrayList<>();

		for (Conta c : contas.values()) {

			if(c instanceof Comerciante) {
				Comerciante cmr = (Comerciante) c;
				if(cmr.estaDisponivel(loc,hInicio, hFim, raio))
					comerciantesDisp.add(new ComercianteInfo(cmr.getUsername()));
			}
		}

		return comerciantesDisp;
	}

	public List<Comerciante> getComerciantes() {

		List<Comerciante> comerciantes = new ArrayList<>();

		for (Conta c : contas.values()) {

			if(c instanceof Comerciante) {
				Comerciante cmr = (Comerciante) c;
				comerciantes.add(cmr);
			}
		}

		return comerciantes;
	}


	/**
	 * Adiciona comerciante ao catalogo de contas
	 * @param username username do comerciante
	 * @param password password do comerciante
	 * @param p localizacao da loja do comerciante
	 * @throws UsernameJaExisteException lancada quando username ja existe
	 */
	public void adicionaComerciante(String username, String password, PosicaoCoordenadas p) throws UsernameJaExisteException {

		if(contas.containsKey(username))
			throw new UsernameJaExisteException();
		else
			contas.put(username, new Comerciante(username, password, p));
	}


	/**
	 * Adiciona utilizador ao catalogo de contas
	 * @param username username do utilizador
	 * @param password password do utilizador
	 * @throws UsernameJaExisteException lancada quando username ja existe
	 */
	public void adicionaUtilizador(String username, String password) throws UsernameJaExisteException {

		if(contas.containsKey(username))
			throw new UsernameJaExisteException();
		else
			contas.put(username, new Utilizador(username, password));
	}


	/**
	 * Devolve a Conta com o username dado
	 * @param name username da conta
	 * @return conta do username dado
	 */
	public Conta getConta(String name) {
		return contas.get(name);
	}


	/**
	 * Tenta autenticar a conta no sistema
	 * @param username username da conta
	 * @param password password da conta
	 * @return Conta se login bem sucedido, nada caso contrario
	 */
	public Optional<Conta> tryLogin(String username, String password) {

		if(contas.containsKey(username) && contas.get(username).hasPassword(password))
			return Optional.of(contas.get(username));
		else
			return Optional.empty();
	}
}
