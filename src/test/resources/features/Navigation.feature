Feature: Login con certificado digital
  Para ver las paginas de BancaICO 
  Puedo hacer el log con certificado digital

  Scenario: Usuario inicia sesion con certificado digital
    Given I navigate to BancaICO
    When I log in using my digital certificate
    Then I should see the home page
