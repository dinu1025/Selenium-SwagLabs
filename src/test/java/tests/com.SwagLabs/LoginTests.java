package tests.SwagLabs;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.testng.AllureTestNg;
import pages.swaglabs.BasePage;
import pages.swaglabs.LoginPage;
import pages.swaglabs.ProductsPage;
import utils.JsonDataProvider;

@Listeners({AllureTestNg.class}) 
public class LoginTests extends BaseTest {
    public static final Logger logger = Logger.getLogger(BasePage.class.getName());
    LoginPage loginPage;

    @DataProvider(name="loginCredentials")
    public Object[][] loginCredentials(){
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user","secret_sauce"}
        };
    }

    @BeforeMethod
    public void beforeEach(){
        logger.info("******* LoginTests started ********");
        loginPage = navigatesToSwagLabs();
    }
    
    @Test(dataProvider = "loginData", dataProviderClass = JsonDataProvider.class)
    public void testLogin(String username, String password) {
        ProductsPage productsPage = loginPage.login(username, password);
        Assert.assertEquals(productsPage.getPageHeading(), "Products", "Products page heading is not matching");
    }

    @Test(description = "Verify invalid login functionality")
    public void testInvalidLoginCredentials() {
        loginPage.invalidLogin(getLockedUserName(), getPassword());
        Assert.assertEquals(loginPage.getErrorMessageText(), "Epic sadface: Sorry, this user has been locked out.");
    }
}
