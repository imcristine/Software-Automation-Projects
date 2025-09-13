package QATest;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.chrome.ChromeDriver; 

public class SeleniumScript1 {

    public static void main(String[] args) {
         
    	//System.setProperty("webdriver.chrome.driver", "C:\\Selenium webdriver\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
 
        //WebDriver driver = new ChromeDriver();
        
        
    	System.setProperty("webdriver.edge.driver", "C:\\Selenium webdriver\\EdgeDriver\\edgedriver_win64\\msedgedriver.exe");
 
        WebDriver driver = new EdgeDriver();
        
        
        driver.get("https://www.facebook.com/"); 
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        //driver.quit();
        
        
    }
}
