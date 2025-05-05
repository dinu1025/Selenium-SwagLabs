package pages.swaglabs;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    public static final Logger logger = Logger.getLogger(LoginPage.class.getName());
    private By username = new By.ById("user-name");
    private By password = new By.ByName("password");
    private By loginButton = new By.ByClassName("submit-button");
    private By errorMessage_onLoginFailure = By.cssSelector("h3[data-test=\"error\"]");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public ProductsPage login(String email, String password){
        findElementBy(this.username).sendKeys(email);
        findElementBy(this.password).sendKeys(password);
        findElementBy(loginButton).click();       
        logger.info("Login successful for user: {} " + username);
        return new ProductsPage(driver);
    }

    public LoginPage invalidLogin(String email, String password){
        findElementBy(this.username).sendKeys(email);
        findElementBy(this.password).sendKeys(password);
        findElementBy(loginButton).click();
        logger.warn("Login failed for user: {} "+ username);
        return this;
    }

    public WebElement getErrorMessage(){
        return findElementBy(errorMessage_onLoginFailure);
    }

    public String getErrorMessageText(){
        return getErrorMessage().getText();
    }
}
