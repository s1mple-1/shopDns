package dns.pages;

import dns.BasketData;
import dns.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasketPage extends BasePage {
    @FindBy(xpath = "//*[@data-cart-product-id][1]")
    public static WebElement checkProductAfterRollback;

    @FindBy(xpath = "//*[@data-cart-product-id][2]")
    public static WebElement checkAfterDeleteSecondProduct;

    @FindBy(xpath = "//span[@class='base-ui-radio-button__icon base-ui-radio-button__icon_checked' and contains(text(), '24  мес')]")
    public static WebElement checkWarrantyAfterRollback;

    @FindBy(xpath = "//*[contains(text(), '24  мес')]")
    WebElement warranty;

    @FindBy(xpath = "//a[text()='Вернуть удалённый товар']")
    WebElement rollback;

    public boolean checkPrice() {
        String price = "//div[@data-cart-product-id][%d]//span[@class='price__current']";
        int count = 1;
        for (Product product : BasketData.getBasketData()) {
            String stringPrice = webDriver.findElement(By.xpath(String.format(price, count))).getText().replaceAll(" ", "");
            long expected = product.getPrice();
            long actual = Long.parseLong(stringPrice);
            if (expected != actual) {
                System.out.println(String.format("Expected price: %d. Actual price: %d. Product: %s", expected, actual, product.getName()));
                return false;
            }
            count++;
        }
        return true;
    }

    public void chooseTwoYearWarranty() {
        waitElementToClick(warranty).click();
    }

    public Product deleteFromBasket(String name) {
        String deleted = "//div[@data-cart-product-id][%d]//button[contains(text(), 'Удалить')]";
        Product product = findCurrentProduct(name);
        int index = BasketData.getBasketData().indexOf(product);
        BasketData.getBasketData().remove(product);
        waitElementToClick(String.format(deleted, index + 1)).click();
        return product;
    }

    public void addMoreProduct(String name, int count, long oldPrice) {
        Product product = findCurrentProduct(name);
        if (product != null) {
            int index = BasketData.getBasketData().indexOf(product);
            String plusButton = "//div[@data-cart-product-id][%d]//*[@class='count-buttons__icon-plus']";
            for (int i = 0; i < count; i++) {
                waitElementToClick(String.format(plusButton, index + 1)).click();
                waitElementRefreshing(oldPrice);
                oldPrice = getTotalBasketPrice();
                product.setCount(product.getCount() + 1);
            }
        }
    }

    private Product findCurrentProduct(String name) {
        for (Product product : BasketData.getBasketData()) {
            if (product.getName().contains(name)) {
                return product;
            }
        }
        return null;
    }

    public void rollbackDelete(Product product) {
        if (BasketData.getBasketData().size() == 0) {
            waitElementToClick(rollback).click();
            BasketData.getBasketData().add(product);
        } else {
            System.out.println("Возврат товара возможет только после удалении последнего товара в корзине. Корзина не пуста.");
        }
    }

    public int checkCountProductAfterRollBack(Product product) {
        String count = "//div[@data-cart-product-id][1]//input[@class='count-buttons__input']";
        BasketData.getBasketData().indexOf(product);
        return Integer.parseInt(waitElementToClick(count).getAttribute("value"));
    }

    public boolean checkProductDelete(String name) {
        String xPath = "//a[contains(text(), '%s')]";
        return webDriver.findElements(By.xpath(String.format(xPath, name))).isEmpty();
    }
}
