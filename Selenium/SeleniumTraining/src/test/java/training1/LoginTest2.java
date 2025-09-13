package training1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

public class LoginTest2 {

	public static void main(String[] args) {

		class TestScenario {
			String testName;
			String username;
			String password;
			String expected;
			String result; // Store test result

			public TestScenario(String testName, String username, String password, String expected) {
				this.testName = testName;
				this.username = username;
				this.password = password;
				this.expected = expected;
				this.result = "Not executed"; // Default value
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
				return testName + " | Username: " + username + " | Password: " + password + " | Expected: : " + expected
						+ " | Result: " + result;
			}
		}

		// Test Scenarios
		List<TestScenario> testScenarios = new ArrayList<>();
		testScenarios.add(new TestScenario("Valid Login", "HRC", "systemadmin", "Successful"));
		testScenarios.add(new TestScenario("Valid Username + Spacing", "HRC ", "systemadmin", "Unsuccessful"));
		testScenarios.add(new TestScenario("Valid Password + Spacing", "HRC", "systemadmin ", "Unsuccessful"));
		testScenarios.add(new TestScenario("Invalid Password", "HRC", "wrongpass", "Unsuccessful"));
		testScenarios.add(new TestScenario("Invalid Account", "who", "wrongpass", "Unsuccessful"));
		testScenarios.add(new TestScenario("Empty Field/s", "", "", "Unsuccessful"));
		testScenarios.add(new TestScenario("Empty Field/s", "", "passwordonly", "Unsuccessful"));
		testScenarios.add(new TestScenario("Empty Field/s", "usernameonly", "", "Unsuccessful"));

		WebDriverManager.chromedriver().setup();
 
		for (TestScenario scenario : testScenarios) {

			WebDriver driver = new ChromeDriver(); // Restart browser for each test
			driver.get("http://localhost:88/");

			// --------------------------
			// Get timestamp in YYYYMMDDHHmmSS format
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            // Generate the screenshot filename
            String screenshotFileName = "test_screenshot_" + timestamp + ".png";
 
			
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

				// Locate username & password fields
				WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtUsrNme")));
				WebElement passwordField = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.id("txtPassword")));
				WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginID")));

				// Clear and enter credentials
				usernameField.clear();
				usernameField.sendKeys(scenario.getUsername());

				passwordField.clear();
				passwordField.sendKeys(scenario.getPassword());

	            // Take screenshot and get the file path
	            String screenshotPath = takeScreenshot(driver, screenshotFileName); 
				// Save test case result to Excel
				saveToExcel("test_results.xlsx", "Homepage Load Test", "Passed", screenshotPath);
				
				loginButton.click();

				// **Validation: Check if login was successful**
				Thread.sleep(2000); // Give time for UI updates

				boolean loginSuccess = isElementPresent(driver, By.className("user-menu"));
				boolean isErrorPresent = isElementPresent(driver, By.id("lblError"));
				String errorText = isErrorPresent ? driver.findElement(By.id("lblError")).getText().trim() : "";

				if (loginSuccess) {
					scenario.setResult("Login successful");
					// System.out.println("✅ SUCCESS: " + scenario.getTestName() + " - Login
					// successful.");
				} else if (!errorText.isEmpty()) {
					scenario.setResult("Unsuccessful Login");
					// System.out.println("❌ FAILED: " + scenario.getTestName() + " - Error Message:
					// " + errorText);
				} else {
					scenario.setResult("Empty Field/s");
					// System.out.println("⚠️ UNKNOWN: " + scenario.getTestName() + " - No expected
					// element found.");
				}

			} catch (Exception e) {
				//System.out.println("🚨 Error in test: " + scenario.getTestName());
				e.printStackTrace();
			}

			// Take screenshot and get the file path
            String screenshotPath = takeScreenshot(driver, screenshotFileName); 
			// Save test case result to Excel
			saveToExcel("test_results.xlsx", "Homepage Load Test", "Passed", screenshotPath);

			driver.quit(); // Close browser and restart for next scenario
		}

		// Print final test results
		System.out.println("\nTest Results:");
		for (TestScenario scenario : testScenarios) {
			System.out.println(scenario);
		}
	}

	// Utility function to check if an element is present
	public static boolean isElementPresent(WebDriver driver, By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

// ------------------------------------------------------------------------------------------------------------------------------------

	// Function to capture screenshot
	public static String takeScreenshot(WebDriver driver, String fileName) {
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(screenshot.toPath(), new File(fileName).toPath());
			System.out.println("Screenshot saved: " + fileName);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to take screenshot";
		}
	}

	// Function to save test results to an Excel file
	public static void saveToExcel(String excelPath, String testCase, String status, String screenshotPath) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Test Results");

			// Add headers
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Test Case");
			headerRow.createCell(1).setCellValue("Status");
			headerRow.createCell(2).setCellValue("Screenshot Path");

			// Add test case result
			Row row = sheet.createRow(1);
			row.createCell(0).setCellValue(testCase);
			row.createCell(1).setCellValue(status);
			row.createCell(2).setCellValue(screenshotPath);

			// Save to Excel file
			FileOutputStream fileOut = new FileOutputStream(excelPath);
			workbook.write(fileOut);
			System.out.println("Results saved in " + excelPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
