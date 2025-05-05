package tests.SwagLabs;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.testng.AllureTestNg;
import pages.swaglabs.CartsPage;
import pages.swaglabs.CheckoutPage;
import pages.swaglabs.LoginPage;
import pages.swaglabs.ProductsPage;
import utils.listener.RetryListener;

@Listeners({AllureTestNg.class, RetryListener.class}) 
public class ProductsTests extends BaseTest {
    LoginPage loginPage;
    ProductsPage productPage;

    @BeforeClass
    public void before() {
        logger.info("******* ProductsTests started ********");
        loginPage = navigatesToSwagLabs();
        productPage = loginPage.login(getUsername(), getPassword());
    }
    
    @Test(description = "Verify that the cart shows the correct item count")
    public void testCartShowing_correctItemCount() {
        int initialItemCount_onCart = productPage.getCartItemCount();
        productPage.addProductToCart(getProductName());
        Assert.assertEquals(productPage.getCartItemCount(), initialItemCount_onCart + 1);
    }

    @Test(description = "Verify Complete the checkout process with mock customer data.", dependsOnMethods = "testCartShowing_correctItemCount")
    public void testCheckoutFlow(){
        CartsPage cartsPage = productPage.navigatesToCart();
        Assert.assertEquals(cartsPage.getCountOfProducts_inCart_byProductName(getProductName()), 1);

        CheckoutPage checkoutPage = cartsPage.checkoutProduct_fromCart();
        Assert.assertEquals(checkoutPage.getOrderSuccessMessage(), "Thank you for your order!");
    }
}
