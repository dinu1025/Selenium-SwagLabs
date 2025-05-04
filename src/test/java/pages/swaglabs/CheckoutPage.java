package pages.swaglabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameField = By.cssSelector("[placeholder=\"First Name\"]");
    private By lastNameField = By.cssSelector("[placeholder=\"Last Name\"]");
    private By zipCodeField = By.cssSelector("[placeholder=\"Zip/Postal Code\"]");
    private By continueButton = By.cssSelector("input[value=\"Continue\"]");
    private By finishButton = By.id("finish");
    private By thankYouForYourOrderMessage = By.cssSelector("h2.complete-header");
    private By pageTitle = By.cssSelector("span.title");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public String isProductPresent_inCart(String productName) {
        return getText(By.xpath("//div[@class='inventory_item_name'][text()='"+productName+"']"));
    }
   
    public int getCountOfProducts_inCart_byProductName(String productName) {
        return Integer.parseInt(getText(By.xpath("//div[text()='"+productName+"']/parent::a/parent::div/preceding-sibling::div[@data-test=\"item-quantity\"]")).trim());
    }

    public void checkoutYourInformation(String firstName, String lastName, String zipCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipCodeField, zipCode);
        click(continueButton);
    }

    public void clickOnFinishButton() {
        click(finishButton);
    }

    public String getOrderSuccessMessage() {
        return getText(thankYouForYourOrderMessage);
    }
}
