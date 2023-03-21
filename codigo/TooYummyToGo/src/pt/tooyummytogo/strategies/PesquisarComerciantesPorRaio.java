package pt.tooyummytogo.strategies;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.dominio.Comerciante;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class PesquisarComerciantesPorRaio implements IPesquisaComerciantesStrategy{

	private CatContas catContas;
	private int raio;
	private PosicaoCoordenadas loc;

	public PesquisarComerciantesPorRaio(CatContas catContas, int i, PosicaoCoordenadas loc) {

		this.catContas = catContas;
		this.raio = i;
		this.loc = loc;
	}

	@Override
	public List<ComercianteInfo> pesquisaComerciantes() {

		List<Comerciante> comerciantes = catContas.getComerciantes();
		LocalDateTime start = LocalDateTime.now();
		
		/*
		 * Pesquisa por raio usa os comerciantes disponiveis
		 * ate ao fim do proprio dia
		 */
		
		//MAX eh o instante antes da meia noite - 23:59
		LocalTime beforeMidnight = LocalTime.MAX;
		
		//dia de hoje antes da meia noite
		LocalDateTime end = LocalDateTime.of(start.toLocalDate(), beforeMidnight);
		
		return comerciantes.stream()
				.filter(c -> c.estaDentroRaio(loc, raio) && c.estaDisponivelHorario(start, end))
				.map(c -> new ComercianteInfo(c.getUsername()))
				.collect(Collectors.toList());
	}

}
