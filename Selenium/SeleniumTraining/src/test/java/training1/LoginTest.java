 package training1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	public static WebDriver driver;
	public static String browser = "chrome";

	public static List<TestScenario> testScenarios = new ArrayList<>();

	public static void main(String[] args) {

		// Test Scenarios
		//testScenarios.add(new TestScenario("Valid Login", "HRC", "systemadmin", "Successful"));
		//testScenarios.add(new TestScenario("Valid Username + Spacing", "HRC ", "stemadmin", "Unsuccessful"));
		testScenarios.add(new TestScenario("Valid Password + Spacing", "HRC", "systemadmin ", "Unsuccessful"));
		testScenarios.add(new TestScenario("Invalid Password", "HRC", "wrongpass", "Unsuccessful"));
		testScenarios.add(new TestScenario("Invalid Account", "who", "wrongpass", "Unsuccessful"));
		testScenarios.add(new TestScenario("Empty Field/s", "", "", "Unsuccessful"));

		runTestCases();

	}

//	--------------------------------------------------------	Functions	--------------------------------------------------------	//  

	public static void runTestCases() {

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.get("http://localhost:94/");

		runLoginValidationScenarios();

	}

	// Run Login Test Scenarios //WebDriver
	public static void runLoginValidationScenarios() {

		for (TestScenario scenario : testScenarios) {

			try {

				// Application site for QA
				driver.get("http://localhost:88/");
				
				//System.out.println("Begin Scenario"); 

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

				WebElement usernameField = driver.findElement(By.id("txtUsrNme"));
				WebElement passwordField = driver.findElement(By.id("txtPassword"));
				WebElement loginButton = driver.findElement(By.id("loginID"));

				WebElement lblError = driver.findElement(By.id("lblError"));
				String errorText = lblError.getText().trim(); 

				usernameField.clear();
				usernameField.sendKeys(scenario.getUsername());
				passwordField.clear();
				passwordField.sendKeys(scenario.getPassword());

				loginButton.click(); 
 
                // **Validation: Check if login was successful**
                Thread.sleep(2000); // Wait for page to load (better to use WebDriverWait)

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-menu")));
				
				//boolean loginSuccess = isElementPresent(By.className("user-menu"));
				//boolean isErrorPresent = isElementPresent(By.id("lblError"));
				//String errorText = driver.findElement(By.id("lblError")).getText();
						//isElementPresent(By.id("lblError")) ? driver.findElement(By.id("lblError")).getText().trim() : "";
                      

				//System.out.println("Error Text: " + errorText.toString());
				 
				System.out.println(errorText);
				
				if (isElementPresent(By.className("user-menu"))) {
					scenario.setResult("Login successful");
				} 

			} catch (Exception e) {
				// System.out.println("Error in test case: ");
				e.printStackTrace();
				scenario.setResult("Test Failed Due to Exception");
			}

		}

		driver.quit(); // Close browser and restart for next scenario
//		
//		for (int i = 0; i < testScenarios.size(); i++) {
//			System.out.println("Index " + i + ": " + testScenarios.get(i));
//		}
	}

	// Utility function to check if an element is present
	public static boolean isElementPresent(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

//	********************************************************	Class	********************************************************	//

	// TestScenario Class
	static class TestScenario {
		private String testName;
		private String username;
		private String password;
		private String expected;
		private String result;

		public TestScenario(String testName, String username, String password, String expected) {
			this.testName = testName;
			this.username = username;
			this.password = password;
			this.expected = expected;
			this.result = "Not executed";
		}

		public String getTestName() {
			return testName;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getExpected() {
			return expected;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		@Override
		public String toString() {
			return testName + " | Username: " + username + " | Password: " + password + " | Expected: " + expected
					+ " | Result: " + result;
		}
	}
}
