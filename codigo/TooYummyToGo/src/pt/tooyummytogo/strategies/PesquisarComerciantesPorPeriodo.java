package pt.tooyummytogo.strategies;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import pt.tooyummytogo.dominio.CatContas;
import pt.tooyummytogo.dominio.Comerciante;
import pt.tooyummytogo.facade.dto.ComercianteInfo;

public class PesquisarComerciantesPorPeriodo implements IPesquisaComerciantesStrategy{

	private CatContas catContas;
	private LocalDateTime start;
	private LocalDateTime end;

	public PesquisarComerciantesPorPeriodo(CatContas catContas, LocalDateTime now, LocalDateTime plusHours) {
		this.catContas = catContas;
		this.start = now;
		this.end = plusHours;
	}

	@Override
	public List<ComercianteInfo> pesquisaComerciantes() {

		List<Comerciante> comerciantes = catContas.getComerciantes();

		return comerciantes.stream()
				.filter(c -> c.estaDisponivelHorario(start, end))
				.map(c -> new ComercianteInfo(c.getUsername()))
				.collect(Collectors.toList());
		
	}

}
