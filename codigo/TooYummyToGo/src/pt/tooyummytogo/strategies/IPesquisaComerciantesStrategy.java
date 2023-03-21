package pt.tooyummytogo.strategies;

import java.util.List;

import pt.tooyummytogo.facade.dto.ComercianteInfo;

public interface IPesquisaComerciantesStrategy {

	public List<ComercianteInfo> pesquisaComerciantes();
}
