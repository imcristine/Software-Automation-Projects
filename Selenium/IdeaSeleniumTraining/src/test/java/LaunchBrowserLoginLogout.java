import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaunchBrowserLoginLogout {

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
        //driver.manage().window().maximize();
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

//        Point point = driver.manage().window().getPosition();
//        System.out.println("Point x: " + point.x);
//        System.out.println("Point y: " + point.y);
//
//        Dimension dimension = new Dimension(1200, 800);
//        driver.manage().window().setSize(dimension);

        driver.findElement(By.id("txtUsrNme")).sendKeys("HRC");
        driver.findElement(By.id("txtPassword")).sendKeys("systemadmin");

        String btnValue = driver.findElement(By.id("loginID")).getText();
        System.out.println("btnvalue: " + btnValue);

        //assertTrue(btnValue.equals("Login"));
        //if(driver.findElement(By.id("loginID")).isDisplayed()){
            driver.findElement(By.id("loginID")).click();
        //}

        System.out.println("Login Successful!");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        assertEquals("http://localhost:94/Dashboard/Index", driver.getCurrentUrl());

    }

    @Test
    public void validateLogout(){

        driver.manage().window().maximize();
        validateLogin();

        WebElement dropdownToggle = driver.findElement(By.xpath("//li[contains(@class, 'main-drop')]//a[contains(@class, 'dropdown-toggle nav-link')]"));

        // Get the initial aria-expanded attribute value
        String actualAriaExpanded = dropdownToggle.getDomAttribute("aria-expanded");

        // Expected default value before expanding is "false" or null (meaning collapsed)
        String expectedInitialValue = "false";

        // Assert initial state
        if (actualAriaExpanded == null) {
            actualAriaExpanded = "false";
        }

        System.out.println("Initial aria-expanded value: " + actualAriaExpanded);

        Assertions.assertEquals(expectedInitialValue, actualAriaExpanded,
                "Initial aria-expanded value should be 'false' indicating collapsed dropdown");

        // If collapsed, click to expand
        if ("false".equals(actualAriaExpanded)) {
            System.out.println("Expanding dropdown...");
            dropdownToggle.click();

            // Wait until aria-expanded becomes "true"
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.attributeToBe(dropdownToggle, "aria-expanded", "true"));

            // Verify that aria-expanded updated correctly
            String actualAfterClick = dropdownToggle.getDomAttribute("aria-expanded");
            System.out.println("aria-expanded value after click: " + actualAfterClick);

            String expectedAfterClick = "true";
            Assertions.assertEquals(expectedAfterClick, actualAfterClick,
                    "aria-expanded should be 'true' after expanding the dropdown");
        } else {
            System.out.println("Dropdown is already expanded.");
            // Optionally assert this state too
            Assertions.assertEquals("true", actualAriaExpanded,
                    "aria-expanded should be 'true' if dropdown is already expanded");
        }



//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector(".dropdown-menu.dropdown-menu-right")));

//        List<WebElement> items = menu.findElements(By.cssSelector("a.dropdown-item"));
//        for (WebElement item : items) {
//            System.out.println("Menu item: " + item.getText());
//        }

//        for (WebElement item : items) {
//            if (item.getText().equalsIgnoreCase("Log off")) {
//                item.click();
//                wait.until(ExpectedConditions.urlContains("/Account/LogOff"));
//                System.out.println("Redirected to: " + driver.getCurrentUrl());
//                break;
//            }
//        }

        //System.out.println("Logout Successful!");



        //        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
        //                By.cssSelector(".dropdown-menu.dropdown-menu-right")));
        //
        //        List<WebElement> menuItems = dropdownMenu.findElements(By.tagName("a"));
        //
        //        assertEquals(menuItems.get(0).getText(), "Save layout changes");
        //        assertEquals(menuItems.get(1).getText(), "Reset layout");
        //        assertEquals(menuItems.get(2).getText(), "Change Password");
        //        assertEquals(menuItems.get(3).getText(), "Log off");
        //
        //        menuItems.get(3).click();
        //        wait.until(ExpectedConditions.urlContains("/Account/LogOff"));
        //        assertTrue(driver.getCurrentUrl().contains("LogOff"));

    }

}
