package dns.pages;

import dns.InitWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public void waitAjaxElement() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 2000);
        webDriverWait.until((ExpectedCondition<Boolean>) driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return jQuery.active == 0");
        });
    }

    public void goToBasket() {
        waitElementToClick(goToBasket).click();
    }


    public long getTotalBasketPrice() {
        waitAjaxElement();
        return Long.parseLong(waitElementToClick(totalPrice).getText().replaceAll(" ", ""));
    }

}
