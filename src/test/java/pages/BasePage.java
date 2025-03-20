package pages;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;  
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

    protected static WebDriver driver;
    protected WebDriverWait wait;
    private static Properties properties = new Properties();

    // Indica cuántas flechas “DOWN” (p.ej. 1 para ir al 2º certificado)
    private static final int CERTIFICADO_POSICION = 2; 

    private static String originalWindowHandle;

    static {
        System.out.println("¿Entorno headless? " + GraphicsEnvironment.isHeadless());

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--lang=es");

        driver = new ChromeDriver(options);

        originalWindowHandle = driver.getWindowHandle();
        System.out.println("Handle original = " + originalWindowHandle);
    }

    public BasePage() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void navigateTo(String url) {
        System.out.println("Navegando a URL: " + url);
        driver.get(url);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement find(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void clickElement(String locator) {
        find(locator).click();
    }

    /**
     * Automatiza la selección de certificado:
     * - Flecha DOWN (CERTIFICADO_POSICION - 1) veces
     * - 1er TAB -> "Certificate information"
     * - 2do TAB -> Botón "OK"
     * - ENTER -> Confirmar
     */
    public void seleccionarCertificadoDigital() {
        try {
            Robot robot = new Robot();
            System.out.println(">> Esperando ventana nativa para certificado...");
            robot.delay(3000); // Da tiempo a que aparezca

            traerVentanaAlFrente();
            robot.delay(1500);

            int downPress = CERTIFICADO_POSICION - 1;  
            System.out.println(">> Pulsando DOWN " + downPress + " veces");
            for (int i = 0; i < downPress; i++) {
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.delay(500);
            }

            // 1er TAB -> "Certificate information"
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(500);

            // 2do TAB -> Botón "OK"
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(500);

            // Enter para confirmar
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            robot.delay(1000);
            System.out.println(">> Certificado seleccionado con Robot. Revisamos ventanas...");

            switchToLastWindow();

        } catch (AWTException e) {
            e.printStackTrace();
            System.err.println("Error Robot: " + e.getMessage());
        }
    }

    private void traerVentanaAlFrente() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void switchToLastWindow() {
        try {
            Set<String> allHandles = driver.getWindowHandles();
            System.out.println("Handles actuales: " + allHandles);
            if (allHandles.size() == 1) {
                for (String h : allHandles) {
                    driver.switchTo().window(h);
                }
                return;
            }
            String lastHandle = "";
            for (String h : allHandles) {
                lastHandle = h;
            }
            driver.switchTo().window(lastHandle);
            System.out.println("Cambiado a handle: " + lastHandle);

        } catch (Exception e) {
            System.err.println("Error cambiando ventana: " + e.getMessage());
        }
    }
}
