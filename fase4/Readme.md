Too Yummy To Go - Meta 4
========================

Objectivo
---------

Nesta fase vamos melhorar o nosso código recorrendo a vários padrões GoF. Nomeadamente:


1. Devem ser suportados ambos os meios de pagamento por cartão de crédito MonsterCard e PortugueseExpress, usando o padrão mais adequado.
2. Os diferentes métodos de pesquisa devem ser abstraídos segundo o padrão Strategy.
3. O comerciante deve ser notificado sempre que uma encomenda é feita com sucesso, de forma decoupled. (Dica: poderá alterar o cliente de forma a este registar um callback que faz print da informação da encomenda, de forma a poder preparar o pedido)
4. Deverá escrever um teste JUnit que cobra o caso de uso login (UC2), bem como a verificação dos handlers a que tem acesso.