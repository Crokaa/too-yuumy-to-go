package pt.tooyummytogo.dominio;

import java.time.LocalDateTime;

public class Horario {

	private LocalDateTime hInicio;
	private LocalDateTime hFim;

	
	/**
	 * Construtor de um Horario para recolha de produtos
	 * @param hInicio hora inicial de recolha
	 * @param hFim hora final de recolha
	 */
	public Horario(LocalDateTime hInicio, LocalDateTime hFim) {

		//LocalDateTime contem o dia, nao eh preciso um atributo para o dia

		this.hInicio = hInicio;
		this.hFim = hFim;
	}

	/**
	 * Verifica se este horario sobrepoe com a janela dada
	 * @param hInicio2 inicio janela a sobrepor
	 * @param hFim2 fim janela a sobrepor
	 * @requires hInicio2 != null , hFim2 != null
	 * @return true se este horario sobrepoe a janela dada
	 */
	public boolean sobrepoe(LocalDateTime hInicio2, LocalDateTime hFim2) {

		//ver se intervalo dado eh valido
		if(hInicio2.isAfter(hFim2))
			return false;

		//ver se os horarios intersetam, ou seja, !(nao se intersetam)
		return !hFim.isBefore(hInicio2) && !hInicio.isAfter(hFim2); 
	}

}
