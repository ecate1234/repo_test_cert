package steps;

import io.cucumber.java.en.*;
import pages.BasePage;
import pages.PaginaPrincipal;
 
public class NavigationSteps {
 
    PaginaPrincipal landingPage = new PaginaPrincipal();
 
    @Given("I navigate to BancaICO")
    public void iNavigateToICO() {
        landingPage.navigateToBancaICO();
        
    }
     @When("I log in using my digital certificate")
    public void loginWithCertificate() {
       
            System.out.println("NAv Step ");
            landingPage.realizarLoginConCertificado();
        
    }
   
    @Then("I should see the home page")
    public void verifyHomePage() {
        String expectedTitle = "Banc@ICO";  // Ajusta este valor según tu aplicación
        String actualTitle = BasePage.getDriver().getTitle();

    // Validamos que el título sea correcto
    assert actualTitle.equals(expectedTitle) : "ERROR: Se esperaba " + expectedTitle + " pero se encontró " + actualTitle;
    }
 
}