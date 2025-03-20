package pages;

public class PaginaPrincipal extends BasePage {

    private String advancedButton = "//*[@id='details-button']";
    private String proceedButton = "//*[@id='proceed-link']";

    public PaginaPrincipal() {
        super();
    }

    public void navigateToBancaICO() {
        navigateTo("https://integra4.ico.red/mediacion/home/home.jsp?index=");
    }

    // Por si Chrome dice "No es seguro"
    public void clickOnSectionNavigationBar() {
        System.out.println("Haciendo click en Advanced + Proceed (si aparecen)...");
        try {
            clickElement(advancedButton);
            clickElement(proceedButton);
        } catch (Exception e) {
            System.out.println("No apareció la alerta de sitio inseguro. Continuamos normalmente...");
        }
    }

    // Si hay un botón en la página que "definitivamente" dispara la ventana
    // lo implementas aquí. Si no, déjalo vacío.
    public void clickAcceso() {
        System.out.println("Simulando el click que dispara la ventana de certificados (si aplica).");
        // clickElement("//button[@id='btnAcceder']"); // Ejemplo
    }

    public void realizarLoginConCertificado() {
        System.out.println("== Selección de certificado con Robot (sin hilos) ==");
        seleccionarCertificadoDigital();
    }
}
