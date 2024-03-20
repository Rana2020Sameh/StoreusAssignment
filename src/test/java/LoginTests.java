import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class LoginTests {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized");
        }
        driver.get("https://storeus.com/");
        // driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        Thread.sleep(20000);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='survey-frame-~5g1c5ba']")));
        driver.findElement(By.id("survey-close-div")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='notification-frame-b8a54917']")));
        driver.findElement(By.id("webklipper-publisher-widget-container-notification-close-div")).click();
        driver.switchTo().defaultContent();
    }

    @Test(priority = 0)
    public void loginWithValidData() {
        WebElement LoginLink = driver.findElement(By.xpath("//span[@class='bold-login-message']"));
        driver.switchTo().defaultContent();
        LoginLink.click();
        driver.switchTo().defaultContent();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        WebElement emailAddress = driver.findElement(By.id("email"));
        emailAddress.sendKeys("RanasamehTest@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("RanasamehTest@Test");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement loggedinuser = driver.findElement(By.xpath("//span[@id='lock-button']"));
        String loggedUser = loggedinuser.getText();
        Assert.assertEquals(loggedUser, "HelloRANA");
    }

    @Test(priority = 1)
    public void loginWithInValidData() {
        WebElement LoginLink = driver.findElement(By.xpath("//span[@class='bold-login-message']"));
        driver.switchTo().defaultContent();
        LoginLink.click();
        driver.switchTo().defaultContent();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        WebElement emailAddress = driver.findElement(By.id("email"));
        emailAddress.sendKeys("RanasamehTest@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("RanasamehTest@T");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement incorrect = driver.findElement(By.xpath("//div[@class='MuiSnackbarContent-message css-1w0ym84']"));
        String incorrectMess = incorrect.getText();
        Assert.assertEquals(incorrectMess, "Incorrect password. Please try again.");

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
