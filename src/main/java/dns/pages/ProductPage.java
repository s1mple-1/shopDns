package dns.pages;

import dns.BasketData;
import dns.Product;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//label['Купить']")
    WebElement buyButton;

    @FindBy(id = "product-page")
    WebElement productPage;

    @FindBy(tagName = "h1")
    WebElement productName;

    @FindBy(xpath = "//span[contains(@class, 'current-price-value')]")
    WebElement productPrice;

    @FindBy(xpath = "//div[contains(@class, 'additional-warranty')]//select")
    WebElement warranty;

    public void addProductToBasket() {
        String name = waitElementToClick(productName).getText();
        long price = Long.parseLong(productPrice.getAttribute("data-price-value"));
        long totalPrice = Long.parseLong(productPrice.getText().replaceAll(" ", ""));
        BasketData.getBasketData().add(new Product(name, price, true, totalPrice));
        buyButton.click();
    }

    public void chooseWarranty(int year) {
        if (year == 1 || year == 2) {
            waitElementToClick(warranty).sendKeys(String.valueOf(year));;
            warranty.sendKeys(Keys.ENTER);
        } else {
            System.out.println("Вы вызвали метод с неверным параметром");
        }
    }


}
