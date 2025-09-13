package training1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.Duration;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest4 {

    public static void main(String[] args) {
        // Test Scenarios
        List<TestScenario> testScenarios = new ArrayList<>();
        testScenarios.add(new TestScenario("Valid Login", "HRC", "systemadmin", "Successful"));
        testScenarios.add(new TestScenario("Valid Username + Spacing", "HRC ", "systemadmin", "Unsuccessful"));
        testScenarios.add(new TestScenario("Valid Password + Spacing", "HRC", "systemadmin ", "Unsuccessful"));
        testScenarios.add(new TestScenario("Invalid Password", "HRC", "wrongpass", "Unsuccessful"));
        testScenarios.add(new TestScenario("Invalid Account", "who", "wrongpass", "Unsuccessful"));
        testScenarios.add(new TestScenario("Empty Field/s", "", "", "Unsuccessful"));

        WebDriverManager.chromedriver().setup();

        // Set directory paths
        String projectPath = System.getProperty("user.dir");
        String screenshotDir = projectPath + "/screenshots/LoginTest";  
        String excelDir = projectPath + "/results/LoginTest";           
        createDirectory(screenshotDir);
        createDirectory(excelDir);

        // Excel file path
        String excelPath = excelDir + "/test_results.xlsx";

        // Initialize WebDriver only once
        WebDriver driver = new ChromeDriver();
        
        System.out.println("Total test scenarios: " + testScenarios.size()); // Debugging

        for (TestScenario scenario : testScenarios) {
            System.out.println("Running test: " + scenario.getTestName()); // Debugging line

            try {
                driver.get("http://localhost:88/");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtUsrNme")));
                WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtPassword")));
                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginID")));

                usernameField.clear();
                usernameField.sendKeys(scenario.getUsername());
                passwordField.clear();
                passwordField.sendKeys(scenario.getPassword());

                loginButton.click();
                Thread.sleep(2000);

                boolean loginSuccess = isElementPresent(driver, By.className("user-menu"));
                boolean isErrorPresent = isElementPresent(driver, By.id("lblError"));
                String errorText = isErrorPresent ? driver.findElement(By.id("lblError")).getText().trim() : "";

                if (loginSuccess) {
                    scenario.setResult("Login successful");
                } else if (!errorText.isEmpty()) {
                    scenario.setResult("Unsuccessful Login: " + errorText);
                } else {
                    scenario.setResult("Unknown result");
                }

                System.out.println("Test result: " + scenario.getResult());

            } catch (Exception e) {
                System.out.println("Error in test case: " + scenario.getTestName());
                e.printStackTrace();
                scenario.setResult("Test Failed Due to Exception");
            }
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

    // Function to create a directory if it does not exist
    public static void createDirectory(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Created folder: " + folderPath);
        }
    }

    // Function to capture screenshot
    public static void takeScreenshot(WebDriver driver, String filePath) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), new File(filePath).toPath());
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to save each test result in an Excel file
    public static void saveToExcel(String filePath, TestScenario scenario, String screenshotPath) {
        try {
            Workbook workbook;
            Sheet sheet;

            File file = new File(filePath);
            if (file.exists() && !file.canWrite()) {
                System.out.println("Excel file is locked. Skipping...");
                return;
            }

            if (file.exists()) {
                workbook = WorkbookFactory.create(file);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Test Results");

                // Add headers if creating a new file
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Test Case");
                headerRow.createCell(1).setCellValue("Username");
                headerRow.createCell(2).setCellValue("Password");
                headerRow.createCell(3).setCellValue("Expected Result");
                headerRow.createCell(4).setCellValue("Actual Result");
                headerRow.createCell(5).setCellValue("Screenshot Path");
            }

            // Find the next empty row
            int rowCount = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(scenario.getTestName());
            row.createCell(1).setCellValue(scenario.getUsername());
            row.createCell(2).setCellValue(scenario.getPassword());
            row.createCell(3).setCellValue(scenario.getExpected());
            row.createCell(4).setCellValue(scenario.getResult());
            row.createCell(5).setCellValue(screenshotPath);

            // Save Excel file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Results saved in " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        public String getTestName() { return testName; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getExpected() { return expected; }
        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }

        @Override
        public String toString() {
            return testName + " | Username: " + username + " | Password: " + password +
                   " | Expected: " + expected + " | Result: " + result;
        }
    }
}
