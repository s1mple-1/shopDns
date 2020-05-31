package dns.pages;

import dns.BasketData;
import dns.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//label['Купить']")
    WebElement buyButton;

//    @FindBy(id = "product-page")
//    WebElement productPage;

    @FindBy(tagName = "h1")
    WebElement productName;

    @FindBy(xpath = "//span[contains(@class, 'current-price-value')]")
    WebElement productPrice;


    public void addProductToBasket() {
        String name = waitElementToClick(productName).getText();
        long price = Long.parseLong(productPrice.getAttribute("data-price-value"));
        Product product = new Product(name, price);
        BasketData.getBasketData().add(product);
        buyButton.click();
    }
}
