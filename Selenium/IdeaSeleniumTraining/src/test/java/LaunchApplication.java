import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.jupiter.api.Assertions.*;

public class LaunchApplication {

    public static String browser = System.getProperty("browser", "firefox");
    public static WebDriver driver;

    @BeforeEach
    public void setUp() {
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
        }

        driver.get("http://localhost:94/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void validateTitle() {
        System.out.println("Title: " + driver.getTitle());
        assertEquals("Aladdin - Login", driver.getTitle());
    }

    @Test
    public void validateLogin(){
        driver.findElement(By.id("txtUsrNme")).sendKeys("HRC");
        driver.findElement(By.id("txtPassword")).sendKeys("systemadmin");
        driver.findElement(By.id("loginID")).click();

        System.out.println("Current URL: " + driver.getCurrentUrl());
        assertEquals("http://localhost:94/Dashboard/Index", driver.getCurrentUrl());
    }
}
