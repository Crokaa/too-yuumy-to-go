package pt.tooyummytogo.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.UsernameJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

class TestLogin {

	@Test
	void loginTest(){

		TooYummyToGo ty2g = new TooYummyToGo();
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (UsernameJaExisteException e) {
			//do nothing
		}

		//right password
		Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");

		assertTrue(talvezSessao.isPresent());

		//wrong password
		Optional<Sessao> talvezSessao2 = ty2g.autenticar("Felismina", "horta");

		assertFalse(!talvezSessao.isPresent());
	}

	@Test
	void registerUsernameAlreadyExistsTest() {

		TooYummyToGo ty2g = new TooYummyToGo();
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (UsernameJaExisteException e) {
			//do nothing
		}

		assertThrows(UsernameJaExisteException.class, () -> 
		regHandler.registarUtilizador("Felismina", "parqueC8"));

	}



}
