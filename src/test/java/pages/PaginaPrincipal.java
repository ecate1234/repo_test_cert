package pages;

import java.sql.DriverManager;


public class PaginaPrincipal extends BasePage {
    
    private String advancedButton ="//*[@id=\"details-button\"]";
    private String proceedButton ="//*[@id=\"proceed-link\"]";
    public PaginaPrincipal() {
        super(driver);
    }
 
    // Método para navegar a BancaICO
    public void navigateToBancaICO() {
        navigateTo("https://integra4.ico.red/mediacion/home/home.jsp?index=");
    }


    public void clickOnSectionNavigationBar(){
        clickElement(advancedButton);
        clickElement(proceedButton);
    }
    public void realizarLoginConCertificado() {
         // Ejecutar `Robot` en un hilo antes de hacer clic en el enlace de acceso
        
                System.out.println("Pagina principal Llama SeleccionarCertificado Digital");
            seleccionarCertificadoDigital();
               // certificateUtil.seleccionarCertificado(BasePage.getCertificatePosition());
           
        }
       
    }
 

