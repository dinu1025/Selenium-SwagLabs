package pages.swaglabs;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartsPage extends BasePage {
    public static final Logger logger = Logger.getLogger(CartsPage.class.getName());
    private By checkoutButton = By.id("checkout");

    public CartsPage(WebDriver driver) {
        super(driver);
    }

    public Boolean isProductPresent_inCart(String productName) {
        return isDisplayed(By.xpath("//div[@class='inventory_item_name'][text()='"+productName+"']"));
    }
   
    public int getCountOfProducts_inCart_byProductName(String productName) {
        return Integer.parseInt(getText(By.xpath("//div[text()='"+productName+"']/parent::a/parent::div/preceding-sibling::div[@data-test=\"item-quantity\"]")).trim());
    }

    public CheckoutPage clickOnCheckoutButton() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}
