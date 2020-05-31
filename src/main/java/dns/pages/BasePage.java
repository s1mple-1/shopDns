package dns.pages;

import dns.InitWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class BasePage {
    WebDriver webDriver;
    private WebDriverWait webDriverWait;

    @FindBy(xpath = "//*[@data-commerce-target='CART']")
    private WebElement goToBasket;

    @FindBy(xpath = "//span[@class = 'cart-link__price']")
    private WebElement totalPrice;

    public BasePage() {
        webDriver = InitWebDriver.getWebDriver();
        webDriverWait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(webDriver, this);
    }

    public WebElement waitElementToClick(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitElementRefreshing(long oldBasketPrice) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                long totalPrice = getTotalBasketPrice();
                return totalPrice != oldBasketPrice;
            }
        });
    }

    public void goToBasket() {
        waitElementToClick(goToBasket).click();
    }

    public long getTotalBasketPrice() {
        return Long.parseLong(waitElementToClick(totalPrice).getText().replaceAll(" ", ""));
    }
}
