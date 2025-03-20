package steps;

import io.cucumber.java.en.*;
import pages.PaginaPrincipal;
import pages.BasePage;

public class NavigationSteps {

    PaginaPrincipal landingPage = new PaginaPrincipal();

    @Given("I navigate to BancaICO")
    public void iNavigateToICO() {
        System.out.println(">> Dado que navego a BancaICO");
        landingPage.navigateToBancaICO();
        landingPage.clickOnSectionNavigationBar();
    }

    @When("I log in using my digital certificate")
    public void loginWithCertificate() {
        System.out.println(">> Cuando hago login con certificado - HILO PARA ROBOT");

        // 1) Lanzamos un hilo que duerme 2s y luego hace la selección
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("--> Hilo: seleccionando certificado...");
                BasePage basePage = new BasePage(); 
                basePage.seleccionarCertificadoDigital();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 2) En el hilo principal, si hay un botón que abre la ventana, lo llamamos
        landingPage.clickAcceso();

        // (Si tu ventana sale sola al cargar la URL, no pasa nada,
        //  igual se sincroniza con el Robot).
    }

    @Then("I should see the home page")
    public void verifyHomePage() {
        System.out.println(">> Entonces verifico la home page");
        String expectedTitle = "Banc@ICO";
        String actualTitle = BasePage.getDriver().getTitle();
        System.out.println("Título actual: " + actualTitle);

        assert actualTitle.equals(expectedTitle)
            : "ERROR: Se esperaba [" + expectedTitle + "] pero se encontró [" + actualTitle + "]";
    }
}
