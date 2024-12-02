package orobatask;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTestWithExtentReport {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        // إعداد ExtentReports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Login Functionality Test");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");

        // إعداد المتصفح
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() {
        test = extent.createTest("Test Login", "Verify login functionality with valid credentials");

        try {
            // الانتقال إلى صفحة تسجيل الدخول
            driver.get("https://opensource-demo.orangehrmlive.com/");
            test.pass("Navigated to login page");

            // إدخال اسم المستخدم
            WebElement username = driver.findElement(By.id("username"));
            username.sendKeys("testuser");
            test.pass("Entered username");

            // إدخال كلمة المرور
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("password123");
            test.pass("Entered password");

            // النقر على زر تسجيل الدخول
            WebElement loginButton = driver.findElement(By.id("loginButton"));
            loginButton.click();
            test.pass("Clicked on login button");

            // التحقق من النجاح
            WebElement welcomeMessage = driver.findElement(By.id("welcomeMessage"));
            Assert.assertTrue(welcomeMessage.isDisplayed());
            test.pass("Login successful and welcome message is displayed");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        // إغلاق المتصفح
        if (driver != null) {
           // driver.quit();
        }

        // إنشاء التقرير
        extent.flush();
    }
}

