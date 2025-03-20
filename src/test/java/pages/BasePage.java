package pages;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
// Importaciones necesarias
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


import org.openqa.selenium.remote.RemoteWebDriver;


import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.Properties;


public class BasePage {
    /*
     * Declaración de una variable estática 'driver' de tipo WebDriver
     * Esta variable va a ser compartida por todas las instancias de BasePage y sus subclases
     */
    protected static WebDriver driver;
    private static Properties properties = new Properties();
    
    /*
     * Declaración de una variable de instancia 'wait' de tipo WebDriverWait.
     * Se inicializa inmediatamente con una instancia dew WebDriverWait utilizando el 'driver' estático
     * WebDriverWait se usa para poner esperas explícitas en los elementos web
     */
    WebDriverWait wait;
 
    /* 
     * Configura el WebDriver para Chrome usando WebDriverManager.
     * WebDriverManager va a estar descargando y configurando automáticamente el driver del navegador
    */
    static {
    WebDriverManager.chromedriver().setup();
    //Inicializa la variable estática 'driver' con una instancia de ChromeDriver
    // driver = new ChromeDriver();
       // WebDriverManager.chromedriver().browserVersion("133.0.6943.143").setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking"); // Evita ventanas emergentes
        options.addArguments("--disable-extensions"); // Evita bloqueos por extensiones
        options.addArguments("--remote-allow-origins=*"); // Evita bloqueos de origen cruzado
        options.addArguments("--incognito"); // Para evitar almacenamiento de sesión
        options.addArguments("--no-sandbox-and-elevated"); 
        options.addArguments("--disable-dev-shm-usage"); 
        options.addArguments("--deny-permission-prompts"); 
        options.addArguments("--lang=es"); 
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-running-insecure-content");  


        driver = new ChromeDriver(options);
    }
 
    /*
     * Este es el constructor de BasePage que acepta un objeto WebDriver como argumento.
     */
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
 
    //Método estático para navegar a una URL.
    public static void navigateTo(String url) {
        driver.get(url);
    }

    //Limpia la instancia del driver
    public static void closeBrowser(){
        if(driver!= null){
            driver.quit();
        }
       
    }
    public static WebDriver getDriver() {
        return driver;
    }
        // Encuentra y devuelve un WebElement en la página utilizando un locator XPath, esperando a que esté presentente en el DOM
    private WebElement Find(String locator){

        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }
 
    public void clickElement(String locator){
        Find(locator).click();
    }

    public void write(String locator, String keysToSend){
        Find(locator).clear();
        Find(locator).sendKeys(keysToSend);
    }

    public void selectFromDropdownByValue(String locator, String value){

        Select dropdown = new Select(Find(locator)); 
        dropdown.selectByValue(value);
    }

    public void selectFromDropdownByIndex(String locator, Integer index){

        Select dropdown = new Select(Find(locator)); 
        dropdown.selectByIndex(index);
    }

    public int dropdownSize(String locator){

        Select dropdown=new Select(Find(locator));
        
        List<WebElement> dropdownOptions = dropdown.getOptions();

        return dropdownOptions.size();

    }
//Metodo que lee la propiedad PosicionCertificado, de cara a saber en que posicion del popUp de certificados se encuentra el certificado que se ha de utilizar
public static int getCertificatePosition(){
    return Integer.parseInt(properties.getProperty("PosicionCertificado"))-1;
}
    // Método para seleccionar el certificado digital y confirmar acceso
    public void seleccionarCertificadoDigital() {

        try {
            System.out.println("Intentando crear Robot...");
            Robot robot = new Robot();
            System.out.println("Robot creado exitosamente.");
            robot.delay(2000);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            System.out.println("tecla down");
            robot.delay(500); // Pequeña espera entre movimientos

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            System.out.println("tecla down");
            robot.delay(500); // Pequeña espera entre movimientos

        //seleccionamos el certificado
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        System.out.println("tecla TAB");
        robot.delay(1000); //Esperar a que se procese

        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        System.out.println("tecla TAB");
        robot.delay(1000); //Esperar a que se procese
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(1000);
        } catch (AWTException e) {
            System.out.println("Error al crear Robot: " + e.getMessage());
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("Permiso denegado: " + e.getMessage());
            e.printStackTrace();
        }
      


  
    }

}
