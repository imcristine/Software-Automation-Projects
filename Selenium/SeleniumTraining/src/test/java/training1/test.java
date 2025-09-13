package training1;

import java.util.ArrayList;
import java.util.Arrays; 

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver; 

import io.github.bonigarcia.wdm.WebDriverManager;

public class test {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup(); 
        
        ArrayList<String[]> testScenarios = new ArrayList<>();
        testScenarios.add(new String[] { "Valid Login", "HRC", "systemadmin" });
        testScenarios.add(new String[] { "Invalid Password", "HRC", "wrongpass" });
        testScenarios.add(new String[] { "Empty Fields", "", "" });

		int count = 0; // Track the test case index
		
        for (String[] scenario : testScenarios) {
            WebDriver driver = new ChromeDriver(); // Restart browser for each test 
            driver.get("http://localhost:88/");
 
            try {
                // Locate username & password fields
                WebElement usernameField = driver.findElement(By.id("txtUsrNme"));
                WebElement passwordField = driver.findElement(By.id("txtPassword"));
                WebElement loginButton = driver.findElement(By.id("loginID"));

                // Clear and enter credentials
                usernameField.clear();
                usernameField.sendKeys(scenario[1]);

                passwordField.clear();
                passwordField.sendKeys(scenario[2]);

                loginButton.click();

                // **Validation: Check if login was successful**
                Thread.sleep(2000); // Wait for page to load (better to use WebDriverWait)
                WebElement errorLabel = driver.findElement(By.id("lblError"));
                String errorText = errorLabel.getText().trim();
                
                if (isElementPresent(driver, By.id("logoutID"))) {
					//testScenarios.get(count)[3] = "Login successful";
                    System.out.println("✅ SUCCESS: " + scenario[0] + " - Login successful.");
                } else if ( !errorText.isEmpty()) {
					//testScenarios.get(count)[3] = "Unsuccessful Login";
                    System.out.println("❌ FAILED: " + scenario[0] + " - Error Message Displayed.");
                } else {
					//testScenarios.get(count)[3] = "Empty Field/s"; 
                    System.out.println("⚠️ UNKNOWN: " + scenario[0] + " - No expected element found.");
                }
                

    			count++;

            } catch (Exception e) {
                //System.out.println("🚨 Error running test: " + scenario[0]);
                e.printStackTrace();
            }

            driver.quit(); // Close browser and restart for next scenario 
            
        }

        
        System.out.println("\n========= COMPLETE TEST SCENARIOS DATA =========");
        for (int i = 0; i < testScenarios.size(); i++) {
            System.out.println("Test " + (i + 1) + ": " + Arrays.toString(testScenarios.get(i)));
        }
         
        
    }

    // Utility function to check if an element is present
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
