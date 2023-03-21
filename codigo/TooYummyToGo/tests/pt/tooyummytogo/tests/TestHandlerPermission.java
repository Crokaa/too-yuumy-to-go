package pt.tooyummytogo.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.OperacaoProibidaException;
import pt.tooyummytogo.exceptions.UsernameJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

class TestHandlerPermission {

	@Test
	void utilizadorHandlerPermission() {

		TooYummyToGo ty2g = new TooYummyToGo();
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");

			Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");
			talvezSessao.ifPresent( (Sessao s) -> {

				assertThrows(OperacaoProibidaException.class, () -> {
					AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
				});

				assertThrows(OperacaoProibidaException.class, () -> {
					ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				});

				assertDoesNotThrow(() -> {
					EncomendarHandler enc = s.getEncomendarComerciantesHandler();
				});

			});

		} catch (UsernameJaExisteException e) {
			//do nothing
		}

	}

	@Test
	void comercianteHandlerPermission() {

		TooYummyToGo ty2g = new TooYummyToGo();
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		try {
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));

			Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
			talvezSessao.ifPresent( (Sessao s) -> {

				assertDoesNotThrow(() -> {
					AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
				});

				assertDoesNotThrow(() -> {
					ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				});

				assertThrows(OperacaoProibidaException.class, () -> {
					EncomendarHandler enc = s.getEncomendarComerciantesHandler();
				});
			});

		} catch (UsernameJaExisteException e) {
			//do nothing
		}

	}

}
