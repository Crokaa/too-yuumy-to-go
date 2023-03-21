package pt.tooyummytogo.facade.dto;

public class ComercianteInfo {

	private String nome;

	/**
	 * Construtor de ComercianteInfo
	 * @param nome
	 */
	public ComercianteInfo(String nome) {
		this.nome = nome;
	}

	/**
	 * Devolve username da informacao deste comerciante
	 * @return username
	 */
	public String getUsername() {
		return nome;
	}

	/**
	 * Representacao em texto do ComercianteInfo
	 * @return representacao textual ComercianteInfo
	 */
	public String toString() {

		return nome;
	}

}
