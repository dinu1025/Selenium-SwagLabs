package pages.swaglabs;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {
    public static final Logger logger = Logger.getLogger(BasePage.class.getName());
    private final By pageHeading = By.className("title");
    private final By addToCartButton = By.id("add-to-cart");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeading() {
        return getText(pageHeading);
    }

    public By getProductNameLocator(String productName) {
        return By.xpath("//a/div[text()='" + productName + "']");
    }

    public void clickOnProductName(String productName) {
        click(getProductNameLocator(productName));
    }

    public void addProductToCart(String productName) {
        clickOnProductName(productName);
        click(addToCartButton);
    }

    public CartsPage navigatesToCart() {
        click(cartIcon);
        return new CartsPage(driver);
    }
}
