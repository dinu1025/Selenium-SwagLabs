package tests.SwagLabs;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.swaglabs.BasePage;
import pages.swaglabs.LoginPage;
import utils.UtilsConfig;

import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties properties;
    protected Properties XPATH;
    protected ChromeOptions options;
    public static final Logger logger = Logger.getLogger(BasePage.class.getName());
    private String browser;

    @BeforeClass
    public void setupBeforeTest() {
        UtilsConfig conifg = new UtilsConfig();
        properties = conifg.getProperties("SwagLabs");
        browser = properties.getProperty("browserName");
        driver = getDriver(browser);
    }

    private WebDriver getDriver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--incognito");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
            WebDriverManager.iedriver().setup();
			return new InternetExplorerDriver();
		}
        throw new IllegalArgumentException("Invalid browser name: " + browserName + ". Supported browsers: chrome, firefox, IE");
    }

    public String getAppUrl() {
        return properties.getProperty("URL");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getLockedUserName() {
        return properties.getProperty("locked_out_user");
    }

    public String getProductName() {
        return properties.getProperty("product_name");
    }

    public LoginPage navigatesToSwagLabs() {
        driver.get(getAppUrl());
        return new LoginPage(driver);
    }

    @AfterClass
    public void setupAfterSuite() {
        driver.quit();
        logger.info("******* Tests completed ********");
    }
}
