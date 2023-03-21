package pt.tooyummytogo.dominio;

public abstract class Conta {

	private String username;
	private String password;

	/**
	 * Construtor de uma conta, estrutura para registar utilizadores no sistema
	 * @param username 
	 * @param password
	 */
	protected Conta(String username, String password) {

		this.username = username;
		this.password = password;
	}
	
	/**
	 * Verifica se password eh igual ah dada
	 * @param password
	 * @return true se as passwords forem iguais, false caso contrario
	 */
	protected boolean hasPassword(String password) {

		return this.password.equals(password);
	}
	
	/**
	 * Devolve username desta conta
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

}
