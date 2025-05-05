package pages.swaglabs;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import utils.UtilsConfig;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    public static final Logger logger = Logger.getLogger(BasePage.class.getName());
    Faker faker = new Faker();
    protected final Properties properties;
    protected final Actions actions;
    protected final By cartCount = By.cssSelector("span.shopping_cart_badge");
    protected final By cartIcon = By.cssSelector("a.shopping_cart_link");

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        UtilsConfig config = new UtilsConfig();
        this.properties = config.getProperties("SwagLabs");
        this.actions = new Actions(driver);
    }

    public String getPageURL(){
        return this.driver.getCurrentUrl();
    }
    
    public String getPageTitle(){
        return this.driver.getTitle();
    }

    public String getPageSource(){
        return this.driver.getPageSource();
    }
    
    public WebElement findElementBy(By locator) {
        logger.info(String.format("Finding element: %s", locator));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> findElementsBy(By locator) {
        logger.info(String.format("Finding all elements for: %s", locator));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void click(By locator) {
        try {
            WebElement element = findElementBy(locator);
            element.click();
            logger.info("Clicked on element: "+ locator);
        } catch (Exception e) {
            logger.error("Failed to click on element: " + locator, e);
            throw e;
        }
    }

    public void type(By locator, String text) {
        try {
            WebElement element = findElementBy(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed '" + text + "' into element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to type into element: "+locator, e);
            throw e;
        }
    }

    public String getText(By locator) {
        try {
            String text = findElementBy(locator).getText();
            logger.info("Got text '"+text+"' from element: "+ locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: "+locator, e);
            throw e;
        }
    }

    public boolean isDisplayed(By locator) {
        try {
            boolean visible = driver.findElement(locator).isDisplayed();
            logger.info("Element displayed status for " + locator + ": " + visible);
            return visible;
        } catch (TimeoutException | NoSuchElementException e) {
            logger.warn("Element not found for display check: "+ locator);
            return false;
        }
    }

    public void waitForVisibility(By locator) {
        logger.info("Waiting for visibility of element: "+ locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickability(By locator) {
        logger.info("Waiting for clickability of element: "+ locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = findElementBy(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: "+ locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: "+ locator, e);
            throw e;
        }
    }

    public int getCartItemCount() {
        if(isDisplayed(cartCount)) {
            return Integer.parseInt(getText(cartCount));
        } else {
            // If badge not present means cart empty, return cart count 0
            return 0;
        }
    }

    /*
     * Opening cart a common method can be accessed from any page
     */
    public CartsPage navigatesToCart() {
        click(cartIcon);
        return new CartsPage(driver);
    }

    /**
     * Common method to checkout the products in the cart, can be accessed from any page 
     * @return
     */
    public CheckoutPage checkoutProduct_fromCart() {
        CartsPage cartsPage = navigatesToCart();
        CheckoutPage checkoutPage = cartsPage.clickOnCheckoutButton();
        checkoutPage.checkoutYourInformation(faker.name().firstName(), faker.name().lastName(), faker.address().zipCode());
        checkoutPage.clickOnFinishButton();
        return checkoutPage;
    }
}

