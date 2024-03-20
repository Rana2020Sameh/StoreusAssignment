import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SignUpTests {

    WebDriver driver;

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
        WebElement LoginLink = driver.findElement(By.xpath("//span[@class='bold-login-message']"));
        driver.switchTo().defaultContent();
        LoginLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("footer-button-width")));
        signupElement.click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
    }

    @Test(priority = 0)
    public void signUPWithValidData() {
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Rana");
        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("sameh");
        WebElement emailAddress = driver.findElement(By.id("login"));
        emailAddress.sendKeys("Ranasahmny43rhawet@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("Ranasamehwer23@Test");
        WebElement passwordConfirmation = driver.findElement(By.id("passwordConfirmation"));
        passwordConfirmation.sendKeys("Ranasamehwer23@Test");
        WebElement createButton = driver.findElement(By.xpath("//button[@type='submit']"));
        createButton.click();
        WebElement loggedinuser = driver.findElement(By.xpath("//span[@id='lock-button']"));
        String loggedUser = loggedinuser.getText();
        Assert.assertEquals(loggedUser, "HelloRANA");

    }

    @Test(priority = 1)
    public void signupWithInvalidEmail() {
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Rana");
        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("sameh");
        WebElement emailAddress = driver.findElement(By.id("login"));
        emailAddress.sendKeys("Ranasam34herr");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("RanasamehTs23@Test");
        WebElement passwordConfirmation = driver.findElement(By.id("passwordConfirmation"));
        passwordConfirmation.sendKeys("RanasamehTs23@Test");
        WebElement createButton = driver.findElement(By.xpath("//button[@type='submit']"));
        createButton.click();
        WebElement invalidMail = driver.findElement(By.id("login-helper-text"));
        String invalidMaiL = invalidMail.getText();
        Assert.assertEquals(invalidMaiL, "please enter a valid email");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
