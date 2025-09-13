package training1;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager; 

public class SeleniumScript1 {

    public static void main(String[] args) {
         
    	WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        
        driver.get("https://www.google.com/"); 
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        
        
        

        //driver.quit();
        
        
    }
}
