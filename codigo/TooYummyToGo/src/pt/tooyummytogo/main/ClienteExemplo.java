package pt.tooyummytogo.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.OperacaoProibidaException;
import pt.tooyummytogo.exceptions.PagamentoSemSucessoException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.exceptions.TipoProdutoNaoExistenteException;
import pt.tooyummytogo.exceptions.UsernameJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class ClienteExemplo {

	public static void main(String[] args){

		TooYummyToGo ty2g = new TooYummyToGo();

		// UC1
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (UsernameJaExisteException e1) {
			System.out.println("Ja existe este username");
		}


		// UC3
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		try {
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
			regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));
		} catch (UsernameJaExisteException e) {
			System.out.println("Ja existe este username");
		}


		// UC4
		Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent( (Sessao s) -> {
			try {
				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
				Random r = new Random();
				for (String tp : new String[] {"Pão", "Pão de Ló", "Mil-folhas"}) {
					atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
				}
			} catch (OperacaoProibidaException e) {
				System.out.println("O Utilizador nao pode executar esta operacao");
			}

		});

		// UC5
		Optional<Sessao> talvezSessao2 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao2.ifPresent( (Sessao s) -> {

			try {
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();

				if(!listaTiposDeProdutos.isEmpty()) {
					try {
					cpv.indicaProduto(listaTiposDeProdutos.get(0), 10); // Pão
					} catch (TipoProdutoNaoExistenteException e) {
						// Era pedido um novo nome de produto e preco
						s.adicionarTipoDeProdutoHandler().registaTipoDeProduto("Pastel Nata", 0.7);
					}
					
					try {
					cpv.indicaProduto(listaTiposDeProdutos.get(2), 5); // Mil-folhas
					} catch (TipoProdutoNaoExistenteException e) {
						// Era pedido um novo nome de produto e preco
						s.adicionarTipoDeProdutoHandler().registaTipoDeProduto("Pastel Nata", 0.7);
					}

					cpv.confirma(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

					System.out.println("Produtos disponiveis");
				}
			} catch (OperacaoProibidaException e) {
				System.out.println("O Utilizador nao pode executar esta operacao");

			} 
			
		});

		// UC6 + UC7
		Optional<Sessao> talvezSessao3 = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao3.ifPresent( (Sessao s) -> {

			try {
				EncomendarHandler lch = s.getEncomendarComerciantesHandler();
				List<ComercianteInfo> cs = lch.indicaLocalizacaoActual(new PosicaoCoordenadas(34.5, 45.2));

				for (ComercianteInfo i : cs) {
					System.out.println(i);
				}

				boolean redefineRaio = false;
				if (redefineRaio) {

					int raio = 100;
					cs = lch.redefineRaio(raio);

					while(cs.isEmpty()) {
						
						if(raio == 200)
							break;
						//extensao 3a UC6
						//nao pode ser exception porque entra em loop
						//repetir o raio, dado pelo utilizador
						//ou sair depois de n vezes
						raio += 10;
						cs = lch.redefineRaio(raio);				
					}

					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}
				}

				boolean redefinePeriodo = false;
				if (redefinePeriodo) {
					cs = lch.redefinePeriodo(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}
				}

				// A partir de agora é UC7
				if(!cs.isEmpty()) {
					List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));

					for (ProdutoInfo p : ps) {
						try {
							lch.indicaProduto(p, 1);
							// Um de cada
						} catch (QuantidadeIndisponivelException e) {
							System.out.println(e.getMessage() + " " + p);
						}
					}

					String codigoReserva = lch.indicaPagamento("365782312312", "02/21", "766");
					System.out.println("Reserva " + codigoReserva + " feita com sucesso"); 
				}
			} catch (OperacaoProibidaException e) {
				System.out.println("O Comerciante nao pode executar esta operacao");
			} catch (PagamentoSemSucessoException e) {
				System.out.println("Ocorreu um erro no pagamento");
			}

		});

		Optional<Sessao> talvezSessao4 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao4.ifPresent( (Sessao s) -> {
			
		});
	}
}

